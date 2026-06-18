package cl.familypark.familyparkrecursos.repository;

import cl.familypark.familyparkrecursos.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findByIdSucursal(Long idSucursal);
    Optional<Inventario> findByRecurso_IdRecursoAndIdSucursal(Long idRecurso, Long idSucursal);
}
