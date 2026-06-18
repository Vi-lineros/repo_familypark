package cl.familypark.familyparkreportes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {
    private Long idEvento;
    private Long idSucursal;
    private String nombreSucursal;
    private String estado;
    private List<AsignacionPersonalDTO> asignacionesPersonal;
    private List<AsignacionRecursoDTO> asignacionesRecurso;
}
