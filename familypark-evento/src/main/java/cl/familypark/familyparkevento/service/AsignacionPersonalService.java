package cl.familypark.familyparkevento.service;

import cl.familypark.familyparkevento.dto.request.AsignacionPersonalRequestDTO;
import cl.familypark.familyparkevento.dto.response.AsignacionPersonalResponseDTO;
import cl.familypark.familyparkevento.exception.ResourceNotFoundException;
import cl.familypark.familyparkevento.model.AsignacionPersonal;
import cl.familypark.familyparkevento.model.Evento;
import cl.familypark.familyparkevento.repository.AsignacionPersonalRepository;
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
public class AsignacionPersonalService {

    private final AsignacionPersonalRepository repository;
    private final EventoRepository eventoRepository;
    private final RestTemplate restTemplate;

    @Value("${service.personal.url:http://localhost:8083/api/v1/personal}")
    private String personalServiceUrl;

    @Transactional
    public AsignacionPersonalResponseDTO asignarPersonal(Long idEvento, AsignacionPersonalRequestDTO requestDTO) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + idEvento));

        obtenerPersonal(requestDTO.getIdPersonal());

        AsignacionPersonal asignacion = AsignacionPersonal.builder()
                .evento(evento)
                .idPersonal(requestDTO.getIdPersonal())
                .nombrePersonal(requestDTO.getNombrePersonal())
                .cargoPersonal(requestDTO.getCargoPersonal())
                .rolAsignado(requestDTO.getRolAsignado())
                .build();

        return mapToDTO(repository.save(asignacion));
    }

    @Transactional
    public void removerPersonal(Long idEvento, Long idAsignacion) {
        AsignacionPersonal asignacion = repository.findById(idAsignacion)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación de personal no encontrada con ID: " + idAsignacion));

        if (!asignacion.getEvento().getIdEvento().equals(idEvento)) {
            throw new ResourceNotFoundException("La asignación no pertenece al evento especificado");
        }

        repository.delete(asignacion);
    }

    public List<AsignacionPersonalResponseDTO> listarPersonalAsignado(Long idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + idEvento));

        return evento.getAsignacionesPersonal().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private void obtenerPersonal(Long idPersonal) {
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(personalServiceUrl + "/" + idPersonal, Object.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ResourceNotFoundException("El personal no es válido");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("No se encontró el personal con ID: " + idPersonal + " en familypark-personal");
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con familypark-personal: " + e.getMessage());
        }
    }

    private AsignacionPersonalResponseDTO mapToDTO(AsignacionPersonal asignacion) {
        return AsignacionPersonalResponseDTO.builder()
                .idAsignacionPersonal(asignacion.getIdAsignacionPersonal())
                .idEvento(asignacion.getEvento().getIdEvento())
                .idPersonal(asignacion.getIdPersonal())
                .nombrePersonal(asignacion.getNombrePersonal())
                .cargoPersonal(asignacion.getCargoPersonal())
                .rolAsignado(asignacion.getRolAsignado())
                .build();
    }
}
