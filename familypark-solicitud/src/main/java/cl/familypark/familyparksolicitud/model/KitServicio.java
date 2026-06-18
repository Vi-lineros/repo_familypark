package cl.familypark.familyparksolicitud.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un Kit de Servicio disponible para contratar.
 * Define precios, duración y capacidad de niños permitida.
 */
@Entity
@Table(name = "kits_servicio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "solicitudes")
public class KitServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kit")
    private Long idKit;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    /** Precio base del kit (incluye costo fijo sin importar la cantidad de niños) */
    @Column(name = "precio_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioBase;

    /** Precio adicional por cada niño sobre el mínimo */
    @Column(name = "precio_por_nino", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioPorNino;

    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

    @Column(name = "minimo_ninos", nullable = false)
    private Integer minimoNinos;

    @Column(name = "maximo_ninos", nullable = false)
    private Integer maximoNinos;

    @Column(name = "activo", nullable = false)
    @Builder.Default
    private Boolean activo = true;

    @OneToMany(mappedBy = "kitServicio", fetch = FetchType.LAZY)
    @Builder.Default
    private List<SolicitudEvento> solicitudes = new ArrayList<>();
}
