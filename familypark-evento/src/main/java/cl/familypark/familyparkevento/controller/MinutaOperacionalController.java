package cl.familypark.familyparkevento.controller;

import cl.familypark.familyparkevento.dto.request.MinutaOperacionalRequestDTO;
import cl.familypark.familyparkevento.dto.response.MinutaDetalleResponseDTO;
import cl.familypark.familyparkevento.dto.response.MinutaOperacionalResponseDTO;
import cl.familypark.familyparkevento.service.MinutaOperacionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/eventos/{idEvento}/minuta")
@RequiredArgsConstructor
public class MinutaOperacionalController {

    private final MinutaOperacionalService service;

    @GetMapping
    public ResponseEntity<MinutaOperacionalResponseDTO> consultarMinuta(@PathVariable Long idEvento) {
        return ResponseEntity.ok(service.consultarMinuta(idEvento));
    }

    @GetMapping("/detalle")
    public ResponseEntity<MinutaDetalleResponseDTO> consultarMinutaDetalle(@PathVariable Long idEvento) {
        return ResponseEntity.ok(service.consultarMinutaDetalle(idEvento));
    }

    @PutMapping
    public ResponseEntity<MinutaOperacionalResponseDTO> actualizarMinuta(@PathVariable Long idEvento, @RequestBody MinutaOperacionalRequestDTO request) {
        return ResponseEntity.ok(service.actualizarMinuta(idEvento, request));
    }
}
