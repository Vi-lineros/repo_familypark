package cl.familypark.familyparksolicitud.config;

import cl.familypark.familyparksolicitud.model.*;
import cl.familypark.familyparksolicitud.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Inicializador de datos de prueba para FamilyPark.
 *
 * Se ejecuta automáticamente al arrancar la aplicación.
 * Solo inserta datos si las tablas están vacías (sin duplicados).
 *
 * Inserta:
 *  - 2 Establecimientos
 *  - 3 Salas (distribuidas entre establecimientos)
 *  - 3 Kits de Servicio
 *  - 5 Clientes ficticios
 *  - 5 Solicitudes de ejemplo
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final EstablecimientoRepository establecimientoRepo;
    private final SalaRepository            salaRepo;
    private final KitServicioRepository     kitRepo;
    private final ClienteRepository         clienteRepo;
    private final SolicitudEventoRepository solicitudRepo;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("========================================");
        log.info("  FamilyPark - Inicializando datos...  ");
        log.info("========================================");

        List<Establecimiento> establecimientos = inicializarEstablecimientos();
        List<Sala>            salas            = inicializarSalas(establecimientos);
        List<KitServicio>     kits             = inicializarKits();
        List<Cliente>         clientes         = inicializarClientes();
                                                 inicializarSolicitudes(clientes, establecimientos, salas, kits);

        log.info("========================================");
        log.info("  Inicialización completada.           ");
        log.info("========================================");
    }

    // ── Establecimientos ──────────────────────────────────────

    private List<Establecimiento> inicializarEstablecimientos() {
        if (establecimientoRepo.count() > 0) {
            log.info("[SKIP] Establecimientos ya existen en BD ({} registros).",
                    establecimientoRepo.count());
            return establecimientoRepo.findAll();
        }

        log.info("[INSERT] Creando establecimientos...");

        Establecimiento maipu = establecimientoRepo.save(
                Establecimiento.builder()
                        .nombre("FamilyPark Maipú")
                        .ciudad("Santiago")
                        .direccion("Av. Pajaritos 1234, Maipú")
                        .telefono("+56 2 2345 6789")
                        .correo("maipu@familypark.cl")
                        .capacidadMaxima(120)
                        .activo(true)
                        .build());

        Establecimiento centro = establecimientoRepo.save(
                Establecimiento.builder()
                        .nombre("FamilyPark Santiago Centro")
                        .ciudad("Santiago")
                        .direccion("Av. Libertador Bernardo O'Higgins 890, Santiago")
                        .telefono("+56 2 2234 5678")
                        .correo("centro@familypark.cl")
                        .capacidadMaxima(100)
                        .activo(true)
                        .build());

        log.info("[OK] {} establecimientos creados.", 2);
        return List.of(maipu, centro);
    }

    // ── Salas ─────────────────────────────────────────────────

    private List<Sala> inicializarSalas(List<Establecimiento> establecimientos) {
        if (salaRepo.count() > 0) {
            log.info("[SKIP] Salas ya existen en BD ({} registros).", salaRepo.count());
            return salaRepo.findAll();
        }

        log.info("[INSERT] Creando salas...");

        Establecimiento maipu  = establecimientos.get(0);
        Establecimiento centro = establecimientos.get(1);

        Sala piratas = salaRepo.save(Sala.builder()
                .nombre("Sala Piratas")
                .tipoSala("Aventura")
                .capacidadMaxima(40)
                .descripcion("Sala temática de piratas con barco de madera, tesoros y personajes del mar.")
                .activa(true)
                .establecimiento(maipu)
                .build());

        Sala princesas = salaRepo.save(Sala.builder()
                .nombre("Sala Princesas")
                .tipoSala("Fantasía")
                .capacidadMaxima(30)
                .descripcion("Sala decorada con castillos, princesas y carruajes de cuento de hadas.")
                .activa(true)
                .establecimiento(maipu)
                .build());

        Sala aventuras = salaRepo.save(Sala.builder()
                .nombre("Sala Aventuras")
                .tipoSala("Exploración")
                .capacidadMaxima(50)
                .descripcion("Sala multiuso para exploración, ciencia y aventuras al estilo selva.")
                .activa(true)
                .establecimiento(centro)
                .build());

        log.info("[OK] {} salas creadas.", 3);
        return List.of(piratas, princesas, aventuras);
    }

    // ── Kits de Servicio ──────────────────────────────────────

    private List<KitServicio> inicializarKits() {
        if (kitRepo.count() > 0) {
            log.info("[SKIP] Kits ya existen en BD ({} registros).", kitRepo.count());
            return kitRepo.findAll();
        }

        log.info("[INSERT] Creando kits de servicio...");

        KitServicio basico = kitRepo.save(KitServicio.builder()
                .nombre("Básico")
                .descripcion("Ideal para celebraciones íntimas. Incluye torta, decoración y 1 hora de animación.")
                .precioBase(new BigDecimal("89990"))
                .precioPorNino(new BigDecimal("3500"))
                .duracionMinutos(90)
                .minimoNinos(5)
                .maximoNinos(15)
                .activo(true)
                .build());

        KitServicio premium = kitRepo.save(KitServicio.builder()
                .nombre("Premium")
                .descripcion("Paquete completo. Torta personalizada, 2 personajes, buffet y 2 horas de show.")
                .precioBase(new BigDecimal("159990"))
                .precioPorNino(new BigDecimal("5000"))
                .duracionMinutos(150)
                .minimoNinos(10)
                .maximoNinos(30)
                .activo(true)
                .build());

        KitServicio deluxe = kitRepo.save(KitServicio.builder()
                .nombre("Deluxe")
                .descripcion("La experiencia máxima. Todo incluido: video, DJ, show de magia, piñata y más.")
                .precioBase(new BigDecimal("299990"))
                .precioPorNino(new BigDecimal("7500"))
                .duracionMinutos(240)
                .minimoNinos(15)
                .maximoNinos(50)
                .activo(true)
                .build());

        log.info("[OK] {} kits creados.", 3);
        return List.of(basico, premium, deluxe);
    }

    // ── Clientes ──────────────────────────────────────────────

    private List<Cliente> inicializarClientes() {
        if (clienteRepo.count() > 0) {
            log.info("[SKIP] Clientes ya existen en BD ({} registros).", clienteRepo.count());
            return clienteRepo.findAll();
        }

        log.info("[INSERT] Creando clientes ficticios...");

        Cliente c1 = clienteRepo.save(Cliente.builder()
                .nombre("María").apellido("González")
                .correo("maria.gonzalez@gmail.com").telefono("+56 9 1234 5678")
                .direccion("Calle Las Rosas 123, Maipú, Santiago").build());

        Cliente c2 = clienteRepo.save(Cliente.builder()
                .nombre("Juan").apellido("Pérez")
                .correo("juan.perez@hotmail.com").telefono("+56 9 2345 6789")
                .direccion("Av. Independencia 456, Santiago Centro").build());

        Cliente c3 = clienteRepo.save(Cliente.builder()
                .nombre("Ana").apellido("Martínez")
                .correo("ana.martinez@yahoo.com").telefono("+56 9 3456 7890")
                .direccion("Calle Las Flores 789, Pudahuel, Santiago").build());

        Cliente c4 = clienteRepo.save(Cliente.builder()
                .nombre("Carlos").apellido("López")
                .correo("carlos.lopez@gmail.com").telefono("+56 9 4567 8901")
                .direccion("Av. Lo Espejo 321, Lo Espejo, Santiago").build());

        Cliente c5 = clienteRepo.save(Cliente.builder()
                .nombre("Sofía").apellido("Ramírez")
                .correo("sofia.ramirez@outlook.com").telefono("+56 9 5678 9012")
                .direccion("Calle Los Álamos 654, Cerrillos, Santiago").build());

        log.info("[OK] {} clientes creados.", 5);
        return List.of(c1, c2, c3, c4, c5);
    }

    // ── Solicitudes de Ejemplo ────────────────────────────────

    private void inicializarSolicitudes(
            List<Cliente>         clientes,
            List<Establecimiento> establecimientos,
            List<Sala>            salas,
            List<KitServicio>     kits) {

        if (solicitudRepo.count() > 0) {
            log.info("[SKIP] Solicitudes ya existen en BD ({} registros).", solicitudRepo.count());
            return;
        }

        log.info("[INSERT] Creando solicitudes de ejemplo...");

        LocalDate hoy = LocalDate.now();

        // Solicitud 1 — María, Sala Piratas, Kit Básico
        solicitudRepo.save(SolicitudEvento.builder()
                .cliente(clientes.get(0))
                .establecimiento(establecimientos.get(0))
                .sala(salas.get(0))
                .kitServicio(kits.get(0))
                .fechaSolicitada(hoy.plusDays(10))
                .horaInicio(LocalTime.of(15, 0))
                .horaFin(LocalTime.of(16, 30))
                .cantidadNinos(10)
                .cantidadAdultos(5)
                .nombreCumpleanero("Tomás")
                .edadCumpleanero(5)
                .requerimientosEspeciales("Decoración en azul marino y dorado.")
                .estado(EstadoSolicitud.PENDIENTE)
                .build());

        // Solicitud 2 — Juan, Sala Princesas, Kit Premium
        solicitudRepo.save(SolicitudEvento.builder()
                .cliente(clientes.get(1))
                .establecimiento(establecimientos.get(0))
                .sala(salas.get(1))
                .kitServicio(kits.get(1))
                .fechaSolicitada(hoy.plusDays(15))
                .horaInicio(LocalTime.of(16, 0))
                .horaFin(LocalTime.of(18, 30))
                .cantidadNinos(20)
                .cantidadAdultos(10)
                .nombreCumpleanero("Isabella")
                .edadCumpleanero(7)
                .requerimientosEspeciales("Sin gluten en la torta. Alergia a los mariscos.")
                .estado(EstadoSolicitud.PENDIENTE)
                .build());

        // Solicitud 3 — Ana, Sala Aventuras, Kit Deluxe
        solicitudRepo.save(SolicitudEvento.builder()
                .cliente(clientes.get(2))
                .establecimiento(establecimientos.get(1))
                .sala(salas.get(2))
                .kitServicio(kits.get(2))
                .fechaSolicitada(hoy.plusDays(20))
                .horaInicio(LocalTime.of(14, 0))
                .horaFin(LocalTime.of(18, 0))
                .cantidadNinos(25)
                .cantidadAdultos(15)
                .nombreCumpleanero("Mateo")
                .edadCumpleanero(8)
                .requerimientosEspeciales("Temática dinosaurios. Necesitan rampa de acceso.")
                .estado(EstadoSolicitud.CONFIRMADO)
                .build());

        // Solicitud 4 — Carlos, Sala Piratas, Kit Premium
        solicitudRepo.save(SolicitudEvento.builder()
                .cliente(clientes.get(3))
                .establecimiento(establecimientos.get(0))
                .sala(salas.get(0))
                .kitServicio(kits.get(1))
                .fechaSolicitada(hoy.plusDays(25))
                .horaInicio(LocalTime.of(11, 0))
                .horaFin(LocalTime.of(13, 30))
                .cantidadNinos(15)
                .cantidadAdultos(8)
                .nombreCumpleanero("Valentina")
                .edadCumpleanero(6)
                .requerimientosEspeciales(null)
                .estado(EstadoSolicitud.PENDIENTE)
                .build());

        // Solicitud 5 — Sofía, Sala Aventuras, Kit Básico
        solicitudRepo.save(SolicitudEvento.builder()
                .cliente(clientes.get(4))
                .establecimiento(establecimientos.get(1))
                .sala(salas.get(2))
                .kitServicio(kits.get(0))
                .fechaSolicitada(hoy.plusDays(30))
                .horaInicio(LocalTime.of(17, 0))
                .horaFin(LocalTime.of(18, 30))
                .cantidadNinos(8)
                .cantidadAdultos(4)
                .nombreCumpleanero("Santiago")
                .edadCumpleanero(4)
                .requerimientosEspeciales("Pastel vegano sin lactosa.")
                .estado(EstadoSolicitud.PENDIENTE)
                .build());

        log.info("[OK] {} solicitudes de ejemplo creadas.", 5);
    }
}
