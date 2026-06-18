package cl.familypark.familyparkevento.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionPersonalResponseDTO {

    private Long idAsignacionPersonal;
    private Long idEvento;
    private Long idPersonal;
    private String nombrePersonal;
    private String cargoPersonal;
    private String rolAsignado;
}
