package cl.familypark.familyparkevento.repository;

import cl.familypark.familyparkevento.model.Evento;
import cl.familypark.familyparkevento.model.EstadoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    Optional<Evento> findByIdSolicitud(Long idSolicitud);
    List<Evento> findByEstado(EstadoEvento estado);
    List<Evento> findByIdSucursal(Long idSucursal);
    List<Evento> findByFecha(LocalDate fecha);
    List<Evento> findByFechaBetween(LocalDate inicio, LocalDate fin);
    boolean existsByIdSolicitud(Long idSolicitud);
}
