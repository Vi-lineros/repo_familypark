package cl.familypark.familyparksolicitud.service;

import cl.familypark.familyparksolicitud.dto.response.SalaResponseDTO;
import cl.familypark.familyparksolicitud.model.Sala;
import cl.familypark.familyparksolicitud.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para operaciones de consulta de Salas.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SalaService {

    private final SalaRepository salaRepository;

    /**
     * Retorna todas las salas activas disponibles.
     */
    public List<SalaResponseDTO> listarActivas() {
        log.debug("Consultando salas activas.");
        return salaRepository.findByActivaTrue()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retorna todas las salas activas de un establecimiento específico.
     */
    public List<SalaResponseDTO> listarPorEstablecimiento(Long idEstablecimiento) {
        log.debug("Consultando salas activas para establecimiento ID: {}", idEstablecimiento);
        return salaRepository
                .findByEstablecimiento_IdEstablecimientoAndActivaTrue(idEstablecimiento)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private SalaResponseDTO toResponseDTO(Sala s) {
        return SalaResponseDTO.builder()
                .idSala(s.getIdSala())
                .nombre(s.getNombre())
                .tipoSala(s.getTipoSala())
                .capacidadMaxima(s.getCapacidadMaxima())
                .descripcion(s.getDescripcion())
                .activa(s.getActiva())
                .idEstablecimiento(s.getEstablecimiento().getIdEstablecimiento())
                .nombreEstablecimiento(s.getEstablecimiento().getNombre())
                .ciudadEstablecimiento(s.getEstablecimiento().getCiudad())
                .build();
    }
}
