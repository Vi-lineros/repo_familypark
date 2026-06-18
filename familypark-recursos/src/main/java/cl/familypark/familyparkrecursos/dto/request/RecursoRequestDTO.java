package cl.familypark.familyparkrecursos.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RecursoRequestDTO {
    @NotBlank(message = "El nombre del recurso es obligatorio")
    @Size(max = 150)
    private String nombre;

    private String descripcion;

    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long idCategoria;

    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precioUnitario;
}
