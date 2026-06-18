package cl.familypark.familyparkevento.service;

import cl.familypark.familyparkevento.dto.request.MinutaOperacionalRequestDTO;
import cl.familypark.familyparkevento.dto.response.MinutaDetalleResponseDTO;
import cl.familypark.familyparkevento.dto.response.MinutaOperacionalResponseDTO;
import cl.familypark.familyparkevento.dto.response.SolicitudEventoResponseDTO;
import cl.familypark.familyparkevento.exception.ResourceNotFoundException;
import cl.familypark.familyparkevento.model.Evento;
import cl.familypark.familyparkevento.model.MinutaOperacional;
import cl.familypark.familyparkevento.repository.EventoRepository;
import cl.familypark.familyparkevento.repository.MinutaOperacionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MinutaOperacionalService {

    private final MinutaOperacionalRepository repository;
    private final EventoRepository eventoRepository;
    private final RestTemplate restTemplate;

    @Value("${app.microservicio.solicitud.url:http://localhost:8082}")
    private String solicitudServiceUrl;

    public MinutaOperacionalResponseDTO consultarMinuta(Long idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + idEvento));

        if (evento.getMinuta() == null) {
            throw new ResourceNotFoundException("El evento no tiene una minuta generada");
        }

        return mapToDTO(evento.getMinuta());
    }

    public MinutaDetalleResponseDTO consultarMinutaDetalle(Long idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + idEvento));

        SolicitudEventoResponseDTO solicitud = obtenerSolicitudPorId(evento.getIdSolicitud());

        BigDecimal precioBaseKit = solicitud.getPrecioBaseKit() != null ? solicitud.getPrecioBaseKit() : BigDecimal.ZERO;
        BigDecimal precioPorNinoKit = solicitud.getPrecioPorNinoKit() != null ? solicitud.getPrecioPorNinoKit() : BigDecimal.ZERO;
        BigDecimal cantidadNinos = evento.getCantidadNinos() != null ? BigDecimal.valueOf(evento.getCantidadNinos()) : BigDecimal.ZERO;
        BigDecimal precioTotalCalculado = precioBaseKit.add(precioPorNinoKit.multiply(cantidadNinos));

        return MinutaDetalleResponseDTO.builder()
                .idEvento(evento.getIdEvento())
                .fecha(evento.getFecha())
                .horaInicio(evento.getHoraInicio())
                .horaFin(evento.getHoraFin())
                .estado(evento.getEstado() != null ? evento.getEstado().name() : null)
                .nombreCumpleanero(evento.getNombreCumpleanero())
                .edadCumpleanero(evento.getEdadCumpleanero())
                .cantidadNinos(evento.getCantidadNinos())
                .cantidadAdultos(evento.getCantidadAdultos())
                .requerimientosEspeciales(evento.getRequerimientosEspeciales())
                .nombreCliente(solicitud.getNombreCliente())
                .apellidoCliente(solicitud.getApellidoCliente())
                .correoCliente(solicitud.getCorreoCliente())
                .telefonoCliente(solicitud.getTelefonoCliente())
                .nombreKit(solicitud.getNombreKit())
                .precioBaseKit(solicitud.getPrecioBaseKit())
                .precioPorNinoKit(solicitud.getPrecioPorNinoKit())
                .precioTotalCalculado(precioTotalCalculado)
                .build();
    }

    private SolicitudEventoResponseDTO obtenerSolicitudPorId(Long idSolicitud) {
        try {
            SolicitudEventoResponseDTO solicitud = restTemplate.getForObject(
                    solicitudServiceUrl + "/api/v1/solicitudes/" + idSolicitud,
                    SolicitudEventoResponseDTO.class);

            if (solicitud == null) {
                throw new ResourceNotFoundException("No se encontró la solicitud con ID: " + idSolicitud);
            }
            return solicitud;
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("No se encontró la solicitud con ID: " + idSolicitud + " en familypark-solicitud");
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con familypark-solicitud: " + e.getMessage(), e);
        }
    }

    @Transactional
    public MinutaOperacionalResponseDTO actualizarMinuta(Long idEvento, MinutaOperacionalRequestDTO requestDTO) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + idEvento));

        MinutaOperacional minuta = evento.getMinuta();
        if (minuta == null) {
            throw new ResourceNotFoundException("El evento no tiene una minuta para actualizar");
        }

        minuta.setChecklistJson(requestDTO.getChecklistJson());
        minuta.setCronogramaJson(requestDTO.getCronogramaJson());
        minuta.setObservaciones(requestDTO.getObservaciones());

        return mapToDTO(repository.save(minuta));
    }

    private MinutaOperacionalResponseDTO mapToDTO(MinutaOperacional minuta) {
        return MinutaOperacionalResponseDTO.builder()
                .idMinuta(minuta.getIdMinuta())
                .idEvento(minuta.getEvento().getIdEvento())
                .checklistJson(minuta.getChecklistJson())
                .cronogramaJson(minuta.getCronogramaJson())
                .observaciones(minuta.getObservaciones())
                .fechaGeneracion(minuta.getFechaGeneracion())
                .build();
    }
}
