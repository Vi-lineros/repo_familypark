package cl.familypark.familyparkevento.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MinutaOperacionalRequestDTO {

    private String checklistJson;
    private String cronogramaJson;
    private String observaciones;
}
