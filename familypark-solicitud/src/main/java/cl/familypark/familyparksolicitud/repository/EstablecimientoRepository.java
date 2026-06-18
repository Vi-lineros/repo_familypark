package cl.familypark.familyparksolicitud.repository;

import cl.familypark.familyparksolicitud.model.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Establecimiento.
 */
@Repository
public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Long> {

    /** Retorna solo los establecimientos activos para mostrar al cliente. */
    List<Establecimiento> findByActivoTrue();

    /** Verifica existencia por nombre (usado en DataInitializer para evitar duplicados). */
    boolean existsByNombre(String nombre);
}
