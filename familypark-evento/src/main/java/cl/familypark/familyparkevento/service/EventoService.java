package cl.familypark.familyparkevento.service;

import cl.familypark.familyparkevento.dto.request.EventoRequestDTO;
import cl.familypark.familyparkevento.dto.response.EventoResponseDTO;
import cl.familypark.familyparkevento.exception.BadRequestException;
import cl.familypark.familyparkevento.exception.ConflictException;
import cl.familypark.familyparkevento.exception.ResourceNotFoundException;
import cl.familypark.familyparkevento.model.EstadoEvento;
import cl.familypark.familyparkevento.model.Evento;
import cl.familypark.familyparkevento.model.MinutaOperacional;
import cl.familypark.familyparkevento.repository.EventoRepository;
import cl.familypark.familyparkevento.repository.MinutaOperacionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final MinutaOperacionalRepository minutaRepository;
    private final RestTemplate restTemplate;

    @Value("${service.solicitud.url:http://localhost:8082/api/v1/solicitudes}")
    private String solicitudServiceUrl;

    @Value("${service.sucursal.url:http://localhost:8081/api/v1/sucursales}")
    private String sucursalServiceUrl;

    @Transactional
    public EventoResponseDTO crearEvento(EventoRequestDTO requestDTO) {
        if (eventoRepository.existsByIdSolicitud(requestDTO.getIdSolicitud())) {
            throw new ConflictException("Ya existe un evento para la solicitud ID: " + requestDTO.getIdSolicitud());
        }

        // Integraciones
        obtenerSolicitudPorId(requestDTO.getIdSolicitud());
        validarSucursal(requestDTO.getIdSucursal());
        validarSala(requestDTO.getIdSucursal(), requestDTO.getIdSala());

        Evento evento = Evento.builder()
                .idSolicitud(requestDTO.getIdSolicitud())
                .idSucursal(requestDTO.getIdSucursal())
                .idSala(requestDTO.getIdSala())
                .nombreSucursal(requestDTO.getNombreSucursal())
                .nombreSala(requestDTO.getNombreSala())
                .fecha(requestDTO.getFecha())
                .horaInicio(requestDTO.getHoraInicio())
                .horaFin(requestDTO.getHoraFin())
                .nombreCumpleanero(requestDTO.getNombreCumpleanero())
                .edadCumpleanero(requestDTO.getEdadCumpleanero())
                .cantidadNinos(requestDTO.getCantidadNinos())
                .cantidadAdultos(requestDTO.getCantidadAdultos())
                .precioTotal(requestDTO.getPrecioTotal())
                .requerimientosEspeciales(requestDTO.getRequerimientosEspeciales())
                .estado(EstadoEvento.PROGRAMADO)
                .build();

        evento = eventoRepository.save(evento);

        // Generar minuta inicial
        MinutaOperacional minuta = MinutaOperacional.builder()
                .evento(evento)
                .checklistJson("[]")
                .cronogramaJson("[]")
                .observaciones("Minuta generada automáticamente")
                .fechaGeneracion(LocalDateTime.now())
                .build();
        minutaRepository.save(minuta);

        return mapToDTO(evento);
    }

    public List<EventoResponseDTO> listarEventos() {
        return eventoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public EventoResponseDTO obtenerEventoPorId(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + id));
        return mapToDTO(evento);
    }

    @Transactional
    public EventoResponseDTO actualizarEvento(Long id, EventoRequestDTO requestDTO) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + id));

        if (!evento.getIdSucursal().equals(requestDTO.getIdSucursal())) {
            validarSucursal(requestDTO.getIdSucursal());
        }
        if (!evento.getIdSala().equals(requestDTO.getIdSala())) {
            validarSala(requestDTO.getIdSucursal(), requestDTO.getIdSala());
        }

        evento.setIdSucursal(requestDTO.getIdSucursal());
        evento.setIdSala(requestDTO.getIdSala());
        evento.setNombreSucursal(requestDTO.getNombreSucursal());
        evento.setNombreSala(requestDTO.getNombreSala());
        evento.setFecha(requestDTO.getFecha());
        evento.setHoraInicio(requestDTO.getHoraInicio());
        evento.setHoraFin(requestDTO.getHoraFin());
        evento.setNombreCumpleanero(requestDTO.getNombreCumpleanero());
        evento.setEdadCumpleanero(requestDTO.getEdadCumpleanero());
        evento.setCantidadNinos(requestDTO.getCantidadNinos());
        evento.setCantidadAdultos(requestDTO.getCantidadAdultos());
        evento.setPrecioTotal(requestDTO.getPrecioTotal());
        evento.setRequerimientosEspeciales(requestDTO.getRequerimientosEspeciales());

        return mapToDTO(eventoRepository.save(evento));
    }

    @Transactional
    public EventoResponseDTO cancelarEvento(Long id, String motivo) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + id));

        if (evento.getEstado() == EstadoEvento.CANCELADO) {
            throw new BadRequestException("El evento ya está cancelado");
        }

        evento.setEstado(EstadoEvento.CANCELADO);
        evento.setMotivoCancelacion(motivo);
        return mapToDTO(eventoRepository.save(evento));
    }

    @Transactional
    public EventoResponseDTO reprogramarEvento(Long id, EventoRequestDTO requestDTO) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + id));

        evento.setFecha(requestDTO.getFecha());
        evento.setHoraInicio(requestDTO.getHoraInicio());
        evento.setHoraFin(requestDTO.getHoraFin());
        evento.setEstado(EstadoEvento.REPROGRAMADO);
        return mapToDTO(eventoRepository.save(evento));
    }

    // --- INTEGRACIONES ---
    private void obtenerSolicitudPorId(Long idSolicitud) {
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(solicitudServiceUrl + "/" + idSolicitud, Object.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new BadRequestException("La solicitud no es válida");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("No se encontró la solicitud con ID: " + idSolicitud + " en familypark-solicitud");
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con familypark-solicitud: " + e.getMessage());
        }
    }

    private void validarSucursal(Long idSucursal) {
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(sucursalServiceUrl + "/" + idSucursal, Object.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new BadRequestException("La sucursal no es válida");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("No se encontró la sucursal con ID: " + idSucursal + " en familypark-sucursal");
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con familypark-sucursal: " + e.getMessage());
        }
    }

    private void validarSala(Long idSucursal, Long idSala) {
    try {
        ResponseEntity<Object> response =
                restTemplate.getForEntity(
                        "http://localhost:8081/api/v1/salas/" + idSala,
                        Object.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new BadRequestException("La sala no es válida");
        }

    } catch (HttpClientErrorException.NotFound e) {
        throw new ResourceNotFoundException(
                "No se encontró la sala con ID: " + idSala);
    } catch (Exception e) {
        throw new RuntimeException(
                "Error al conectar con familypark-sucursal para validar sala: "
                        + e.getMessage());
    }
}

    private EventoResponseDTO mapToDTO(Evento evento) {
        return EventoResponseDTO.builder()
                .idEvento(evento.getIdEvento())
                .idSolicitud(evento.getIdSolicitud())
                .idSucursal(evento.getIdSucursal())
                .idSala(evento.getIdSala())
                .nombreSucursal(evento.getNombreSucursal())
                .nombreSala(evento.getNombreSala())
                .fecha(evento.getFecha())
                .horaInicio(evento.getHoraInicio())
                .horaFin(evento.getHoraFin())
                .nombreCumpleanero(evento.getNombreCumpleanero())
                .edadCumpleanero(evento.getEdadCumpleanero())
                .cantidadNinos(evento.getCantidadNinos())
                .cantidadAdultos(evento.getCantidadAdultos())
                .precioTotal(evento.getPrecioTotal())
                .requerimientosEspeciales(evento.getRequerimientosEspeciales())
                .estado(evento.getEstado())
                .motivoCancelacion(evento.getMotivoCancelacion())
                .fechaCreacion(evento.getFechaCreacion())
                .fechaActualizacion(evento.getFechaActualizacion())
                .build();
    }
}
