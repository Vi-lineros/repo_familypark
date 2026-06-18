package cl.familypark.familyparkpersonal.repository;

import cl.familypark.familyparkpersonal.model.Disponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {
    List<Disponibilidad> findByPersonal_IdPersonal(Long idPersonal);
    List<Disponibilidad> findByPersonal_IdPersonalAndActivaTrue(Long idPersonal);
}
