package cl.familypark.familyparkevento.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionPersonalRequestDTO {

    @NotNull(message = "El idPersonal es obligatorio")
    private Long idPersonal;

    @NotBlank(message = "El nombrePersonal es obligatorio")
    private String nombrePersonal;

    @NotBlank(message = "El cargoPersonal es obligatorio")
    private String cargoPersonal;

    @NotBlank(message = "El rolAsignado es obligatorio")
    private String rolAsignado;
}
