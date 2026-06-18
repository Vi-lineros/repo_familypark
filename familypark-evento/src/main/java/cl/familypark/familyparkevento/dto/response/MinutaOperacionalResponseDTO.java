package cl.familypark.familyparkevento.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MinutaOperacionalResponseDTO {

    private Long idMinuta;
    private Long idEvento;
    private String checklistJson;
    private String cronogramaJson;
    private String observaciones;
    private LocalDateTime fechaGeneracion;
}
