package cl.familypark.familyparkrecursos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias_recursos", uniqueConstraints = @UniqueConstraint(name = "uk_categoria_nombre", columnNames = "nombre"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CategoriaRecurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "activa", nullable = false)
    @Builder.Default
    private Boolean activa = true;
}
