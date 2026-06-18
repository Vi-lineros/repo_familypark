package cl.familypark.familyparkpersonal.config;

import cl.familypark.familyparkpersonal.model.Cargo;
import cl.familypark.familyparkpersonal.model.Disponibilidad;
import cl.familypark.familyparkpersonal.model.Personal;
import cl.familypark.familyparkpersonal.repository.CargoRepository;
import cl.familypark.familyparkpersonal.repository.DisponibilidadRepository;
import cl.familypark.familyparkpersonal.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final CargoRepository cargoRepo;
    private final PersonalRepository personalRepo;
    private final DisponibilidadRepository disponibilidadRepo;

    @Override
    public void run(String... args) throws Exception {
        if (cargoRepo.count() == 0) {
            log.info("Sembrando base de datos de familypark-personal...");

            // 1. Cargos
            Cargo animador = Cargo.builder()
                    .nombre("Animador")
                    .descripcion("Encargado de dirigir los juegos, cantar el cumpleaños y mantener la energía del evento.")
                    .activo(true)
                    .build();

            Cargo coordinador = Cargo.builder()
                    .nombre("Coordinador de Eventos")
                    .descripcion("Responsable general del desarrollo del evento, control de minutas y contacto con el cliente.")
                    .activo(true)
                    .build();

            Cargo cajero = Cargo.builder()
                    .nombre("Cajero")
                    .descripcion("Atención en recepción y cobros.")
                    .activo(true)
                    .build();

            animador = cargoRepo.save(animador);
            coordinador = cargoRepo.save(coordinador);
            cajero = cargoRepo.save(cajero);

            // 2. Personal
            Personal juan = Personal.builder()
                    .nombre("Juan")
                    .apellido("Pérez")
                    .correo("juan.perez@familypark.cl")
                    .telefono("+56911111111")
                    .cargo(animador)
                    .activo(true)
                    .build();

            Personal maria = Personal.builder()
                    .nombre("María")
                    .apellido("González")
                    .correo("maria.gonzalez@familypark.cl")
                    .telefono("+56922222222")
                    .cargo(coordinador)
                    .activo(true)
                    .build();

            juan = personalRepo.save(juan);
            maria = personalRepo.save(maria);

            // 3. Disponibilidad Juan (Animador)
            Disponibilidad dj1 = Disponibilidad.builder()
                    .personal(juan)
                    .diaSemana(DayOfWeek.SATURDAY)
                    .horaInicio(LocalTime.of(10, 0))
                    .horaFin(LocalTime.of(20, 0))
                    .activa(true)
                    .build();

            Disponibilidad dj2 = Disponibilidad.builder()
                    .personal(juan)
                    .diaSemana(DayOfWeek.SUNDAY)
                    .horaInicio(LocalTime.of(10, 0))
                    .horaFin(LocalTime.of(18, 0))
                    .activa(true)
                    .build();

            // Disponibilidad María (Coordinador)
            Disponibilidad dm1 = Disponibilidad.builder()
                    .personal(maria)
                    .diaSemana(DayOfWeek.FRIDAY)
                    .horaInicio(LocalTime.of(15, 0))
                    .horaFin(LocalTime.of(22, 0))
                    .activa(true)
                    .build();

            Disponibilidad dm2 = Disponibilidad.builder()
                    .personal(maria)
                    .diaSemana(DayOfWeek.SATURDAY)
                    .horaInicio(LocalTime.of(9, 0))
                    .horaFin(LocalTime.of(21, 0))
                    .activa(true)
                    .build();

            disponibilidadRepo.saveAll(List.of(dj1, dj2, dm1, dm2));

            log.info("Datos sembrados con éxito en familypark-personal. 3 Cargos, 2 Trabajadores y 4 Disponibilidades creadas.");
        } else {
            log.info("La base de datos de familypark-personal ya contiene registros. Omitiendo siembra.");
        }
    }
}
