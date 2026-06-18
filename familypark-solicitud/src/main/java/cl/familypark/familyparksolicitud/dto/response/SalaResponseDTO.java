package cl.familypark.familyparksolicitud.dto.response;

import lombok.*;

/**
 * DTO de respuesta para Sala.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaResponseDTO {
    private Long idSala;
    private String nombre;
    private String tipoSala;
    private Integer capacidadMaxima;
    private String descripcion;
    private Boolean activa;
    private Long idEstablecimiento;
    private String nombreEstablecimiento;
    private String ciudadEstablecimiento;
}
