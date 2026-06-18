package cl.familypark.familyparkevento;

import cl.familypark.familyparkevento.model.EstadoEvento;
import cl.familypark.familyparkevento.model.Evento;
import cl.familypark.familyparkevento.model.MinutaOperacional;
import cl.familypark.familyparkevento.repository.EventoRepository;
import cl.familypark.familyparkevento.repository.MinutaOperacionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final EventoRepository eventoRepository;
    private final MinutaOperacionalRepository minutaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (eventoRepository.count() == 0) {
            Evento evento1 = Evento.builder()
                    .idSolicitud(1L)
                    .idSucursal(1L)
                    .idSala(1L)
                    .nombreSucursal("Sucursal Santiago Centro")
                    .nombreSala("Sala Mario Bros")
                    .fecha(LocalDate.now().plusDays(10))
                    .horaInicio(LocalTime.of(15, 0))
                    .horaFin(LocalTime.of(18, 0))
                    .nombreCumpleanero("Juanito Pérez")
                    .edadCumpleanero(8)
                    .cantidadNinos(15)
                    .cantidadAdultos(5)
                    .precioTotal(150000.0)
                    .requerimientosEspeciales("Silla de ruedas para abuelo")
                    .estado(EstadoEvento.PROGRAMADO)
                    .fechaCreacion(LocalDateTime.now())
                    .build();
            eventoRepository.save(evento1);

            MinutaOperacional minuta1 = MinutaOperacional.builder()
                    .evento(evento1)
                    .checklistJson("[{\"tarea\":\"Limpiar sala\",\"completada\":false}]")
                    .cronogramaJson("[{\"hora\":\"15:00\",\"actividad\":\"Llegada\"}]")
                    .observaciones("Primera minuta generada")
                    .fechaGeneracion(LocalDateTime.now())
                    .build();
            minutaRepository.save(minuta1);

            System.out.println("--- Datos de prueba de Eventos cargados exitosamente ---");
        }
    }
}
