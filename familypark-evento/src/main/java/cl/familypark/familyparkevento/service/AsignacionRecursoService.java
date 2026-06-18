package cl.familypark.familyparkevento.service;

import cl.familypark.familyparkevento.dto.request.AsignacionRecursoRequestDTO;
import cl.familypark.familyparkevento.dto.response.AsignacionRecursoResponseDTO;
import cl.familypark.familyparkevento.exception.ResourceNotFoundException;
import cl.familypark.familyparkevento.model.AsignacionRecurso;
import cl.familypark.familyparkevento.model.Evento;
import cl.familypark.familyparkevento.repository.AsignacionRecursoRepository;
import cl.familypark.familyparkevento.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AsignacionRecursoService {

    private final AsignacionRecursoRepository repository;
    private final EventoRepository eventoRepository;
    private final RestTemplate restTemplate;

    @Value("${service.recursos.url:http://localhost:8084/api/v1/recursos}")
    private String recursosServiceUrl;

    @Transactional
    public AsignacionRecursoResponseDTO asignarRecurso(Long idEvento, AsignacionRecursoRequestDTO requestDTO) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + idEvento));

        obtenerRecursos(requestDTO.getIdRecurso());

        AsignacionRecurso asignacion = AsignacionRecurso.builder()
                .evento(evento)
                .idRecurso(requestDTO.getIdRecurso())
                .nombreRecurso(requestDTO.getNombreRecurso())
                .cantidad(requestDTO.getCantidad())
                .precioUnitario(requestDTO.getPrecioUnitario())
                .build();

        return mapToDTO(repository.save(asignacion));
    }

    @Transactional
    public void removerRecurso(Long idEvento, Long idAsignacion) {
        AsignacionRecurso asignacion = repository.findById(idAsignacion)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación de recurso no encontrada con ID: " + idAsignacion));

        if (!asignacion.getEvento().getIdEvento().equals(idEvento)) {
            throw new ResourceNotFoundException("La asignación no pertenece al evento especificado");
        }

        repository.delete(asignacion);
    }

    public List<AsignacionRecursoResponseDTO> listarRecursosAsignados(Long idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + idEvento));

        return evento.getAsignacionesRecurso().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private void obtenerRecursos(Long idRecurso) {
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(recursosServiceUrl + "/" + idRecurso, Object.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ResourceNotFoundException("El recurso no es válido");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("No se encontró el recurso con ID: " + idRecurso + " en familypark-recursos");
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con familypark-recursos: " + e.getMessage());
        }
    }

    private AsignacionRecursoResponseDTO mapToDTO(AsignacionRecurso asignacion) {
        return AsignacionRecursoResponseDTO.builder()
                .idAsignacionRecurso(asignacion.getIdAsignacionRecurso())
                .idEvento(asignacion.getEvento().getIdEvento())
                .idRecurso(asignacion.getIdRecurso())
                .nombreRecurso(asignacion.getNombreRecurso())
                .cantidad(asignacion.getCantidad())
                .precioUnitario(asignacion.getPrecioUnitario())
                .build();
    }
}
