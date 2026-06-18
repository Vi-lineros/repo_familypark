package cl.familypark.familyparksolicitud.repository;

import cl.familypark.familyparksolicitud.model.EstadoSolicitud;
import cl.familypark.familyparksolicitud.model.SolicitudEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio JPA para la entidad SolicitudEvento.
 */
@Repository
public interface SolicitudEventoRepository extends JpaRepository<SolicitudEvento, Long> {

    /** Lista todas las solicitudes de un cliente específico. */
    List<SolicitudEvento> findByCliente_IdCliente(Long idCliente);

    /** Lista solicitudes filtradas por estado. */
    List<SolicitudEvento> findByEstado(EstadoSolicitud estado);

    /** Lista solicitudes para una fecha específica (útil para ver disponibilidad). */
    List<SolicitudEvento> findByFechaSolicitada(LocalDate fecha);

    /** Lista solicitudes de una sala en una fecha dada (para validar disponibilidad). */
    List<SolicitudEvento> findBySala_IdSalaAndFechaSolicitada(Long idSala, LocalDate fecha);
}
