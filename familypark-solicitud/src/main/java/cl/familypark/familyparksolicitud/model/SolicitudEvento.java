package cl.familypark.familyparksolicitud.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Entidad principal del microservicio.
 * Representa una solicitud de evento infantil registrada en FamilyPark.
 *
 * Relaciones:
 *  - ManyToOne → Cliente
 *  - ManyToOne → Establecimiento
 *  - ManyToOne → Sala
 *  - ManyToOne → KitServicio
 */
@Entity
@Table(name = "solicitudes_evento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"cliente", "establecimiento", "sala", "kitServicio"})
public class SolicitudEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Long idSolicitud;

    /** Fecha del evento solicitado (solo fecha, sin hora) */
    @Column(name = "fecha_solicitada", nullable = false)
    private LocalDate fechaSolicitada;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(name = "cantidad_ninos", nullable = false)
    private Integer cantidadNinos;

    @Column(name = "cantidad_adultos", nullable = false)
    @Builder.Default
    private Integer cantidadAdultos = 0;

    @Column(name = "nombre_cumpleanero", nullable = false, length = 100)
    private String nombreCumpleanero;

    @Column(name = "edad_cumpleanero", nullable = false)
    private Integer edadCumpleanero;

    @Column(name = "requerimientos_especiales", columnDefinition = "TEXT")
    private String requerimientosEspeciales;

    /** Estado del ciclo de vida: PENDIENTE por defecto */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    /** Fecha y hora en que se registró la solicitud en el sistema */
    @Column(name = "fecha_solicitud", nullable = false, updatable = false)
    private LocalDateTime fechaSolicitud;

    // ── Relaciones ManyToOne ──────────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_cliente",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_solicitud_cliente"))
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_establecimiento",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_solicitud_establecimiento"))
    private Establecimiento establecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_sala",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_solicitud_sala"))
    private Sala sala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_kit",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_solicitud_kit"))
    private KitServicio kitServicio;

    /** Se ejecuta antes de persistir: asigna la fecha/hora de registro */
    @PrePersist
    protected void onPersist() {
        this.fechaSolicitud = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoSolicitud.PENDIENTE;
        }
    }
}
