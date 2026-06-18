package cl.familypark.familyparksolicitud.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO de entrada para crear una nueva solicitud de evento infantil.
 * Contiene tanto los datos del cliente como los datos del evento.
 * Las validaciones Bean Validation aseguran integridad antes de llegar al Service.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudEventoRequestDTO {

    // ── Datos del Cliente ────────────────────────────────────

    @NotBlank(message = "El nombre del cliente es obligatorio.")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido del cliente es obligatorio.")
    @Size(max = 100, message = "El apellido no puede exceder los 100 caracteres.")
    private String apellido;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico no tiene un formato válido.")
    @Size(max = 150, message = "El correo no puede exceder los 150 caracteres.")
    private String correo;

    @NotBlank(message = "El teléfono es obligatorio.")
    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres.")
    private String telefono;

    @NotBlank(message = "La dirección es obligatoria.")
    @Size(max = 255, message = "La dirección no puede exceder los 255 caracteres.")
    private String direccion;

    // ── Identificadores de Recursos ──────────────────────────

    @NotNull(message = "El establecimiento es obligatorio.")
    @Positive(message = "El ID de establecimiento debe ser un valor positivo.")
    private Long idEstablecimiento;

    @NotNull(message = "La sala es obligatoria.")
    @Positive(message = "El ID de sala debe ser un valor positivo.")
    private Long idSala;

    @NotNull(message = "El kit de servicio es obligatorio.")
    @Positive(message = "El ID de kit debe ser un valor positivo.")
    private Long idKit;

    // ── Datos del Evento ─────────────────────────────────────

    @NotNull(message = "La fecha del evento es obligatoria.")
    @Future(message = "La fecha del evento debe ser una fecha futura.")
    private LocalDate fechaSolicitada;

    @NotNull(message = "La hora de inicio es obligatoria.")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria.")
    private LocalTime horaFin;

    @NotNull(message = "La cantidad de niños es obligatoria.")
    @Min(value = 1, message = "Debe haber al menos 1 niño en el evento.")
    private Integer cantidadNinos;

    @NotNull(message = "La cantidad de adultos es obligatoria.")
    @Min(value = 0, message = "La cantidad de adultos no puede ser negativa.")
    private Integer cantidadAdultos;

    // ── Datos del Festejado ──────────────────────────────────

    @NotBlank(message = "El nombre del cumpleañero es obligatorio.")
    @Size(max = 100, message = "El nombre del cumpleañero no puede exceder los 100 caracteres.")
    private String nombreCumpleanero;

    @NotNull(message = "La edad del cumpleañero es obligatoria.")
    @Min(value = 1, message = "La edad del cumpleañero debe ser mayor a 0.")
    @Max(value = 17, message = "FamilyPark atiende eventos para menores de 18 años.")
    private Integer edadCumpleanero;

    // ── Extras ───────────────────────────────────────────────

    private String requerimientosEspeciales;
}
