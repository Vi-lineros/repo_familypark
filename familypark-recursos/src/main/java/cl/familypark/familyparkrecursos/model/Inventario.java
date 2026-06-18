package cl.familypark.familyparkrecursos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventarios", uniqueConstraints = @UniqueConstraint(name = "uk_inventario_recurso_sucursal", columnNames = {"id_recurso", "id_sucursal"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long idInventario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recurso", nullable = false, foreignKey = @ForeignKey(name = "fk_inventario_recurso"))
    private Recurso recurso;

    @Column(name = "id_sucursal", nullable = false)
    private Long idSucursal;

    @Column(name = "cantidad_total", nullable = false)
    private Integer cantidadTotal;

    @Column(name = "cantidad_disponible", nullable = false)
    private Integer cantidadDisponible;
}
