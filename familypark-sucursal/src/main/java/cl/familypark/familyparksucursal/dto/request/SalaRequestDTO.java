package cl.familypark.familyparksucursal.dto.request;

import cl.familypark.familyparksucursal.model.TipoLocal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SalaRequestDTO {

    @NotBlank(message = "El nombre de la sala es obligatorio")
    @Size(max = 100)
    private String nombre;

    @NotNull(message = "El tipo de local es obligatorio (ARCADE o PARQUE_TRAMPOLINES)")
    private TipoLocal tipoLocal;

    @NotNull(message = "La capacidad maxima es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    private Integer capacidadMaxima;

    @Size(max = 500)
    private String descripcion;

    @NotNull(message = "El ID de la sucursal es obligatorio")
    private Long idSucursal;
}
