package cl.familypark.familyparkrecursos.controller;

import cl.familypark.familyparkrecursos.dto.request.CategoriaRecursoRequestDTO;
import cl.familypark.familyparkrecursos.dto.response.CategoriaRecursoResponseDTO;
import cl.familypark.familyparkrecursos.service.CategoriaRecursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias-recursos")
@RequiredArgsConstructor
@Slf4j
public class CategoriaRecursoController {

    private final CategoriaRecursoService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaRecursoResponseDTO>> listar(
            @RequestParam(name = "soloActivas", required = false, defaultValue = "true") boolean soloActivas) {
        log.info("GET /api/v1/categorias-recursos - soloActivas: {}", soloActivas);
        List<CategoriaRecursoResponseDTO> list = soloActivas 
                ? categoriaService.listarActivas() 
                : categoriaService.listarTodas();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaRecursoResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/categorias-recursos/{}", id);
        return ResponseEntity.ok(categoriaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaRecursoResponseDTO> crear(@Valid @RequestBody CategoriaRecursoRequestDTO requestDTO) {
        log.info("POST /api/v1/categorias-recursos - nombre: {}", requestDTO.getNombre());
        CategoriaRecursoResponseDTO response = categoriaService.crear(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaRecursoResponseDTO> actualizar(
            @PathVariable Long id, 
            @Valid @RequestBody CategoriaRecursoRequestDTO requestDTO) {
        log.info("PUT /api/v1/categorias-recursos/{} - nombre: {}", id, requestDTO.getNombre());
        return ResponseEntity.ok(categoriaService.actualizar(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoriaRecursoResponseDTO> desactivar(@PathVariable Long id) {
        log.info("DELETE /api/v1/categorias-recursos/{} (Desactivación)", id);
        return ResponseEntity.ok(categoriaService.desactivar(id));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<CategoriaRecursoResponseDTO> activar(@PathVariable Long id) {
        log.info("PUT /api/v1/categorias-recursos/{}/activar", id);
        return ResponseEntity.ok(categoriaService.activar(id));
    }
}
