package cl.familypark.familyparksucursal.service;

import cl.familypark.familyparksucursal.dto.request.SalaRequestDTO;
import cl.familypark.familyparksucursal.dto.response.SalaResponseDTO;
import cl.familypark.familyparksucursal.exception.BadRequestException;
import cl.familypark.familyparksucursal.exception.ConflictException;
import cl.familypark.familyparksucursal.exception.ResourceNotFoundException;
import cl.familypark.familyparksucursal.model.Sala;
import cl.familypark.familyparksucursal.model.Sucursal;
import cl.familypark.familyparksucursal.repository.SalaRepository;
import cl.familypark.familyparksucursal.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SalaService {

    private final SalaRepository salaRepo;
    private final SucursalRepository sucursalRepo;

    @Transactional(readOnly = true)
    public List<SalaResponseDTO> listarTodas() {
        log.debug("Listando todas las salas.");
        return salaRepo.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SalaResponseDTO> listarActivas() {
        log.debug("Listando salas activas.");
        return salaRepo.findByActivaTrue().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SalaResponseDTO> listarPorSucursal(Long idSucursal, boolean soloActivas) {
        log.debug("Listando salas por sucursal: {}, soloActivas: {}", idSucursal, soloActivas);
        List<Sala> salas = soloActivas 
                ? salaRepo.findBySucursal_IdSucursalAndActivaTrue(idSucursal)
                : salaRepo.findBySucursal_IdSucursal(idSucursal);
        return salas.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SalaResponseDTO obtenerPorId(Long id) {
        log.debug("Obteniendo sala por ID: {}", id);
        Sala sala = salaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala", id));
        return toResponseDTO(sala);
    }

    public SalaResponseDTO crear(SalaRequestDTO dto) {
        log.info("Creando sala: {} para sucursal ID: {}", dto.getNombre(), dto.getIdSucursal());
        Sucursal sucursal = sucursalRepo.findById(dto.getIdSucursal())
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal", dto.getIdSucursal()));

        if (!sucursal.getActiva()) {
            throw new BadRequestException("No se pueden agregar salas a una sucursal inactiva.");
        }

        // Evitar duplicados de nombre de sala dentro de la misma sucursal
        boolean existeNombreEnSucursal = salaRepo.findBySucursal_IdSucursal(dto.getIdSucursal())
                .stream()
                .anyMatch(s -> s.getNombre().equalsIgnoreCase(dto.getNombre()));
        if (existeNombreEnSucursal) {
            throw new ConflictException("Ya existe una sala con el nombre '" + dto.getNombre() + "' en esta sucursal.");
        }

        Sala sala = Sala.builder()
                .nombre(dto.getNombre())
                .tipoLocal(dto.getTipoLocal())
                .capacidadMaxima(dto.getCapacidadMaxima())
                .descripcion(dto.getDescripcion())
                .sucursal(sucursal)
                .activa(true)
                .build();

        Sala guardada = salaRepo.save(sala);
        return toResponseDTO(guardada);
    }

    public SalaResponseDTO actualizar(Long id, SalaRequestDTO dto) {
        log.info("Actualizando sala con ID: {}", id);
        Sala sala = salaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala", id));

        // Validar sucursal si cambia
        if (!sala.getSucursal().getIdSucursal().equals(dto.getIdSucursal())) {
            Sucursal sucursal = sucursalRepo.findById(dto.getIdSucursal())
                    .orElseThrow(() -> new ResourceNotFoundException("Sucursal", dto.getIdSucursal()));
            if (!sucursal.getActiva()) {
                throw new BadRequestException("No se puede mover la sala a una sucursal inactiva.");
            }
            sala.setSucursal(sucursal);
        }

        // Validar nombre duplicado en la sucursal de destino
        boolean existeNombreEnSucursal = salaRepo.findBySucursal_IdSucursal(dto.getIdSucursal())
                .stream()
                .anyMatch(s -> !s.getIdSala().equals(id) && s.getNombre().equalsIgnoreCase(dto.getNombre()));
        if (existeNombreEnSucursal) {
            throw new ConflictException("Ya existe otra sala con el nombre '" + dto.getNombre() + "' en la sucursal.");
        }

        sala.setNombre(dto.getNombre());
        sala.setTipoLocal(dto.getTipoLocal());
        sala.setCapacidadMaxima(dto.getCapacidadMaxima());
        sala.setDescripcion(dto.getDescripcion());

        Sala actualizada = salaRepo.save(sala);
        return toResponseDTO(actualizada);
    }

    public SalaResponseDTO desactivar(Long id) {
        log.info("Desactivando sala con ID: {}", id);
        Sala sala = salaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala", id));
        sala.setActiva(false);
        Sala guardada = salaRepo.save(sala);
        return toResponseDTO(guardada);
    }

    public SalaResponseDTO activar(Long id) {
        log.info("Activando sala con ID: {}", id);
        Sala sala = salaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala", id));
        if (!sala.getSucursal().getActiva()) {
            throw new BadRequestException("No se puede activar una sala si su sucursal está inactiva.");
        }
        sala.setActiva(true);
        Sala guardada = salaRepo.save(sala);
        return toResponseDTO(guardada);
    }

    public SalaResponseDTO toResponseDTO(Sala s) {
        return SalaResponseDTO.builder()
                .idSala(s.getIdSala())
                .nombre(s.getNombre())
                .tipoLocal(s.getTipoLocal())
                .capacidadMaxima(s.getCapacidadMaxima())
                .descripcion(s.getDescripcion())
                .activa(s.getActiva())
                .idSucursal(s.getSucursal() != null ? s.getSucursal().getIdSucursal() : null)
                .build();
    }
}
