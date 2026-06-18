package cl.familypark.familyparksucursal.service;

import cl.familypark.familyparksucursal.dto.request.SucursalRequestDTO;
import cl.familypark.familyparksucursal.dto.response.SalaResponseDTO;
import cl.familypark.familyparksucursal.dto.response.SucursalResponseDTO;
import cl.familypark.familyparksucursal.exception.ConflictException;
import cl.familypark.familyparksucursal.exception.ResourceNotFoundException;
import cl.familypark.familyparksucursal.model.Sala;
import cl.familypark.familyparksucursal.model.Sucursal;
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
public class SucursalService {

    private final SucursalRepository sucursalRepo;

    @Transactional(readOnly = true)
    public List<SucursalResponseDTO> listarTodas() {
        log.debug("Listando todas las sucursales.");
        return sucursalRepo.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SucursalResponseDTO> listarActivas() {
        log.debug("Listando sucursales activas.");
        return sucursalRepo.findByActivaTrue().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SucursalResponseDTO obtenerPorId(Long id) {
        log.debug("Obteniendo sucursal por ID: {}", id);
        Sucursal sucursal = sucursalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal", id));
        return toResponseDTO(sucursal);
    }

    public SucursalResponseDTO crear(SucursalRequestDTO dto) {
        log.info("Creando sucursal: {}", dto.getNombre());
        if (sucursalRepo.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new ConflictException("Ya existe una sucursal con el nombre: " + dto.getNombre());
        }

        Sucursal sucursal = Sucursal.builder()
                .nombre(dto.getNombre())
                .ciudad(dto.getCiudad())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .activa(true)
                .build();

        Sucursal guardada = sucursalRepo.save(sucursal);
        return toResponseDTO(guardada);
    }

    public SucursalResponseDTO actualizar(Long id, SucursalRequestDTO dto) {
        log.info("Actualizando sucursal con ID: {}", id);
        Sucursal sucursal = sucursalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal", id));

        if (!sucursal.getNombre().equalsIgnoreCase(dto.getNombre()) && 
                sucursalRepo.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new ConflictException("Ya existe otra sucursal con el nombre: " + dto.getNombre());
        }

        sucursal.setNombre(dto.getNombre());
        sucursal.setCiudad(dto.getCiudad());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setTelefono(dto.getTelefono());
        sucursal.setCorreo(dto.getCorreo());

        Sucursal actualizada = sucursalRepo.save(sucursal);
        return toResponseDTO(actualizada);
    }

    public SucursalResponseDTO desactivar(Long id) {
        log.info("Desactivando sucursal con ID: {}", id);
        Sucursal sucursal = sucursalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal", id));

        sucursal.setActiva(false);
        // Desactivación lógica en cascada para sus salas
        if (sucursal.getSalas() != null) {
            sucursal.getSalas().forEach(sala -> sala.setActiva(false));
        }

        Sucursal guardada = sucursalRepo.save(sucursal);
        return toResponseDTO(guardada);
    }

    public SucursalResponseDTO activar(Long id) {
        log.info("Activando sucursal con ID: {}", id);
        Sucursal sucursal = sucursalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal", id));

        sucursal.setActiva(true);
        Sucursal guardada = sucursalRepo.save(sucursal);
        return toResponseDTO(guardada);
    }

    public SucursalResponseDTO toResponseDTO(Sucursal s) {
        return SucursalResponseDTO.builder()
                .idSucursal(s.getIdSucursal())
                .nombre(s.getNombre())
                .ciudad(s.getCiudad())
                .direccion(s.getDireccion())
                .telefono(s.getTelefono())
                .correo(s.getCorreo())
                .activa(s.getActiva())
                .salas(s.getSalas() != null ? s.getSalas().stream().map(this::toSalaResponseDTO).collect(Collectors.toList()) : List.of())
                .build();
    }

    public SalaResponseDTO toSalaResponseDTO(Sala s) {
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
