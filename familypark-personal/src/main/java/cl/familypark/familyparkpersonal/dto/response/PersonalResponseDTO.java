package cl.familypark.familyparkpersonal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonalResponseDTO {
    private Long idPersonal;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private CargoResponseDTO cargo;
    private Boolean activo;
    private List<DisponibilidadResponseDTO> disponibilidades;
}
