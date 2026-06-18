package cl.familypark.familyparkrecursos.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoriaRecursoResponseDTO {
    private Long idCategoria;
    private String nombre;
    private Boolean activa;
}
