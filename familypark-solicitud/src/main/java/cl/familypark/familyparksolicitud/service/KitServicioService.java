package cl.familypark.familyparksolicitud.service;

import cl.familypark.familyparksolicitud.dto.response.KitServicioResponseDTO;
import cl.familypark.familyparksolicitud.model.KitServicio;
import cl.familypark.familyparksolicitud.repository.KitServicioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para operaciones de consulta de Kits de Servicio.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class KitServicioService {

    private final KitServicioRepository kitRepository;

    /**
     * Retorna todos los kits activos disponibles para contratar.
     */
    public List<KitServicioResponseDTO> listarActivos() {
        log.debug("Consultando kits de servicio activos.");
        return kitRepository.findByActivoTrue()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private KitServicioResponseDTO toResponseDTO(KitServicio k) {
        return KitServicioResponseDTO.builder()
                .idKit(k.getIdKit())
                .nombre(k.getNombre())
                .descripcion(k.getDescripcion())
                .precioBase(k.getPrecioBase())
                .precioPorNino(k.getPrecioPorNino())
                .duracionMinutos(k.getDuracionMinutos())
                .minimoNinos(k.getMinimoNinos())
                .maximoNinos(k.getMaximoNinos())
                .activo(k.getActivo())
                .build();
    }
}
