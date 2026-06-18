package cl.familypark.familyparkpersonal.controller;

import cl.familypark.familyparkpersonal.dto.request.CargoRequestDTO;
import cl.familypark.familyparkpersonal.dto.response.CargoResponseDTO;
import cl.familypark.familyparkpersonal.service.CargoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cargos")
@RequiredArgsConstructor
@Slf4j
public class CargoController {

    private final CargoService cargoService;

    @GetMapping
    public ResponseEntity<List<CargoResponseDTO>> listar(
            @RequestParam(name = "soloActivos", required = false, defaultValue = "true") boolean soloActivos) {
        log.info("GET /api/v1/cargos - soloActivos: {}", soloActivos);
        List<CargoResponseDTO> cargos = soloActivos 
                ? cargoService.listarActivos() 
                : cargoService.listarTodos();
        return ResponseEntity.ok(cargos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/cargos/{}", id);
        return ResponseEntity.ok(cargoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CargoResponseDTO> crear(@Valid @RequestBody CargoRequestDTO requestDTO) {
        log.info("POST /api/v1/cargos - nombre: {}", requestDTO.getNombre());
        CargoResponseDTO response = cargoService.crear(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CargoRequestDTO requestDTO) {
        log.info("PUT /api/v1/cargos/{} - nombre: {}", id, requestDTO.getNombre());
        return ResponseEntity.ok(cargoService.actualizar(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CargoResponseDTO> desactivar(@PathVariable Long id) {
        log.info("DELETE /api/v1/cargos/{} (Desactivación)", id);
        return ResponseEntity.ok(cargoService.desactivar(id));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<CargoResponseDTO> activar(@PathVariable Long id) {
        log.info("PUT /api/v1/cargos/{}/activar", id);
        return ResponseEntity.ok(cargoService.activar(id));
    }
}
