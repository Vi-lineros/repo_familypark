package cl.familypark.familyparkreportes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionRecursoDTO {
    private Long idRecurso;
    private String nombreRecurso;
    private Integer cantidad;
}
