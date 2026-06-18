package cl.familypark.familyparkpersonal.service;

import cl.familypark.familyparkpersonal.dto.request.CargoRequestDTO;
import cl.familypark.familyparkpersonal.dto.response.CargoResponseDTO;
import cl.familypark.familyparkpersonal.exception.ConflictException;
import cl.familypark.familyparkpersonal.exception.ResourceNotFoundException;
import cl.familypark.familyparkpersonal.model.Cargo;
import cl.familypark.familyparkpersonal.repository.CargoRepository;
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
public class CargoService {

    private final CargoRepository cargoRepo;

    @Transactional(readOnly = true)
    public List<CargoResponseDTO> listarTodos() {
        log.debug("Listando todos los cargos.");
        return cargoRepo.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CargoResponseDTO> listarActivos() {
        log.debug("Listando cargos activos.");
        return cargoRepo.findByActivoTrue().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CargoResponseDTO obtenerPorId(Long id) {
        log.debug("Obteniendo cargo por ID: {}", id);
        Cargo cargo = cargoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cargo", id));
        return toResponseDTO(cargo);
    }

    public CargoResponseDTO crear(CargoRequestDTO dto) {
        log.info("Creando cargo: {}", dto.getNombre());
        if (cargoRepo.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new ConflictException("Ya existe un cargo con el nombre: " + dto.getNombre());
        }

        Cargo cargo = Cargo.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .activo(true)
                .build();

        Cargo guardado = cargoRepo.save(cargo);
        return toResponseDTO(guardado);
    }

    public CargoResponseDTO actualizar(Long id, CargoRequestDTO dto) {
        log.info("Actualizando cargo con ID: {}", id);
        Cargo cargo = cargoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cargo", id));

        if (!cargo.getNombre().equalsIgnoreCase(dto.getNombre()) && 
                cargoRepo.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new ConflictException("Ya existe otro cargo con el nombre: " + dto.getNombre());
        }

        cargo.setNombre(dto.getNombre());
        cargo.setDescripcion(dto.getDescripcion());

        Cargo actualizado = cargoRepo.save(cargo);
        return toResponseDTO(actualizado);
    }

    public CargoResponseDTO desactivar(Long id) {
        log.info("Desactivando cargo con ID: {}", id);
        Cargo cargo = cargoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cargo", id));
        cargo.setActivo(false);
        Cargo guardado = cargoRepo.save(cargo);
        return toResponseDTO(guardado);
    }

    public CargoResponseDTO activar(Long id) {
        log.info("Activando cargo con ID: {}", id);
        Cargo cargo = cargoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cargo", id));
        cargo.setActivo(true);
        Cargo guardado = cargoRepo.save(cargo);
        return toResponseDTO(guardado);
    }

    public CargoResponseDTO toResponseDTO(Cargo cargo) {
        return CargoResponseDTO.builder()
                .idCargo(cargo.getIdCargo())
                .nombre(cargo.getNombre())
                .descripcion(cargo.getDescripcion())
                .activo(cargo.getActivo())
                .build();
    }
}
