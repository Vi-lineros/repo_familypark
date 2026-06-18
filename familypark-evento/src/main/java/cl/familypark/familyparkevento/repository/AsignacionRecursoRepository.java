package cl.familypark.familyparkevento.repository;

import cl.familypark.familyparkevento.model.AsignacionRecurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionRecursoRepository extends JpaRepository<AsignacionRecurso, Long> {
    List<AsignacionRecurso> findByEvento_IdEvento(Long idEvento);
    void deleteByEvento_IdEvento(Long idEvento);
}
