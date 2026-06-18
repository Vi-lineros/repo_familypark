package cl.familypark.familyparkrecursos.repository;

import cl.familypark.familyparkrecursos.model.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long> {
    List<Recurso> findByActivoTrue();
    List<Recurso> findByCategoria_IdCategoria(Long idCategoria);
    boolean existsByNombreIgnoreCase(String nombre);
}
