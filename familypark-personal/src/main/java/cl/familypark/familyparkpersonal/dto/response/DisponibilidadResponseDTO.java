package cl.familypark.familyparkpersonal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
public class DisponibilidadResponseDTO {
    private Long idDisponibilidad;
    private Long idPersonal;
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Boolean activa;
}
