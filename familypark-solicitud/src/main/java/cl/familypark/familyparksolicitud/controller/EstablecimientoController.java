package cl.familypark.familyparksolicitud.controller;

import cl.familypark.familyparksolicitud.dto.response.EstablecimientoResponseDTO;
import cl.familypark.familyparksolicitud.service.EstablecimientoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller REST para consulta de Establecimientos.
 *
 * GET /api/v1/establecimientos → Lista establecimientos activos (200 OK)
 */
@RestController
@RequestMapping("/api/v1/establecimientos")
@RequiredArgsConstructor
@Slf4j
public class EstablecimientoController {

    private final EstablecimientoService establecimientoService;

    /**
     * GET /api/v1/establecimientos
     * Retorna los establecimientos activos disponibles para el cliente.
     *
     * @return 200 OK con lista de establecimientos.
     */
    @GetMapping
    public ResponseEntity<List<EstablecimientoResponseDTO>> listarEstablecimientos() {
        log.info("GET /api/v1/establecimientos");
        List<EstablecimientoResponseDTO> establecimientos =
                establecimientoService.listarActivos();
        return ResponseEntity.ok(establecimientos);
    }
}
