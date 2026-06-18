package cl.familypark.familyparkrecursos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recursos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recurso")
    private Long idRecurso;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false, foreignKey = @ForeignKey(name = "fk_recurso_categoria"))
    private CategoriaRecurso categoria;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    @Column(name = "activo", nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
