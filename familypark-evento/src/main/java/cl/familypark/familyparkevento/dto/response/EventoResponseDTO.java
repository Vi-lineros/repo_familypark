package cl.familypark.familyparkevento.dto.response;

import cl.familypark.familyparkevento.model.EstadoEvento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoResponseDTO {

    private Long idEvento;
    private Long idSolicitud;
    private Long idSucursal;
    private Long idSala;
    private String nombreSucursal;
    private String nombreSala;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String nombreCumpleanero;
    private Integer edadCumpleanero;
    private Integer cantidadNinos;
    private Integer cantidadAdultos;
    private Double precioTotal;
    private String requerimientosEspeciales;
    private EstadoEvento estado;
    private String motivoCancelacion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    
    private List<AsignacionPersonalResponseDTO> asignacionesPersonal;
    private List<AsignacionRecursoResponseDTO> asignacionesRecurso;
    private MinutaOperacionalResponseDTO minuta;
}
