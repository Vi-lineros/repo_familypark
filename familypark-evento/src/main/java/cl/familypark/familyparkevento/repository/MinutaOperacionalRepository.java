package cl.familypark.familyparkevento.repository;

import cl.familypark.familyparkevento.model.MinutaOperacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MinutaOperacionalRepository extends JpaRepository<MinutaOperacional, Long> {
    Optional<MinutaOperacional> findByEvento_IdEvento(Long idEvento);
}
