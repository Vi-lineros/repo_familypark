package cl.familypark.familyparkrecursos.repository;

import cl.familypark.familyparkrecursos.model.CategoriaRecurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRecursoRepository extends JpaRepository<CategoriaRecurso, Long> {
    List<CategoriaRecurso> findByActivaTrue();
    boolean existsByNombreIgnoreCase(String nombre);
}
