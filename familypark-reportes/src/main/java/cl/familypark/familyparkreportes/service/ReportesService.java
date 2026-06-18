package cl.familypark.familyparkreportes.service;

import cl.familypark.familyparkreportes.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportesService {

    private final RestTemplate restTemplate;

    @Value("${service.solicitud.url}")
    private String solicitudServiceUrl;

    @Value("${service.evento.url}")
    private String eventoServiceUrl;

    public DashboardResponseDTO obtenerDashboard() {
        List<SolicitudDTO> solicitudes = obtenerSolicitudes();
        List<EventoDTO> eventos = obtenerEventos();

        long solicitudesAprobadas = solicitudes.stream()
                .filter(s -> "CONFIRMADO".equalsIgnoreCase(s.getEstado()))
                .count();

        long solicitudesRechazadas = solicitudes.stream()
                .filter(s -> "CANCELADO".equalsIgnoreCase(s.getEstado()))
                .count();

        long eventosRealizados = eventos.stream()
                .filter(e -> "COMPLETADO".equalsIgnoreCase(e.getEstado()))
                .count();

        long eventosCancelados = eventos.stream()
                .filter(e -> "CANCELADO".equalsIgnoreCase(e.getEstado()))
                .count();

        List<OcupacionSucursalDTO> ocupacion = eventos.stream()
                .collect(Collectors.groupingBy(EventoDTO::getNombreSucursal, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new OcupacionSucursalDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        Map<String, Integer> recursosMap = eventos.stream()
                .flatMap(e -> e.getAsignacionesRecurso() != null ? e.getAsignacionesRecurso().stream() : java.util.stream.Stream.empty())
                .collect(Collectors.groupingBy(
                        AsignacionRecursoDTO::getNombreRecurso,
                        Collectors.summingInt(AsignacionRecursoDTO::getCantidad)
                ));

        List<RecursoUtilizadoDTO> recursosUtilizados = recursosMap.entrySet().stream()
                .map(entry -> new RecursoUtilizadoDTO(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> Integer.compare(b.getCantidadTotal(), a.getCantidadTotal()))
                .collect(Collectors.toList());

        Map<String, Long> personalMap = eventos.stream()
                .flatMap(e -> e.getAsignacionesPersonal() != null ? e.getAsignacionesPersonal().stream() : java.util.stream.Stream.empty())
                .collect(Collectors.groupingBy(AsignacionPersonalDTO::getNombrePersonal, Collectors.counting()));

        List<PersonalUtilizadoDTO> personalUtilizado = personalMap.entrySet().stream()
                .map(entry -> new PersonalUtilizadoDTO(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> Long.compare(b.getCantidadEventosParticipados(), a.getCantidadEventosParticipados()))
                .collect(Collectors.toList());

        return DashboardResponseDTO.builder()
                .eventosRealizados(eventosRealizados)
                .eventosCancelados(eventosCancelados)
                .solicitudesAprobadas(solicitudesAprobadas)
                .solicitudesRechazadas(solicitudesRechazadas)
                .ocupacionPorSucursal(ocupacion)
                .recursosMasUtilizados(recursosUtilizados)
                .personalMasUtilizado(personalUtilizado)
                .build();
    }

    private List<SolicitudDTO> obtenerSolicitudes() {
        try {
            SolicitudDTO[] solicitudes = restTemplate.getForObject(solicitudServiceUrl, SolicitudDTO[].class);
            return solicitudes != null ? Arrays.asList(solicitudes) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private List<EventoDTO> obtenerEventos() {
        try {
            EventoDTO[] eventos = restTemplate.getForObject(eventoServiceUrl, EventoDTO[].class);
            return eventos != null ? Arrays.asList(eventos) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
