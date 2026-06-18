package cl.familypark.familyparksucursal.controller;

import cl.familypark.familyparksucursal.dto.request.SalaRequestDTO;
import cl.familypark.familyparksucursal.dto.response.SalaResponseDTO;
import cl.familypark.familyparksucursal.service.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salas")
@RequiredArgsConstructor
@Slf4j
public class SalaController {

    private final SalaService salaService;

    @GetMapping
    public ResponseEntity<List<SalaResponseDTO>> listar(
            @RequestParam(name = "soloActivas", required = false, defaultValue = "true") boolean soloActivas) {
        log.info("GET /api/v1/salas - soloActivas: {}", soloActivas);
        List<SalaResponseDTO> salas = soloActivas 
                ? salaService.listarActivas() 
                : salaService.listarTodas();
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/salas/{}", id);
        return ResponseEntity.ok(salaService.obtenerPorId(id));
    }

    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<SalaResponseDTO>> listarPorSucursal(
            @PathVariable Long idSucursal,
            @RequestParam(name = "soloActivas", required = false, defaultValue = "true") boolean soloActivas) {
        log.info("GET /api/v1/salas/sucursal/{} - soloActivas: {}", idSucursal, soloActivas);
        return ResponseEntity.ok(salaService.listarPorSucursal(idSucursal, soloActivas));
    }

    @PostMapping
    public ResponseEntity<SalaResponseDTO> crear(@Valid @RequestBody SalaRequestDTO requestDTO) {
        log.info("POST /api/v1/salas - nombre: {}, idSucursal: {}", requestDTO.getNombre(), requestDTO.getIdSucursal());
        SalaResponseDTO response = salaService.crear(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> actualizar(
            @PathVariable Long id, 
            @Valid @RequestBody SalaRequestDTO requestDTO) {
        log.info("PUT /api/v1/salas/{} - nombre: {}", id, requestDTO.getNombre());
        return ResponseEntity.ok(salaService.actualizar(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> desactivar(@PathVariable Long id) {
        log.info("DELETE /api/v1/salas/{} (Desactivación)", id);
        return ResponseEntity.ok(salaService.desactivar(id));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<SalaResponseDTO> activar(@PathVariable Long id) {
        log.info("PUT /api/v1/salas/{}/activar", id);
        return ResponseEntity.ok(salaService.activar(id));
    }
}
