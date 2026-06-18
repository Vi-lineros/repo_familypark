package cl.familypark.familyparksolicitud.service;

import cl.familypark.familyparksolicitud.dto.request.SolicitudEventoRequestDTO;
import cl.familypark.familyparksolicitud.dto.response.SolicitudEventoResponseDTO;
import cl.familypark.familyparksolicitud.exception.BadRequestException;
import cl.familypark.familyparksolicitud.exception.ResourceNotFoundException;
import cl.familypark.familyparksolicitud.model.*;
import cl.familypark.familyparksolicitud.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service principal del microservicio.
 * Contiene toda la lógica de negocio para la gestión de solicitudes de eventos.
 *
 * Patrón: Controller → Service → Repository → MySQL
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SolicitudEventoService {

    private final SolicitudEventoRepository solicitudRepo;
    private final ClienteRepository          clienteRepo;
    private final EstablecimientoRepository  establecimientoRepo;
    private final SalaRepository             salaRepo;
    private final KitServicioRepository      kitRepo;
        private final RestTemplate               restTemplate;

        @Value("${app.microservicio.evento.url:http://localhost:8085}")
        private String eventoServiceUrl;

    // ── crearSolicitud ────────────────────────────────────────

    /**
     * Crea una nueva solicitud de evento siguiendo estos pasos:
     *  1. Buscar cliente por correo; si no existe, crearlo.
     *  2. Buscar y validar Establecimiento.
     *  3. Buscar y validar Sala (debe pertenecer al establecimiento).
     *  4. Buscar y validar Kit.
     *  5. Validar que horaFin > horaInicio.
     *  6. Construir y persistir la SolicitudEvento con estado PENDIENTE.
     *  7. Retornar DTO de respuesta completo.
     *
     * @param dto DTO validado con los datos del cliente y del evento.
     * @return SolicitudEventoResponseDTO con todos los datos persistidos.
     */
    public SolicitudEventoResponseDTO crearSolicitud(SolicitudEventoRequestDTO dto) {
        log.info("Iniciando creación de solicitud para correo: {}", dto.getCorreo());

        // 1. Buscar cliente por correo; crear si no existe
        Cliente cliente = clienteRepo.findByCorreo(dto.getCorreo())
                .orElseGet(() -> {
                    log.info("Cliente no encontrado. Creando nuevo cliente: {}", dto.getCorreo());
                    Cliente nuevo = Cliente.builder()
                            .nombre(dto.getNombre())
                            .apellido(dto.getApellido())
                            .correo(dto.getCorreo())
                            .telefono(dto.getTelefono())
                            .direccion(dto.getDireccion())
                            .build();
                    return clienteRepo.save(nuevo);
                });

        // 2. Buscar establecimiento
        Establecimiento establecimiento = establecimientoRepo
                .findById(dto.getIdEstablecimiento())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Establecimiento", dto.getIdEstablecimiento()));

        if (!establecimiento.getActivo()) {
            throw new BadRequestException(
                    "El establecimiento seleccionado no está activo.");
        }

        // 3. Buscar sala y validar que pertenece al establecimiento
        Sala sala = salaRepo.findById(dto.getIdSala())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sala", dto.getIdSala()));

        if (!sala.getActiva()) {
            throw new BadRequestException("La sala seleccionada no está activa.");
        }

        if (!sala.getEstablecimiento().getIdEstablecimiento()
                .equals(establecimiento.getIdEstablecimiento())) {
            throw new BadRequestException(
                    "La sala seleccionada no pertenece al establecimiento indicado.");
        }

        // 4. Buscar kit
        KitServicio kit = kitRepo.findById(dto.getIdKit())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Kit de Servicio", dto.getIdKit()));

        if (!kit.getActivo()) {
            throw new BadRequestException("El kit de servicio seleccionado no está activo.");
        }

        // 5. Validar rango horario
        if (dto.getHoraFin().isBefore(dto.getHoraInicio()) ||
                dto.getHoraFin().equals(dto.getHoraInicio())) {
            throw new BadRequestException(
                    "La hora de fin debe ser posterior a la hora de inicio.");
        }

        // 5b. Validar cantidad de niños contra el rango del kit
        if (dto.getCantidadNinos() < kit.getMinimoNinos()) {
            throw new BadRequestException(String.format(
                    "El kit '%s' requiere un mínimo de %d niños.",
                    kit.getNombre(), kit.getMinimoNinos()));
        }
        if (dto.getCantidadNinos() > kit.getMaximoNinos()) {
            throw new BadRequestException(String.format(
                    "El kit '%s' permite un máximo de %d niños.",
                    kit.getNombre(), kit.getMaximoNinos()));
        }

        // 6. Construir solicitud — estado = PENDIENTE, fechaSolicitud via @PrePersist
        SolicitudEvento solicitud = SolicitudEvento.builder()
                .cliente(cliente)
                .establecimiento(establecimiento)
                .sala(sala)
                .kitServicio(kit)
                .fechaSolicitada(dto.getFechaSolicitada())
                .horaInicio(dto.getHoraInicio())
                .horaFin(dto.getHoraFin())
                .cantidadNinos(dto.getCantidadNinos())
                .cantidadAdultos(dto.getCantidadAdultos())
                .nombreCumpleanero(dto.getNombreCumpleanero())
                .edadCumpleanero(dto.getEdadCumpleanero())
                .requerimientosEspeciales(dto.getRequerimientosEspeciales())
                .estado(EstadoSolicitud.PENDIENTE)
                .build();

        // 7. Guardar
        SolicitudEvento guardada = solicitudRepo.save(solicitud);
        log.info("Solicitud creada con ID: {}", guardada.getIdSolicitud());

        // 8. Retornar DTO de respuesta
        return toResponseDTO(guardada);
    }

    // ── listarTodas ───────────────────────────────────────────

    /**
     * Retorna todas las solicitudes registradas en el sistema.
     */
    @Transactional(readOnly = true)
    public List<SolicitudEventoResponseDTO> listarTodas() {
        log.debug("Listando todas las solicitudes.");
        return solicitudRepo.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SolicitudEventoResponseDTO obtenerPorId(Long id) {
        log.debug("Obteniendo solicitud por ID: {}", id);
        SolicitudEvento s = solicitudRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud de Evento", id));
        return toResponseDTO(s);
    }

    // ── Mapper ────────────────────────────────────────────────

    /**
     * Convierte una entidad SolicitudEvento a su DTO de respuesta.
     * Se realiza aquí para mantener toda la lógica en el Service.
     */
    private SolicitudEventoResponseDTO toResponseDTO(SolicitudEvento s) {
        return SolicitudEventoResponseDTO.builder()
                .idSolicitud(s.getIdSolicitud())
                .estado(s.getEstado().name())
                .fechaSolicitud(s.getFechaSolicitud())
                .fechaSolicitada(s.getFechaSolicitada())
                .horaInicio(s.getHoraInicio())
                .horaFin(s.getHoraFin())
                .cantidadNinos(s.getCantidadNinos())
                .cantidadAdultos(s.getCantidadAdultos())
                .nombreCumpleanero(s.getNombreCumpleanero())
                .edadCumpleanero(s.getEdadCumpleanero())
                .requerimientosEspeciales(s.getRequerimientosEspeciales())
                // Cliente
                .idCliente(s.getCliente().getIdCliente())
                .nombreCliente(s.getCliente().getNombre())
                .apellidoCliente(s.getCliente().getApellido())
                .correoCliente(s.getCliente().getCorreo())
                .telefonoCliente(s.getCliente().getTelefono())
                // Establecimiento
                .idEstablecimiento(s.getEstablecimiento().getIdEstablecimiento())
                .nombreEstablecimiento(s.getEstablecimiento().getNombre())
                .ciudadEstablecimiento(s.getEstablecimiento().getCiudad())
                // Sala
                .idSala(s.getSala().getIdSala())
                .nombreSala(s.getSala().getNombre())
                .tipoSala(s.getSala().getTipoSala())
                // Kit
                .idKit(s.getKitServicio().getIdKit())
                .nombreKit(s.getKitServicio().getNombre())
                .precioBaseKit(s.getKitServicio().getPrecioBase())
                .precioPorNinoKit(s.getKitServicio().getPrecioPorNino())
                .duracionMinutosKit(s.getKitServicio().getDuracionMinutos())
                .build();
    }

        @Transactional
        public SolicitudEventoResponseDTO cambiarEstado(Long id, String estadoStr) {
                SolicitudEvento solicitud = solicitudRepo.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Solicitud de Evento", id));

                EstadoSolicitud nuevoEstado;
                try {
                        nuevoEstado = EstadoSolicitud.valueOf(estadoStr);
                } catch (Exception e) {
                        throw new BadRequestException("Estado inválido: " + estadoStr);
                }

                solicitud.setEstado(nuevoEstado);
                solicitudRepo.save(solicitud);

                // Si se aprueba, intentar crear/actualizar un Evento en familypark-evento
                if (nuevoEstado == EstadoSolicitud.CONFIRMADO) {
                        try {
                                // Construir payload compatible con EventoRequestDTO
                                java.util.Map<String, Object> payload = new java.util.HashMap<>();
                                payload.put("idSolicitud", solicitud.getIdSolicitud());
                                payload.put("idSucursal", solicitud.getEstablecimiento().getIdEstablecimiento());
                                payload.put("idSala", solicitud.getSala().getIdSala());
                                payload.put("nombreSucursal", solicitud.getEstablecimiento().getNombre());
                                payload.put("nombreSala", solicitud.getSala().getNombre());
                                payload.put("fecha", solicitud.getFechaSolicitada());
                                payload.put("horaInicio", solicitud.getHoraInicio());
                                payload.put("horaFin", solicitud.getHoraFin());
                                payload.put("nombreCumpleanero", solicitud.getNombreCumpleanero());
                                payload.put("edadCumpleanero", solicitud.getEdadCumpleanero());
                                payload.put("cantidadNinos", solicitud.getCantidadNinos());
                                payload.put("cantidadAdultos", solicitud.getCantidadAdultos());
                                BigDecimal precioTotal = BigDecimal.ZERO;
                                if (solicitud.getKitServicio() != null) {
                                        BigDecimal base = solicitud.getKitServicio().getPrecioBase() != null ? solicitud.getKitServicio().getPrecioBase() : BigDecimal.ZERO;
                                        BigDecimal porNino = solicitud.getKitServicio().getPrecioPorNino() != null ? solicitud.getKitServicio().getPrecioPorNino() : BigDecimal.ZERO;
                                        precioTotal = base.add(porNino.multiply(BigDecimal.valueOf(solicitud.getCantidadNinos() != null ? solicitud.getCantidadNinos() : 0)));
                                }
                                payload.put("precioTotal", precioTotal);
                                payload.put("requerimientosEspeciales", solicitud.getRequerimientosEspeciales());

                                try {
                                        restTemplate.postForEntity(eventoServiceUrl + "/api/v1/eventos", payload, Object.class);
                                } catch (HttpClientErrorException.Conflict c) {
                                        // Si ya existe evento para la solicitud, intentar buscar y actualizarlo
                                        try {
                                                java.util.Map[] eventos = restTemplate.getForObject(eventoServiceUrl + "/api/v1/eventos", java.util.Map[].class);
                                                if (eventos != null) {
                                                        for (java.util.Map ev : eventos) {
                                                                Object idSol = ev.get("idSolicitud");
                                                                if (idSol != null && Long.valueOf(idSol.toString()).equals(solicitud.getIdSolicitud())) {
                                                                        Object idEvento = ev.get("idEvento");
                                                                        if (idEvento != null) {
                                                                                Long idEv = Long.valueOf(idEvento.toString());
                                                                                restTemplate.put(eventoServiceUrl + "/api/v1/eventos/" + idEv, payload);
                                                                                break;
                                                                        }
                                                                }
                                                        }
                                                }
                                        } catch (Exception ex) {
                                                // Registrar y continuar
                                                log.warn("No se pudo sincronizar evento existente: {}", ex.getMessage());
                                        }
                                } catch (Exception e) {
                                        log.warn("No fue posible crear evento en familypark-evento: {}", e.getMessage());
                                }
                        } catch (Exception e) {
                                log.error("Error al construir/crear evento desde solicitud: {}", e.getMessage());
                        }
                }

                return toResponseDTO(solicitud);
        }
}
