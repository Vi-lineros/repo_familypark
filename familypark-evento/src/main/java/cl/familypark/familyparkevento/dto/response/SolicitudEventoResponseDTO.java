package cl.familypark.familyparkevento.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudEventoResponseDTO {

    private Long idSolicitud;
    private String estado;
    private LocalDateTime fechaSolicitud;
    private LocalDate fechaSolicitada;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Integer cantidadNinos;
    private Integer cantidadAdultos;
    private String nombreCumpleanero;
    private Integer edadCumpleanero;
    private String requerimientosEspeciales;
    private Long idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String correoCliente;
    private String telefonoCliente;
    private Long idEstablecimiento;
    private String nombreEstablecimiento;
    private String ciudadEstablecimiento;
    private Long idSala;
    private String nombreSala;
    private String tipoSala;
    private Long idKit;
    private String nombreKit;
    private BigDecimal precioBaseKit;
    private BigDecimal precioPorNinoKit;
    private Integer duracionMinutosKit;
}
