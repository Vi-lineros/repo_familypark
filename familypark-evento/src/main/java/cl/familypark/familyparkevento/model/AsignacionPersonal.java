package cl.familypark.familyparkevento.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Asignación de un trabajador a un evento.
 * Mantiene FK lógica a familypark-personal.
 */
@Entity
@Table(name = "asignaciones_personal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "evento")
public class AsignacionPersonal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion_personal")
    private Long idAsignacionPersonal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false,
                foreignKey = @ForeignKey(name = "fk_asp_evento"))
    private Evento evento;

    /** FK lógica a familypark-personal */
    @Column(name = "id_personal", nullable = false)
    private Long idPersonal;

    @Column(name = "nombre_personal", nullable = false, length = 200)
    private String nombrePersonal;

    @Column(name = "cargo_personal", length = 100)
    private String cargoPersonal;

    @Column(name = "rol_asignado", nullable = false, length = 150)
    private String rolAsignado;
}
