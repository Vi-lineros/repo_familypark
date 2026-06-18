package cl.familypark.familyparkevento.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Minuta operacional generada automáticamente al crear el evento.
 * Contiene checklist y cronograma del cumpleaños en formato JSON.
 */
@Entity
@Table(name = "minutas_operacionales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "evento")
public class MinutaOperacional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_minuta")
    private Long idMinuta;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false,
                foreignKey = @ForeignKey(name = "fk_minuta_evento"))
    private Evento evento;

    /**
     * Checklist de actividades en formato JSON.
     * Ejemplo: [{"tarea":"Montar sala","completada":false}, ...]
     */
    @Column(name = "checklist_json", columnDefinition = "TEXT")
    private String checklistJson;

    /**
     * Cronograma de hitos horarios en formato JSON.
     * Ejemplo: [{"hora":"14:00","actividad":"Llegada de invitados"}, ...]
     */
    @Column(name = "cronograma_json", columnDefinition = "TEXT")
    private String cronogramaJson;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_generacion", nullable = false)
    private LocalDateTime fechaGeneracion;

    @PrePersist
    protected void onCreate() {
        fechaGeneracion = LocalDateTime.now();
    }
}
