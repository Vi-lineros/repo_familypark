package cl.familypark.familyparkevento.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Asignación de recursos físicos a un evento.
 * Mantiene FK lógica a familypark-recursos.
 */
@Entity
@Table(name = "asignaciones_recurso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "evento")
public class AsignacionRecurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion_recurso")
    private Long idAsignacionRecurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false,
                foreignKey = @ForeignKey(name = "fk_asr_evento"))
    private Evento evento;

    /** FK lógica a familypark-recursos */
    @Column(name = "id_recurso", nullable = false)
    private Long idRecurso;

    @Column(name = "nombre_recurso", nullable = false, length = 150)
    private String nombreRecurso;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Double precioUnitario;
}
