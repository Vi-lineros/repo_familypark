package cl.familypark.familyparkrecursos.controller;

import cl.familypark.familyparkrecursos.dto.request.InventarioRequestDTO;
import cl.familypark.familyparkrecursos.dto.response.InventarioResponseDTO;
import cl.familypark.familyparkrecursos.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventario")
@RequiredArgsConstructor
@Slf4j
public class InventarioController {

    private final InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> listarTodos() {
        log.info("GET /api/v1/inventario");
        return ResponseEntity.ok(inventarioService.listarTodos());
    }

    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<InventarioResponseDTO>> listarPorSucursal(@PathVariable Long idSucursal) {
        log.info("GET /api/v1/inventario/sucursal/{}", idSucursal);
        return ResponseEntity.ok(inventarioService.listarPorSucursal(idSucursal));
    }

    @PostMapping
    public ResponseEntity<InventarioResponseDTO> guardar(@Valid @RequestBody InventarioRequestDTO requestDTO) {
        log.info("POST /api/v1/inventario - recurso: {}, sucursal: {}", requestDTO.getIdRecurso(), requestDTO.getIdSucursal());
        InventarioResponseDTO response = inventarioService.guardar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/descontar")
    public ResponseEntity<InventarioResponseDTO> descontarStock(
            @RequestParam(name = "idRecurso") Long idRecurso,
            @RequestParam(name = "idSucursal") Long idSucursal,
            @RequestParam(name = "cantidad") int cantidad) {
        log.info("POST /api/v1/inventario/descontar - recurso: {}, sucursal: {}, cant: {}", idRecurso, idSucursal, cantidad);
        return ResponseEntity.ok(inventarioService.descontarStock(idRecurso, idSucursal, cantidad));
    }

    @PostMapping("/devolver")
    public ResponseEntity<InventarioResponseDTO> devolverStock(
            @RequestParam(name = "idRecurso") Long idRecurso,
            @RequestParam(name = "idSucursal") Long idSucursal,
            @RequestParam(name = "cantidad") int cantidad) {
        log.info("POST /api/v1/inventario/devolver - recurso: {}, sucursal: {}, cant: {}", idRecurso, idSucursal, cantidad);
        return ResponseEntity.ok(inventarioService.devolverStock(idRecurso, idSucursal, cantidad));
    }
}
