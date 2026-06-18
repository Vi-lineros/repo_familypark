package cl.familypark.familyparkpersonal.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CargoResponseDTO {
    private Long idCargo;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}
