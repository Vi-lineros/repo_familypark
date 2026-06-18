package cl.familypark.familyparksolicitud.controller;

import cl.familypark.familyparksolicitud.dto.response.SalaResponseDTO;
import cl.familypark.familyparksolicitud.service.SalaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para consulta de Salas.
 *
 * GET /api/v1/salas                           → Lista salas activas (200 OK)
 * GET /api/v1/salas?establecimiento={id}      → Lista salas por establecimiento (200 OK)
 */
@RestController
@RequestMapping("/api/v1/salas")
@RequiredArgsConstructor
@Slf4j
public class SalaController {

    private final SalaService salaService;

    /**
     * GET /api/v1/salas
     * GET /api/v1/salas?establecimiento=1
     *
     * Si se proporciona el parámetro 'establecimiento', filtra por ese ID.
     * Si no, retorna todas las salas activas.
     *
     * @param idEstablecimiento (opcional) ID del establecimiento a filtrar.
     * @return 200 OK con lista de salas.
     */
    @GetMapping
    public ResponseEntity<List<SalaResponseDTO>> listarSalas(
            @RequestParam(name = "establecimiento", required = false) Long idEstablecimiento) {

        log.info("GET /api/v1/salas - establecimiento: {}", idEstablecimiento);

        List<SalaResponseDTO> salas = (idEstablecimiento != null)
                ? salaService.listarPorEstablecimiento(idEstablecimiento)
                : salaService.listarActivas();

        return ResponseEntity.ok(salas);
    }
}
