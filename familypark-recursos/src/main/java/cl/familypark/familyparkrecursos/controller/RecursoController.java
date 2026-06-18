package cl.familypark.familyparkrecursos.controller;

import cl.familypark.familyparkrecursos.dto.request.RecursoRequestDTO;
import cl.familypark.familyparkrecursos.dto.response.RecursoResponseDTO;
import cl.familypark.familyparkrecursos.service.RecursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recursos")
@RequiredArgsConstructor
@Slf4j
public class RecursoController {

    private final RecursoService recursoService;

    @GetMapping
    public ResponseEntity<List<RecursoResponseDTO>> listar(
            @RequestParam(name = "soloActivos", required = false, defaultValue = "true") boolean soloActivos) {
        log.info("GET /api/v1/recursos - soloActivos: {}", soloActivos);
        List<RecursoResponseDTO> list = soloActivos 
                ? recursoService.listarActivos() 
                : recursoService.listarTodos();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecursoResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/recursos/{}", id);
        return ResponseEntity.ok(recursoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<RecursoResponseDTO> crear(@Valid @RequestBody RecursoRequestDTO requestDTO) {
        log.info("POST /api/v1/recursos - nombre: {}", requestDTO.getNombre());
        RecursoResponseDTO response = recursoService.crear(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecursoResponseDTO> actualizar(
            @PathVariable Long id, 
            @Valid @RequestBody RecursoRequestDTO requestDTO) {
        log.info("PUT /api/v1/recursos/{} - nombre: {}", id, requestDTO.getNombre());
        return ResponseEntity.ok(recursoService.actualizar(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RecursoResponseDTO> desactivar(@PathVariable Long id) {
        log.info("DELETE /api/v1/recursos/{} (Desactivación)", id);
        return ResponseEntity.ok(recursoService.desactivar(id));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<RecursoResponseDTO> activar(@PathVariable Long id) {
        log.info("PUT /api/v1/recursos/{}/activar", id);
        return ResponseEntity.ok(recursoService.activar(id));
    }
}
