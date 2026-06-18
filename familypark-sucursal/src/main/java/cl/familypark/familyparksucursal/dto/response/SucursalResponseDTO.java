package cl.familypark.familyparksucursal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SucursalResponseDTO {
    private Long idSucursal;
    private String nombre;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String correo;
    private Boolean activa;
    private List<SalaResponseDTO> salas;
}
