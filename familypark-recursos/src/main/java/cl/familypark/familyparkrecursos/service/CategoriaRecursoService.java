package cl.familypark.familyparkrecursos.service;

import cl.familypark.familyparkrecursos.dto.request.CategoriaRecursoRequestDTO;
import cl.familypark.familyparkrecursos.dto.response.CategoriaRecursoResponseDTO;
import cl.familypark.familyparkrecursos.exception.ConflictException;
import cl.familypark.familyparkrecursos.exception.ResourceNotFoundException;
import cl.familypark.familyparkrecursos.model.CategoriaRecurso;
import cl.familypark.familyparkrecursos.repository.CategoriaRecursoRepository;
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
public class CategoriaRecursoService {

    private final CategoriaRecursoRepository categoriaRepo;

    @Transactional(readOnly = true)
    public List<CategoriaRecursoResponseDTO> listarTodas() {
        log.debug("Listando todas las categorías de recursos.");
        return categoriaRepo.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoriaRecursoResponseDTO> listarActivas() {
        log.debug("Listando categorías de recursos activas.");
        return categoriaRepo.findByActivaTrue().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaRecursoResponseDTO obtenerPorId(Long id) {
        log.debug("Obteniendo categoría por ID: {}", id);
        CategoriaRecurso cat = categoriaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría de Recurso", id));
        return toResponseDTO(cat);
    }

    public CategoriaRecursoResponseDTO crear(CategoriaRecursoRequestDTO dto) {
        log.info("Creando categoría de recurso: {}", dto.getNombre());
        if (categoriaRepo.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new ConflictException("Ya existe una categoría con el nombre: " + dto.getNombre());
        }

        CategoriaRecurso cat = CategoriaRecurso.builder()
                .nombre(dto.getNombre())
                .activa(true)
                .build();

        CategoriaRecurso guardada = categoriaRepo.save(cat);
        return toResponseDTO(guardada);
    }

    public CategoriaRecursoResponseDTO actualizar(Long id, CategoriaRecursoRequestDTO dto) {
        log.info("Actualizando categoría de recurso con ID: {}", id);
        CategoriaRecurso cat = categoriaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría de Recurso", id));

        if (!cat.getNombre().equalsIgnoreCase(dto.getNombre()) && 
                categoriaRepo.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new ConflictException("Ya existe otra categoría con el nombre: " + dto.getNombre());
        }

        cat.setNombre(dto.getNombre());

        CategoriaRecurso actualizada = categoriaRepo.save(cat);
        return toResponseDTO(actualizada);
    }

    public CategoriaRecursoResponseDTO desactivar(Long id) {
        log.info("Desactivando categoría de recurso con ID: {}", id);
        CategoriaRecurso cat = categoriaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría de Recurso", id));
        cat.setActiva(false);
        CategoriaRecurso guardada = categoriaRepo.save(cat);
        return toResponseDTO(guardada);
    }

    public CategoriaRecursoResponseDTO activar(Long id) {
        log.info("Activando categoría de recurso con ID: {}", id);
        CategoriaRecurso cat = categoriaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría de Recurso", id));
        cat.setActiva(true);
        CategoriaRecurso guardada = categoriaRepo.save(cat);
        return toResponseDTO(guardada);
    }

    public CategoriaRecursoResponseDTO toResponseDTO(CategoriaRecurso cat) {
        return CategoriaRecursoResponseDTO.builder()
                .idCategoria(cat.getIdCategoria())
                .nombre(cat.getNombre())
                .activa(cat.getActiva())
                .build();
    }
}
