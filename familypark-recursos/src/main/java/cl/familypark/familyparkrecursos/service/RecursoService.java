package cl.familypark.familyparkrecursos.service;

import cl.familypark.familyparkrecursos.dto.request.RecursoRequestDTO;
import cl.familypark.familyparkrecursos.dto.response.CategoriaRecursoResponseDTO;
import cl.familypark.familyparkrecursos.dto.response.RecursoResponseDTO;
import cl.familypark.familyparkrecursos.exception.BadRequestException;
import cl.familypark.familyparkrecursos.exception.ConflictException;
import cl.familypark.familyparkrecursos.exception.ResourceNotFoundException;
import cl.familypark.familyparkrecursos.model.CategoriaRecurso;
import cl.familypark.familyparkrecursos.model.Recurso;
import cl.familypark.familyparkrecursos.repository.CategoriaRecursoRepository;
import cl.familypark.familyparkrecursos.repository.RecursoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RecursoService {

    private final RecursoRepository recursoRepo;
    private final CategoriaRecursoRepository categoriaRepo;

    @Transactional(readOnly = true)
    public List<RecursoResponseDTO> listarTodos() {
        log.debug("Listando todos los recursos.");
        return recursoRepo.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecursoResponseDTO> listarActivos() {
        log.debug("Listando recursos activos.");
        return recursoRepo.findByActivoTrue().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RecursoResponseDTO obtenerPorId(Long id) {
        log.debug("Obteniendo recurso por ID: {}", id);
        Recurso recurso = recursoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso", id));
        return toResponseDTO(recurso);
    }

    public RecursoResponseDTO crear(RecursoRequestDTO dto) {
        log.info("Creando recurso: {}", dto.getNombre());
        if (recursoRepo.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new ConflictException("Ya existe un recurso con el nombre: " + dto.getNombre());
        }

        CategoriaRecurso cat = categoriaRepo.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría de Recurso", dto.getIdCategoria()));
        if (!cat.getActiva()) {
            throw new BadRequestException("No se puede asignar una categoría inactiva.");
        }

        Recurso recurso = Recurso.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .categoria(cat)
                .precioUnitario(dto.getPrecioUnitario())
                .activo(true)
                .build();

        Recurso guardado = recursoRepo.save(recurso);
        return toResponseDTO(guardado);
    }

    public RecursoResponseDTO actualizar(Long id, RecursoRequestDTO dto) {
        log.info("Actualizando recurso con ID: {}", id);
        Recurso recurso = recursoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso", id));

        if (!recurso.getNombre().equalsIgnoreCase(dto.getNombre()) && 
                recursoRepo.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new ConflictException("Ya existe otro recurso con el nombre: " + dto.getNombre());
        }

        CategoriaRecurso cat = categoriaRepo.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría de Recurso", dto.getIdCategoria()));
        if (!cat.getActiva()) {
            throw new BadRequestException("No se puede asignar una categoría inactiva.");
        }

        recurso.setNombre(dto.getNombre());
        recurso.setDescripcion(dto.getDescripcion());
        recurso.setCategoria(cat);
        recurso.setPrecioUnitario(dto.getPrecioUnitario());

        Recurso actualizado = recursoRepo.save(recurso);
        return toResponseDTO(actualizado);
    }

    public RecursoResponseDTO desactivar(Long id) {
        log.info("Desactivando recurso con ID: {}", id);
        Recurso recurso = recursoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso", id));
        recurso.setActivo(false);
        Recurso guardado = recursoRepo.save(recurso);
        return toResponseDTO(guardado);
    }

    public RecursoResponseDTO activar(Long id) {
        log.info("Activando recurso con ID: {}", id);
        Recurso recurso = recursoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso", id));
        if (!recurso.getCategoria().getActiva()) {
            throw new BadRequestException("No se puede activar el recurso porque su categoría está inactiva.");
        }
        recurso.setActivo(true);
        Recurso guardado = recursoRepo.save(recurso);
        return toResponseDTO(guardado);
    }

    public RecursoResponseDTO toResponseDTO(Recurso r) {
        return RecursoResponseDTO.builder()
                .idRecurso(r.getIdRecurso())
                .nombre(r.getNombre())
                .descripcion(r.getDescripcion())
                .categoria(CategoriaRecursoResponseDTO.builder()
                        .idCategoria(r.getCategoria().getIdCategoria())
                        .nombre(r.getCategoria().getNombre())
                        .activa(r.getCategoria().getActiva())
                        .build())
                .precioUnitario(r.getPrecioUnitario())
                .activo(r.getActivo())
                .build();
    }
}
