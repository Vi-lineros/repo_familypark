package cl.familypark.familyparkevento.controller;

import cl.familypark.familyparkevento.dto.request.AsignacionRecursoRequestDTO;
import cl.familypark.familyparkevento.dto.response.AsignacionRecursoResponseDTO;
import cl.familypark.familyparkevento.service.AsignacionRecursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eventos/{idEvento}/recursos")
@RequiredArgsConstructor
public class AsignacionRecursoController {

    private final AsignacionRecursoService service;

    @PostMapping
    public ResponseEntity<AsignacionRecursoResponseDTO> asignarRecurso(@PathVariable Long idEvento, @Valid @RequestBody AsignacionRecursoRequestDTO request) {
        return new ResponseEntity<>(service.asignarRecurso(idEvento, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{idAsignacion}")
    public ResponseEntity<Void> removerRecurso(@PathVariable Long idEvento, @PathVariable Long idAsignacion) {
        service.removerRecurso(idEvento, idAsignacion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AsignacionRecursoResponseDTO>> listarRecursosAsignados(@PathVariable Long idEvento) {
        return ResponseEntity.ok(service.listarRecursosAsignados(idEvento));
    }
}
