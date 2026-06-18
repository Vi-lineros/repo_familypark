package cl.familypark.familyparksucursal.config;

import cl.familypark.familyparksucursal.model.Sala;
import cl.familypark.familyparksucursal.model.Sucursal;
import cl.familypark.familyparksucursal.model.TipoLocal;
import cl.familypark.familyparksucursal.repository.SalaRepository;
import cl.familypark.familyparksucursal.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final SucursalRepository sucursalRepo;
    private final SalaRepository salaRepo;

    @Override
    public void run(String... args) throws Exception {
        if (sucursalRepo.count() == 0) {
            log.info("Sembrando base de datos de familypark-sucursal...");

            Sucursal florida = Sucursal.builder()
                    .nombre("FamilyPark La Florida")
                    .ciudad("Santiago")
                    .direccion("Av. La Florida 1234")
                    .telefono("22222222")
                    .correo("florida@familypark.cl")
                    .activa(true)
                    .build();

            Sucursal lasCondes = Sucursal.builder()
                    .nombre("FamilyPark Las Condes")
                    .ciudad("Santiago")
                    .direccion("Av. Kennedy 5600")
                    .telefono("22222223")
                    .correo("condes@familypark.cl")
                    .activa(true)
                    .build();

            florida = sucursalRepo.save(florida);
            lasCondes = sucursalRepo.save(lasCondes);

            Sala sala1 = Sala.builder()
                    .nombre("Zona Arcade Florida")
                    .tipoLocal(TipoLocal.ARCADE)
                    .capacidadMaxima(50)
                    .descripcion("Área con máquinas recreativas clásicas y modernas, simuladores y realidad virtual.")
                    .sucursal(florida)
                    .activa(true)
                    .build();

            Sala sala2 = Sala.builder()
                    .nombre("Trampolines Pro Florida")
                    .tipoLocal(TipoLocal.PARQUE_TRAMPOLINES)
                    .capacidadMaxima(40)
                    .descripcion("Área de saltos libres con trampolines olímpicos, piscina de espuma y muro de escalada.")
                    .sucursal(florida)
                    .activa(true)
                    .build();

            Sala sala3 = Sala.builder()
                    .nombre("Jump Zone Condes")
                    .tipoLocal(TipoLocal.PARQUE_TRAMPOLINES)
                    .capacidadMaxima(60)
                    .descripcion("El parque de trampolines más grande del sector oriente, ideal para acrobacias.")
                    .sucursal(lasCondes)
                    .activa(true)
                    .build();

            Sala sala4 = Sala.builder()
                    .nombre("Retro Arcade Condes")
                    .tipoLocal(TipoLocal.ARCADE)
                    .capacidadMaxima(30)
                    .descripcion("Espacio nostálgico con pinballs, clásicos de los 90 y zona de cafetería.")
                    .sucursal(lasCondes)
                    .activa(true)
                    .build();

            salaRepo.saveAll(List.of(sala1, sala2, sala3, sala4));

            log.info("Datos sembrados con éxito. 2 Sucursales y 4 Salas creadas.");
        } else {
            log.info("La base de datos de familypark-sucursal ya contiene registros. Omitiendo siembra.");
        }
    }
}
