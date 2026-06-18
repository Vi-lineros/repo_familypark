package cl.familypark.familyparkevento.controller;

import cl.familypark.familyparkevento.dto.request.AsignacionPersonalRequestDTO;
import cl.familypark.familyparkevento.dto.response.AsignacionPersonalResponseDTO;
import cl.familypark.familyparkevento.service.AsignacionPersonalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eventos/{idEvento}/personal")
@RequiredArgsConstructor
public class AsignacionPersonalController {

    private final AsignacionPersonalService service;

    @PostMapping
    public ResponseEntity<AsignacionPersonalResponseDTO> asignarPersonal(@PathVariable Long idEvento, @Valid @RequestBody AsignacionPersonalRequestDTO request) {
        return new ResponseEntity<>(service.asignarPersonal(idEvento, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{idAsignacion}")
    public ResponseEntity<Void> removerPersonal(@PathVariable Long idEvento, @PathVariable Long idAsignacion) {
        service.removerPersonal(idEvento, idAsignacion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AsignacionPersonalResponseDTO>> listarPersonalAsignado(@PathVariable Long idEvento) {
        return ResponseEntity.ok(service.listarPersonalAsignado(idEvento));
    }
}
