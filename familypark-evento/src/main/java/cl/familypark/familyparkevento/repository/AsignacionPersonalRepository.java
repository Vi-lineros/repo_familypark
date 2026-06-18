package cl.familypark.familyparkevento.repository;

import cl.familypark.familyparkevento.model.AsignacionPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionPersonalRepository extends JpaRepository<AsignacionPersonal, Long> {
    List<AsignacionPersonal> findByEvento_IdEvento(Long idEvento);
    boolean existsByEvento_IdEventoAndIdPersonal(Long idEvento, Long idPersonal);
    void deleteByEvento_IdEvento(Long idEvento);
}
