package cl.familypark.familyparkrecursos.service;

import cl.familypark.familyparkrecursos.dto.request.InventarioRequestDTO;
import cl.familypark.familyparkrecursos.dto.response.CategoriaRecursoResponseDTO;
import cl.familypark.familyparkrecursos.dto.response.InventarioResponseDTO;
import cl.familypark.familyparkrecursos.dto.response.RecursoResponseDTO;
import cl.familypark.familyparkrecursos.exception.BadRequestException;
import cl.familypark.familyparkrecursos.exception.ResourceNotFoundException;
import cl.familypark.familyparkrecursos.model.Inventario;
import cl.familypark.familyparkrecursos.model.Recurso;
import cl.familypark.familyparkrecursos.repository.InventarioRepository;
import cl.familypark.familyparkrecursos.repository.RecursoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InventarioService {

    private final InventarioRepository inventarioRepo;
    private final RecursoRepository recursoRepo;

    @Transactional(readOnly = true)
    public List<InventarioResponseDTO> listarTodos() {
        log.debug("Listando todo el inventario.");
        return inventarioRepo.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InventarioResponseDTO> listarPorSucursal(Long idSucursal) {
        log.debug("Listando inventario para sucursal ID: {}", idSucursal);
        return inventarioRepo.findByIdSucursal(idSucursal).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InventarioResponseDTO obtenerPorId(Long id) {
        log.debug("Obteniendo inventario por ID: {}", id);
        Inventario inv = inventarioRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario", id));
        return toResponseDTO(inv);
    }

    public InventarioResponseDTO guardar(InventarioRequestDTO dto) {
        log.info("Guardando/Actualizando stock de recurso ID: {} para sucursal ID: {}", dto.getIdRecurso(), dto.getIdSucursal());
        Recurso recurso = recursoRepo.findById(dto.getIdRecurso())
                .orElseThrow(() -> new ResourceNotFoundException("Recurso", dto.getIdRecurso()));

        if (!recurso.getActivo()) {
            throw new BadRequestException("No se puede añadir al inventario un recurso inactivo.");
        }

        if (dto.getCantidadDisponible() > dto.getCantidadTotal()) {
            throw new BadRequestException("La cantidad disponible no puede exceder a la cantidad total.");
        }

        // Buscar si ya existe la relación
        Optional<Inventario> existente = inventarioRepo.findByRecurso_IdRecursoAndIdSucursal(dto.getIdRecurso(), dto.getIdSucursal());
        Inventario inv;
        if (existente.isPresent()) {
            inv = existente.get();
            inv.setCantidadTotal(dto.getCantidadTotal());
            inv.setCantidadDisponible(dto.getCantidadDisponible());
        } else {
            inv = Inventario.builder()
                    .recurso(recurso)
                    .idSucursal(dto.getIdSucursal())
                    .cantidadTotal(dto.getCantidadTotal())
                    .cantidadDisponible(dto.getCantidadDisponible())
                    .build();
        }

        Inventario guardado = inventarioRepo.save(inv);
        return toResponseDTO(guardado);
    }

    public InventarioResponseDTO descontarStock(Long idRecurso, Long idSucursal, int cantidad) {
        log.info("Descontando {} unidades de recurso ID: {} en sucursal ID: {}", cantidad, idRecurso, idSucursal);
        Inventario inv = inventarioRepo.findByRecurso_IdRecursoAndIdSucursal(idRecurso, idSucursal)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No se encontró registro en inventario para el recurso %d en la sucursal %d", idRecurso, idSucursal)));

        if (inv.getCantidadDisponible() < cantidad) {
            throw new BadRequestException(String.format(
                    "Stock insuficiente para el recurso '%s'. Disponible: %d, Solicitado: %d",
                    inv.getRecurso().getNombre(), inv.getCantidadDisponible(), cantidad));
        }

        inv.setCantidadDisponible(inv.getCantidadDisponible() - cantidad);
        Inventario guardado = inventarioRepo.save(inv);
        return toResponseDTO(guardado);
    }

    public InventarioResponseDTO devolverStock(Long idRecurso, Long idSucursal, int cantidad) {
        log.info("Devolviendo {} unidades de recurso ID: {} en sucursal ID: {}", cantidad, idRecurso, idSucursal);
        Inventario inv = inventarioRepo.findByRecurso_IdRecursoAndIdSucursal(idRecurso, idSucursal)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No se encontró registro en inventario para el recurso %d en la sucursal %d", idRecurso, idSucursal)));

        int nuevaCantDisp = inv.getCantidadDisponible() + cantidad;
        if (nuevaCantDisp > inv.getCantidadTotal()) {
            nuevaCantDisp = inv.getCantidadTotal(); // Tope de stock total
        }

        inv.setCantidadDisponible(nuevaCantDisp);
        Inventario guardado = inventarioRepo.save(inv);
        return toResponseDTO(guardado);
    }

    public InventarioResponseDTO toResponseDTO(Inventario inv) {
        return InventarioResponseDTO.builder()
                .idInventario(inv.getIdInventario())
                .recurso(RecursoResponseDTO.builder()
                        .idRecurso(inv.getRecurso().getIdRecurso())
                        .nombre(inv.getRecurso().getNombre())
                        .descripcion(inv.getRecurso().getDescripcion())
                        .precioUnitario(inv.getRecurso().getPrecioUnitario())
                        .activo(inv.getRecurso().getActivo())
                        .categoria(CategoriaRecursoResponseDTO.builder()
                                .idCategoria(inv.getRecurso().getCategoria().getIdCategoria())
                                .nombre(inv.getRecurso().getCategoria().getNombre())
                                .activa(inv.getRecurso().getCategoria().getActiva())
                                .build())
                        .build())
                .idSucursal(inv.getIdSucursal())
                .cantidadTotal(inv.getCantidadTotal())
                .cantidadDisponible(inv.getCantidadDisponible())
                .build();
    }
}
