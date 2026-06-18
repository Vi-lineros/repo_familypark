package cl.familypark.familyparksucursal.dto.response;

import cl.familypark.familyparksucursal.model.TipoLocal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalaResponseDTO {
    private Long idSala;
    private String nombre;
    private TipoLocal tipoLocal;
    private Integer capacidadMaxima;
    private String descripcion;
    private Boolean activa;
    private Long idSucursal;
}
