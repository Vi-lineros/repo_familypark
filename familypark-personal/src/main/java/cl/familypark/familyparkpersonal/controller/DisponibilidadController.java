package cl.familypark.familyparkpersonal.controller;

import cl.familypark.familyparkpersonal.dto.request.DisponibilidadRequestDTO;
import cl.familypark.familyparkpersonal.dto.response.DisponibilidadResponseDTO;
import cl.familypark.familyparkpersonal.service.DisponibilidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/disponibilidades")
@RequiredArgsConstructor
@Slf4j
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    @GetMapping
    public ResponseEntity<List<DisponibilidadResponseDTO>> listar(
            @RequestParam(name = "soloActivas", required = false, defaultValue = "true") boolean soloActivas) {
        log.info("GET /api/v1/disponibilidades - soloActivas: {}", soloActivas);
        List<DisponibilidadResponseDTO> lista = soloActivas 
                ? disponibilidadService.listarTodas().stream().filter(DisponibilidadResponseDTO::getActiva).toList()
                : disponibilidadService.listarTodas();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisponibilidadResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/disponibilidades/{}", id);
        return ResponseEntity.ok(disponibilidadService.obtenerPorId(id));
    }

    @GetMapping("/personal/{idPersonal}")
    public ResponseEntity<List<DisponibilidadResponseDTO>> listarPorPersonal(
            @PathVariable Long idPersonal,
            @RequestParam(name = "soloActivas", required = false, defaultValue = "true") boolean soloActivas) {
        log.info("GET /api/v1/disponibilidades/personal/{} - soloActivas: {}", idPersonal, soloActivas);
        return ResponseEntity.ok(disponibilidadService.listarPorPersonal(idPersonal, soloActivas));
    }

    @PostMapping
    public ResponseEntity<DisponibilidadResponseDTO> crear(@Valid @RequestBody DisponibilidadRequestDTO requestDTO) {
        log.info("POST /api/v1/disponibilidades - personal: {}, dia: {}", requestDTO.getIdPersonal(), requestDTO.getDiaSemana());
        DisponibilidadResponseDTO response = disponibilidadService.crear(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisponibilidadResponseDTO> actualizar(
            @PathVariable Long id, 
            @Valid @RequestBody DisponibilidadRequestDTO requestDTO) {
        log.info("PUT /api/v1/disponibilidades/{}", id);
        return ResponseEntity.ok(disponibilidadService.actualizar(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DisponibilidadResponseDTO> desactivar(@PathVariable Long id) {
        log.info("DELETE /api/v1/disponibilidades/{} (Desactivación)", id);
        return ResponseEntity.ok(disponibilidadService.desactivar(id));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<DisponibilidadResponseDTO> activar(@PathVariable Long id) {
        log.info("PUT /api/v1/disponibilidades/{}/activar", id);
        return ResponseEntity.ok(disponibilidadService.activar(id));
    }
}
