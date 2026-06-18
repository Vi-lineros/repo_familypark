package cl.familypark.familyparkevento.controller;

import cl.familypark.familyparkevento.dto.request.EventoRequestDTO;
import cl.familypark.familyparkevento.dto.response.EventoResponseDTO;
import cl.familypark.familyparkevento.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService service;

    @PostMapping
    public ResponseEntity<EventoResponseDTO> crearEvento(@Valid @RequestBody EventoRequestDTO request) {
        return new ResponseEntity<>(service.crearEvento(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> listarEventos() {
        return ResponseEntity.ok(service.listarEventos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> obtenerEvento(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerEventoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> actualizarEvento(@PathVariable Long id, @Valid @RequestBody EventoRequestDTO request) {
        return ResponseEntity.ok(service.actualizarEvento(id, request));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<EventoResponseDTO> cancelarEvento(@PathVariable Long id, @RequestParam String motivo) {
        return ResponseEntity.ok(service.cancelarEvento(id, motivo));
    }

    @PutMapping("/{id}/reprogramar")
    public ResponseEntity<EventoResponseDTO> reprogramarEvento(@PathVariable Long id, @Valid @RequestBody EventoRequestDTO request) {
        return ResponseEntity.ok(service.reprogramarEvento(id, request));
    }
}
