package cl.familypark.familyparkevento.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad central del sistema: representa un evento de cumpleaños confirmado.
 * Se crea a partir de una Solicitud aprobada.
 * Mantiene referencias lógicas (IDs) a otros microservicios.
 */
@Entity
@Table(name = "eventos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"asignacionesPersonal", "asignacionesRecurso", "minuta"})
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;

    /** FK lógica a familypark-solicitud */
    @Column(name = "id_solicitud", nullable = false, unique = true)
    private Long idSolicitud;

    /** FK lógica a familypark-sucursal */
    @Column(name = "id_sucursal", nullable = false)
    private Long idSucursal;

    /** FK lógica a familypark-sucursal */
    @Column(name = "id_sala", nullable = false)
    private Long idSala;

    @Column(name = "nombre_sucursal", nullable = false, length = 150)
    private String nombreSucursal;

    @Column(name = "nombre_sala", nullable = false, length = 100)
    private String nombreSala;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(name = "nombre_cumpleanero", nullable = false, length = 150)
    private String nombreCumpleanero;

    @Column(name = "edad_cumpleanero")
    private Integer edadCumpleanero;

    @Column(name = "cantidad_ninos", nullable = false)
    private Integer cantidadNinos;

    @Column(name = "cantidad_adultos")
    private Integer cantidadAdultos;

    @Column(name = "precio_total")
    private Double precioTotal;

    @Column(name = "requerimientos_especiales", columnDefinition = "TEXT")
    private String requerimientosEspeciales;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private EstadoEvento estado = EstadoEvento.PROGRAMADO;

    @Column(name = "motivo_cancelacion", length = 500)
    private String motivoCancelacion;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<AsignacionPersonal> asignacionesPersonal = new ArrayList<>();

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<AsignacionRecurso> asignacionesRecurso = new ArrayList<>();

    @OneToOne(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MinutaOperacional minuta;
}
