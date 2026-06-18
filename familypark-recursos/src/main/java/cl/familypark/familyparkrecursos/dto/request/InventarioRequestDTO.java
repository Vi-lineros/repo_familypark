package cl.familypark.familyparkrecursos.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventarioRequestDTO {
    @NotNull(message = "El ID del recurso es obligatorio")
    private Long idRecurso;

    @NotNull(message = "El ID de la sucursal es obligatorio")
    private Long idSucursal;

    @NotNull(message = "La cantidad total es obligatoria")
    @Min(value = 0, message = "La cantidad total no puede ser negativa")
    private Integer cantidadTotal;

    @NotNull(message = "La cantidad disponible es obligatoria")
    @Min(value = 0, message = "La cantidad disponible no puede ser negativa")
    private Integer cantidadDisponible;
}
