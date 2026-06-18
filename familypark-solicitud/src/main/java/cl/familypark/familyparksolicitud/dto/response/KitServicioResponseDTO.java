package cl.familypark.familyparksolicitud.dto.response;

import lombok.*;

import java.math.BigDecimal;

/**
 * DTO de respuesta para KitServicio.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitServicioResponseDTO {
    private Long idKit;
    private String nombre;
    private String descripcion;
    private BigDecimal precioBase;
    private BigDecimal precioPorNino;
    private Integer duracionMinutos;
    private Integer minimoNinos;
    private Integer maximoNinos;
    private Boolean activo;
}
