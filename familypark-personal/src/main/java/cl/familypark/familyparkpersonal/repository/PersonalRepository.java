package cl.familypark.familyparkpersonal.repository;

import cl.familypark.familyparkpersonal.model.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {
    List<Personal> findByActivoTrue();
    Optional<Personal> findByCorreo(String correo);
    boolean existsByCorreoIgnoreCase(String correo);
    List<Personal> findByCargo_IdCargo(Long idCargo);
}
