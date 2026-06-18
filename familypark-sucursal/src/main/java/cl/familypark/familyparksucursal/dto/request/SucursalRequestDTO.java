package cl.familypark.familyparksucursal.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SucursalRequestDTO {

    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
    private String nombre;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 100)
    private String ciudad;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 255)
    private String direccion;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(max = 20)
    private String telefono;

    @Email(message = "Formato de correo invalido")
    @Size(max = 150)
    private String correo;
}
