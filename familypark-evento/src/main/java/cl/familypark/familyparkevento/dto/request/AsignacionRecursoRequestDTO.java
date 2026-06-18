package cl.familypark.familyparkevento.dto.request;

import jakarta.validation.constraints.Min;
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
public class AsignacionRecursoRequestDTO {

    @NotNull(message = "El idRecurso es obligatorio")
    private Long idRecurso;

    @NotBlank(message = "El nombreRecurso es obligatorio")
    private String nombreRecurso;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @Min(value = 0, message = "El precio unitario no puede ser negativo")
    private Double precioUnitario;
}
