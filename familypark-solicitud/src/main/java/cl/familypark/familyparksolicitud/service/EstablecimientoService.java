package cl.familypark.familyparksolicitud.service;

import cl.familypark.familyparksolicitud.dto.response.EstablecimientoResponseDTO;
import cl.familypark.familyparksolicitud.model.Establecimiento;
import cl.familypark.familyparksolicitud.repository.EstablecimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para operaciones de consulta de Establecimientos.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EstablecimientoService {

    private final EstablecimientoRepository establecimientoRepository;

    /**
     * Retorna todos los establecimientos activos disponibles.
     */
    public List<EstablecimientoResponseDTO> listarActivos() {
        log.debug("Consultando establecimientos activos.");
        return establecimientoRepository.findByActivoTrue()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retorna todos los establecimientos (activos e inactivos).
     */
    public List<EstablecimientoResponseDTO> listarTodos() {
        log.debug("Consultando todos los establecimientos.");
        return establecimientoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private EstablecimientoResponseDTO toResponseDTO(Establecimiento e) {
        return EstablecimientoResponseDTO.builder()
                .idEstablecimiento(e.getIdEstablecimiento())
                .nombre(e.getNombre())
                .ciudad(e.getCiudad())
                .direccion(e.getDireccion())
                .telefono(e.getTelefono())
                .correo(e.getCorreo())
                .capacidadMaxima(e.getCapacidadMaxima())
                .activo(e.getActivo())
                .build();
    }
}
