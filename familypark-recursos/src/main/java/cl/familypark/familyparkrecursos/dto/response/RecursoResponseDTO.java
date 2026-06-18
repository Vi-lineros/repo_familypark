package cl.familypark.familyparkrecursos.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecursoResponseDTO {
    private Long idRecurso;
    private String nombre;
    private String descripcion;
    private CategoriaRecursoResponseDTO categoria;
    private Double precioUnitario;
    private Boolean activo;
}
