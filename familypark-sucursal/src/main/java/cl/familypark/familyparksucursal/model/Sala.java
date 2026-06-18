package cl.familypark.familyparksucursal.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa una sala dentro de una Sucursal de FamilyPark.
 * El tipo de local es exclusivamente ARCADE o PARQUE_TRAMPOLINES.
 */
@Entity
@Table(name = "salas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "sucursal")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private Long idSala;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_local", nullable = false, length = 30)
    private TipoLocal tipoLocal;

    @Column(name = "capacidad_maxima", nullable = false)
    private Integer capacidadMaxima;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "activa", nullable = false)
    @Builder.Default
    private Boolean activa = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_sucursal",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_sala_sucursal"))
    private Sucursal sucursal;
}
