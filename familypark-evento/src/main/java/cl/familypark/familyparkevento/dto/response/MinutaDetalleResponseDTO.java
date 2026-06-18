package cl.familypark.familyparkevento.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MinutaDetalleResponseDTO {

    private Long idEvento;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado;

    private String nombreCumpleanero;
    private Integer edadCumpleanero;
    private Integer cantidadNinos;
    private Integer cantidadAdultos;
    private String requerimientosEspeciales;

    private String nombreCliente;
    private String apellidoCliente;
    private String correoCliente;
    private String telefonoCliente;

    private String nombreKit;
    private BigDecimal precioBaseKit;
    private BigDecimal precioPorNinoKit;
    private BigDecimal precioTotalCalculado;
}
