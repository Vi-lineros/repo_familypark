package cl.familypark.familyparkevento.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionRecursoResponseDTO {

    private Long idAsignacionRecurso;
    private Long idEvento;
    private Long idRecurso;
    private String nombreRecurso;
    private Integer cantidad;
    private Double precioUnitario;
}
