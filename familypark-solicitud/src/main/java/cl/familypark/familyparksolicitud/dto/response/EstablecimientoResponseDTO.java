package cl.familypark.familyparksolicitud.dto.response;

import lombok.*;

/**
 * DTO de respuesta para Establecimiento.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstablecimientoResponseDTO {
    private Long idEstablecimiento;
    private String nombre;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String correo;
    private Integer capacidadMaxima;
    private Boolean activo;
}
