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
public class DashboardResponseDTO {
    private Long eventosRealizados;
    private Long eventosCancelados;
    private Long solicitudesAprobadas;
    private Long solicitudesRechazadas;
    private List<OcupacionSucursalDTO> ocupacionPorSucursal;
    private List<RecursoUtilizadoDTO> recursosMasUtilizados;
    private List<PersonalUtilizadoDTO> personalMasUtilizado;
}
