package cl.familypark.familyparksucursal.controller;

import cl.familypark.familyparksucursal.dto.request.SucursalRequestDTO;
import cl.familypark.familyparksucursal.dto.response.SucursalResponseDTO;
import cl.familypark.familyparksucursal.service.SucursalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sucursales")
@RequiredArgsConstructor
@Slf4j
public class SucursalController {

    private final SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalResponseDTO>> listar(
            @RequestParam(name = "soloActivas", required = false, defaultValue = "true") boolean soloActivas) {
        log.info("GET /api/v1/sucursales - soloActivas: {}", soloActivas);
        List<SucursalResponseDTO> sucursales = soloActivas 
                ? sucursalService.listarActivas() 
                : sucursalService.listarTodas();
        return ResponseEntity.ok(sucursales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/sucursales/{}", id);
        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<SucursalResponseDTO> crear(@Valid @RequestBody SucursalRequestDTO requestDTO) {
        log.info("POST /api/v1/sucursales - nombre: {}", requestDTO.getNombre());
        SucursalResponseDTO response = sucursalService.crear(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalResponseDTO> actualizar(
            @PathVariable Long id, 
            @Valid @RequestBody SucursalRequestDTO requestDTO) {
        log.info("PUT /api/v1/sucursales/{} - nombre: {}", id, requestDTO.getNombre());
        return ResponseEntity.ok(sucursalService.actualizar(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SucursalResponseDTO> desactivar(@PathVariable Long id) {
        log.info("DELETE /api/v1/sucursales/{} (Desactivación)", id);
        return ResponseEntity.ok(sucursalService.desactivar(id));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<SucursalResponseDTO> activar(@PathVariable Long id) {
        log.info("PUT /api/v1/sucursales/{}/activar", id);
        return ResponseEntity.ok(sucursalService.activar(id));
    }
}
