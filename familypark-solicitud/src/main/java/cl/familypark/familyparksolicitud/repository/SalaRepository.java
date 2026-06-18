package cl.familypark.familyparksolicitud.repository;

import cl.familypark.familyparksolicitud.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Sala.
 */
@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {

    /** Retorna solo las salas activas. */
    List<Sala> findByActivaTrue();

    /** Retorna las salas activas de un establecimiento específico. */
    List<Sala> findByEstablecimiento_IdEstablecimientoAndActivaTrue(Long idEstablecimiento);

    /** Usado en el DataInitializer para evitar duplicados. */
    boolean existsByNombre(String nombre);
}
