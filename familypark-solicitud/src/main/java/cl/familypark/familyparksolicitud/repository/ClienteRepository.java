package cl.familypark.familyparksolicitud.repository;

import cl.familypark.familyparksolicitud.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Cliente.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /** Busca un cliente por su correo electrónico (usado en crearSolicitud para deduplicación). */
    Optional<Cliente> findByCorreo(String correo);

    /** Verifica si existe un cliente con ese correo (útil para validaciones). */
    boolean existsByCorreo(String correo);
}
