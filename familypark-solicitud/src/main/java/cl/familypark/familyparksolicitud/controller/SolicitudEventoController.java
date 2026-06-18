package cl.familypark.familyparksolicitud.controller;

import cl.familypark.familyparksolicitud.dto.request.SolicitudEventoRequestDTO;
import cl.familypark.familyparksolicitud.dto.response.SolicitudEventoResponseDTO;
import cl.familypark.familyparksolicitud.service.SolicitudEventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import cl.familypark.familyparksolicitud.dto.request.EstadoUpdateRequestDTO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller REST para la gestión de Solicitudes de Eventos.
 *
 * POST /api/v1/solicitudes  → Crear nueva solicitud (201 Created)
 * GET  /api/v1/solicitudes  → Listar todas las solicitudes (200 OK)
 *
 * No contiene lógica de negocio — delega todo al SolicitudEventoService.
 */
@RestController
@RequestMapping("/api/v1/solicitudes")
@RequiredArgsConstructor
@Slf4j
public class SolicitudEventoController {

    private final SolicitudEventoService solicitudService;

    /**
     * POST /api/v1/solicitudes
     * Registra una nueva solicitud de evento infantil.
     *
     * @param requestDTO Datos del cliente + datos del evento (validados con @Valid).
     * @return 201 Created con el DTO de respuesta completo.
     */
    @PostMapping
    public ResponseEntity<SolicitudEventoResponseDTO> crearSolicitud(
            @Valid @RequestBody SolicitudEventoRequestDTO requestDTO) {

        log.info("POST /api/v1/solicitudes - correo: {}", requestDTO.getCorreo());
        SolicitudEventoResponseDTO response = solicitudService.crearSolicitud(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/v1/solicitudes
     * Retorna todas las solicitudes registradas en el sistema.
     *
     * @return 200 OK con lista de solicitudes.
     */
    @GetMapping
    public ResponseEntity<List<SolicitudEventoResponseDTO>> listarSolicitudes() {
        log.info("GET /api/v1/solicitudes");
        List<SolicitudEventoResponseDTO> solicitudes = solicitudService.listarTodas();
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudEventoResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/solicitudes/{}", id);
        return ResponseEntity.ok(solicitudService.obtenerPorId(id));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<SolicitudEventoResponseDTO> actualizarEstado(@PathVariable Long id,
                                                                       @RequestBody EstadoUpdateRequestDTO request) {
        log.info("PUT /api/v1/solicitudes/{}/estado - {}", id, request.getEstado());
        SolicitudEventoResponseDTO response = solicitudService.cambiarEstado(id, request.getEstado());
        return ResponseEntity.ok(response);
    }
}
