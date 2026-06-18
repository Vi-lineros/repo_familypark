package cl.familypark.familyparkpersonal.repository;

import cl.familypark.familyparkpersonal.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    List<Cargo> findByActivoTrue();
    boolean existsByNombreIgnoreCase(String nombre);
}
