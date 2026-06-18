package cl.familypark.familyparkrecursos.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventarioResponseDTO {
    private Long idInventario;
    private RecursoResponseDTO recurso;
    private Long idSucursal;
    private Integer cantidadTotal;
    private Integer cantidadDisponible;
}
