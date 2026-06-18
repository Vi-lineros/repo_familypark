package cl.familypark.familyparksucursal.repository;

import cl.familypark.familyparksucursal.model.Sala;
import cl.familypark.familyparksucursal.model.TipoLocal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
    List<Sala> findBySucursal_IdSucursal(Long idSucursal);
    List<Sala> findBySucursal_IdSucursalAndActivaTrue(Long idSucursal);
    List<Sala> findByTipoLocal(TipoLocal tipoLocal);
    List<Sala> findByActivaTrue();
}
