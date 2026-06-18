package cl.familypark.familyparksolicitud.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO de respuesta para una SolicitudEvento.
 * Expone todos los campos relevantes sin exponer la entidad JPA directamente.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudEventoResponseDTO {

    private Long idSolicitud;
    private String estado;
    private LocalDateTime fechaSolicitud;

    // Datos del evento
    private LocalDate fechaSolicitada;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Integer cantidadNinos;
    private Integer cantidadAdultos;
    private String nombreCumpleanero;
    private Integer edadCumpleanero;
    private String requerimientosEspeciales;

    // Datos del cliente
    private Long idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String correoCliente;
    private String telefonoCliente;

    // Datos del establecimiento
    private Long idEstablecimiento;
    private String nombreEstablecimiento;
    private String ciudadEstablecimiento;

    // Datos de la sala
    private Long idSala;
    private String nombreSala;
    private String tipoSala;

    // Datos del kit
    private Long idKit;
    private String nombreKit;
    private BigDecimal precioBaseKit;
    private BigDecimal precioPorNinoKit;
    private Integer duracionMinutosKit;
}
