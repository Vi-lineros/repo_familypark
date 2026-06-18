package cl.familypark.familyparksolicitud.controller;

import cl.familypark.familyparksolicitud.dto.response.KitServicioResponseDTO;
import cl.familypark.familyparksolicitud.service.KitServicioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller REST para consulta de Kits de Servicio.
 *
 * GET /api/v1/kits → Lista kits activos (200 OK)
 */
@RestController
@RequestMapping("/api/v1/kits")
@RequiredArgsConstructor
@Slf4j
public class KitServicioController {

    private final KitServicioService kitService;

    /**
     * GET /api/v1/kits
     * Retorna todos los kits de servicio activos disponibles para contratar.
     *
     * @return 200 OK con lista de kits.
     */
    @GetMapping
    public ResponseEntity<List<KitServicioResponseDTO>> listarKits() {
        log.info("GET /api/v1/kits");
        List<KitServicioResponseDTO> kits = kitService.listarActivos();
        return ResponseEntity.ok(kits);
    }
}
