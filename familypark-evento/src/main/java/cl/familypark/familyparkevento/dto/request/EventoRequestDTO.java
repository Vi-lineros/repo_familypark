package cl.familypark.familyparkevento.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoRequestDTO {

    @NotNull(message = "El idSolicitud es obligatorio")
    private Long idSolicitud;

    @NotNull(message = "El idSucursal es obligatorio")
    private Long idSucursal;

    @NotNull(message = "El idSala es obligatorio")
    private Long idSala;

    @NotBlank(message = "El nombreSucursal es obligatorio")
    private String nombreSucursal;

    @NotBlank(message = "El nombreSala es obligatorio")
    private String nombreSala;

    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha no puede estar en el pasado")
    private LocalDate fecha;

    @NotNull(message = "La horaInicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La horaFin es obligatoria")
    private LocalTime horaFin;

    @NotBlank(message = "El nombreCumpleanero es obligatorio")
    private String nombreCumpleanero;

    @NotNull(message = "La edadCumpleanero es obligatoria")
    @Min(value = 1, message = "La edad debe ser mayor a 0")
    private Integer edadCumpleanero;

    @NotNull(message = "La cantidadNinos es obligatoria")
    @Min(value = 1, message = "La cantidad de niños debe ser al menos 1")
    private Integer cantidadNinos;

    @Min(value = 0, message = "La cantidad de adultos no puede ser negativa")
    private Integer cantidadAdultos;

    @NotNull(message = "El precioTotal es obligatorio")
    @Min(value = 0, message = "El precio total no puede ser negativo")
    private Double precioTotal;

    private String requerimientosEspeciales;
}
