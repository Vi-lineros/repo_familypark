package cl.familypark.familyparksolicitud.repository;

import cl.familypark.familyparksolicitud.model.KitServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad KitServicio.
 */
@Repository
public interface KitServicioRepository extends JpaRepository<KitServicio, Long> {

    /** Retorna solo los kits activos para que el cliente pueda seleccionarlos. */
    List<KitServicio> findByActivoTrue();

    /** Usado en el DataInitializer para evitar duplicados. */
    boolean existsByNombre(String nombre);
}
