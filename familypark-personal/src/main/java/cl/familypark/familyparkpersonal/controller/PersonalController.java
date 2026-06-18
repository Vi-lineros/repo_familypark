package cl.familypark.familyparkpersonal.controller;

import cl.familypark.familyparkpersonal.dto.request.PersonalRequestDTO;
import cl.familypark.familyparkpersonal.dto.response.PersonalResponseDTO;
import cl.familypark.familyparkpersonal.service.PersonalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/personal")
@RequiredArgsConstructor
@Slf4j
public class PersonalController {

    private final PersonalService personalService;

    @GetMapping
    public ResponseEntity<List<PersonalResponseDTO>> listar(
            @RequestParam(name = "soloActivos", required = false, defaultValue = "true") boolean soloActivos) {
        log.info("GET /api/v1/personal - soloActivos: {}", soloActivos);
        List<PersonalResponseDTO> lista = soloActivos 
                ? personalService.listarActivos() 
                : personalService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/personal/{}", id);
        return ResponseEntity.ok(personalService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PersonalResponseDTO> crear(@Valid @RequestBody PersonalRequestDTO requestDTO) {
        log.info("POST /api/v1/personal - nombre: {} {}", requestDTO.getNombre(), requestDTO.getApellido());
        PersonalResponseDTO response = personalService.crear(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonalResponseDTO> actualizar(
            @PathVariable Long id, 
            @Valid @RequestBody PersonalRequestDTO requestDTO) {
        log.info("PUT /api/v1/personal/{} - nombre: {}", id, requestDTO.getNombre());
        return ResponseEntity.ok(personalService.actualizar(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonalResponseDTO> desactivar(@PathVariable Long id) {
        log.info("DELETE /api/v1/personal/{} (Desactivación)", id);
        return ResponseEntity.ok(personalService.desactivar(id));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<PersonalResponseDTO> activar(@PathVariable Long id) {
        log.info("PUT /api/v1/personal/{}/activar", id);
        return ResponseEntity.ok(personalService.activar(id));
    }
}
