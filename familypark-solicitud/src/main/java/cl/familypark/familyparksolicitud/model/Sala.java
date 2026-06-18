package cl.familypark.familyparksolicitud.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una sala de eventos dentro de un Establecimiento.
 * Una sala pertenece a un único establecimiento (ManyToOne).
 */
@Entity
@Table(name = "salas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"establecimiento", "solicitudes"})
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private Long idSala;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "tipo_sala", nullable = false, length = 80)
    private String tipoSala;

    @Column(name = "capacidad_maxima", nullable = false)
    private Integer capacidadMaxima;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "activa", nullable = false)
    @Builder.Default
    private Boolean activa = true;

    /** Relación: Sala pertenece a un Establecimiento */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_establecimiento",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_sala_establecimiento"))
    private Establecimiento establecimiento;

    @OneToMany(mappedBy = "sala", fetch = FetchType.LAZY)
    @Builder.Default
    private List<SolicitudEvento> solicitudes = new ArrayList<>();
}
