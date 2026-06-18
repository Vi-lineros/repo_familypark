package cl.familypark.familyparkrecursos.config;

import cl.familypark.familyparkrecursos.model.CategoriaRecurso;
import cl.familypark.familyparkrecursos.model.Inventario;
import cl.familypark.familyparkrecursos.model.Recurso;
import cl.familypark.familyparkrecursos.repository.CategoriaRecursoRepository;
import cl.familypark.familyparkrecursos.repository.InventarioRepository;
import cl.familypark.familyparkrecursos.repository.RecursoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final CategoriaRecursoRepository categoriaRepo;
    private final RecursoRepository recursoRepo;
    private final InventarioRepository inventarioRepo;

    @Override
    public void run(String... args) throws Exception {
        if (categoriaRepo.count() == 0) {
            log.info("Sembrando base de datos de familypark-recursos...");

            // 1. Categorías
            CategoriaRecurso mobiliario = CategoriaRecurso.builder()
                    .nombre("Mobiliario")
                    .activa(true)
                    .build();

            CategoriaRecurso juegos = CategoriaRecurso.builder()
                    .nombre("Juegos")
                    .activa(true)
                    .build();

            CategoriaRecurso insumos = CategoriaRecurso.builder()
                    .nombre("Insumos y Consumibles")
                    .activa(true)
                    .build();

            mobiliario = categoriaRepo.save(mobiliario);
            juegos = categoriaRepo.save(juegos);
            insumos = categoriaRepo.save(insumos);

            // 2. Recursos
            Recurso mesa = Recurso.builder()
                    .nombre("Mesa Temática Cumpleaños")
                    .descripcion("Mesa decorada de alta calidad para catering y tarta.")
                    .categoria(mobiliario)
                    .precioUnitario(15000.0)
                    .activo(true)
                    .build();

            Recurso consola = Recurso.builder()
                    .nombre("Consola Arcade Retro Adicional")
                    .descripcion("Consola con pantalla LED de 24 pulgadas y 500 juegos clásicos.")
                    .categoria(juegos)
                    .precioUnitario(25000.0)
                    .activo(true)
                    .build();

            Recurso globos = Recurso.builder()
                    .nombre("Pack Decoración de Globos")
                    .descripcion("Arco de globos y pilares decorativos según colores a elección.")
                    .categoria(mobiliario)
                    .precioUnitario(30000.0)
                    .activo(true)
                    .build();

            Recurso pintacarita = Recurso.builder()
                    .nombre("Kit Pinta Caritas Completo")
                    .descripcion("Maquillaje hipoalergénico con brillos y plantillas de diseño.")
                    .categoria(juegos)
                    .precioUnitario(12000.0)
                    .activo(true)
                    .build();

            mesa = recursoRepo.save(mesa);
            consola = recursoRepo.save(consola);
            globos = recursoRepo.save(globos);
            pintacarita = recursoRepo.save(pintacarita);

            // 3. Inventario (Sucursal La Florida ID: 1, Sucursal Las Condes ID: 2)
            Inventario inv1 = Inventario.builder()
                    .recurso(mesa)
                    .idSucursal(1L)
                    .cantidadTotal(10)
                    .cantidadDisponible(10)
                    .build();

            Inventario inv2 = Inventario.builder()
                    .recurso(globos)
                    .idSucursal(1L)
                    .cantidadTotal(5)
                    .cantidadDisponible(5)
                    .build();

            Inventario inv3 = Inventario.builder()
                    .recurso(consola)
                    .idSucursal(1L)
                    .cantidadTotal(3)
                    .cantidadDisponible(3)
                    .build();

            Inventario inv4 = Inventario.builder()
                    .recurso(mesa)
                    .idSucursal(2L)
                    .cantidadTotal(8)
                    .cantidadDisponible(8)
                    .build();

            Inventario inv5 = Inventario.builder()
                    .recurso(pintacarita)
                    .idSucursal(2L)
                    .cantidadTotal(15)
                    .cantidadDisponible(15)
                    .build();

            inventarioRepo.saveAll(List.of(inv1, inv2, inv3, inv4, inv5));

            log.info("Datos sembrados con éxito en familypark-recursos. 3 Categorías, 4 Recursos y 5 Inventarios creados.");
        } else {
            log.info("La base de datos de familypark-recursos ya contiene registros. Omitiendo siembra.");
        }
    }
}
