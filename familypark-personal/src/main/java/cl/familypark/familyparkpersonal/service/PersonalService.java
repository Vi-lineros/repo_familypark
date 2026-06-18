package cl.familypark.familyparkpersonal.service;

import cl.familypark.familyparkpersonal.dto.request.PersonalRequestDTO;
import cl.familypark.familyparkpersonal.dto.response.CargoResponseDTO;
import cl.familypark.familyparkpersonal.dto.response.DisponibilidadResponseDTO;
import cl.familypark.familyparkpersonal.dto.response.PersonalResponseDTO;
import cl.familypark.familyparkpersonal.exception.BadRequestException;
import cl.familypark.familyparkpersonal.exception.ConflictException;
import cl.familypark.familyparkpersonal.exception.ResourceNotFoundException;
import cl.familypark.familyparkpersonal.model.Cargo;
import cl.familypark.familyparkpersonal.model.Disponibilidad;
import cl.familypark.familyparkpersonal.model.Personal;
import cl.familypark.familyparkpersonal.repository.CargoRepository;
import cl.familypark.familyparkpersonal.repository.PersonalRepository;
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
public class PersonalService {

    private final PersonalRepository personalRepo;
    private final CargoRepository cargoRepo;

    @Transactional(readOnly = true)
    public List<PersonalResponseDTO> listarTodos() {
        log.debug("Listando todos los trabajadores.");
        return personalRepo.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PersonalResponseDTO> listarActivos() {
        log.debug("Listando trabajadores activos.");
        return personalRepo.findByActivoTrue().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PersonalResponseDTO obtenerPorId(Long id) {
        log.debug("Obteniendo trabajador por ID: {}", id);
        Personal personal = personalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personal", id));
        return toResponseDTO(personal);
    }

    public PersonalResponseDTO crear(PersonalRequestDTO dto) {
        log.info("Creando trabajador: {} {} con correo {}", dto.getNombre(), dto.getApellido(), dto.getCorreo());
        if (personalRepo.existsByCorreoIgnoreCase(dto.getCorreo())) {
            throw new ConflictException("Ya existe un trabajador registrado con el correo: " + dto.getCorreo());
        }

        Cargo cargo = cargoRepo.findById(dto.getIdCargo())
                .orElseThrow(() -> new ResourceNotFoundException("Cargo", dto.getIdCargo()));
        if (!cargo.getActivo()) {
            throw new BadRequestException("No se puede asignar un cargo inactivo.");
        }

        Personal personal = Personal.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .correo(dto.getCorreo())
                .telefono(dto.getTelefono())
                .cargo(cargo)
                .activo(true)
                .build();

        Personal guardado = personalRepo.save(personal);
        return toResponseDTO(guardado);
    }

    public PersonalResponseDTO actualizar(Long id, PersonalRequestDTO dto) {
        log.info("Actualizando trabajador con ID: {}", id);
        Personal personal = personalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personal", id));

        if (!personal.getCorreo().equalsIgnoreCase(dto.getCorreo()) && 
                personalRepo.existsByCorreoIgnoreCase(dto.getCorreo())) {
            throw new ConflictException("Ya existe otro trabajador con el correo: " + dto.getCorreo());
        }

        Cargo cargo = cargoRepo.findById(dto.getIdCargo())
                .orElseThrow(() -> new ResourceNotFoundException("Cargo", dto.getIdCargo()));
        if (!cargo.getActivo()) {
            throw new BadRequestException("No se puede asignar un cargo inactivo.");
        }

        personal.setNombre(dto.getNombre());
        personal.setApellido(dto.getApellido());
        personal.setCorreo(dto.getCorreo());
        personal.setTelefono(dto.getTelefono());
        personal.setCargo(cargo);

        Personal actualizado = personalRepo.save(personal);
        return toResponseDTO(actualizado);
    }

    public PersonalResponseDTO desactivar(Long id) {
        log.info("Desactivando trabajador con ID: {}", id);
        Personal personal = personalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personal", id));
        personal.setActivo(false);
        // Desactivamos lógicamente sus disponibilidades
        if (personal.getDisponibilidades() != null) {
            personal.getDisponibilidades().forEach(disp -> disp.setActiva(false));
        }
        Personal guardado = personalRepo.save(personal);
        return toResponseDTO(guardado);
    }

    public PersonalResponseDTO activar(Long id) {
        log.info("Activando trabajador con ID: {}", id);
        Personal personal = personalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personal", id));
        if (!personal.getCargo().getActivo()) {
            throw new BadRequestException("No se puede activar el trabajador porque su cargo está inactivo.");
        }
        personal.setActivo(true);
        Personal guardado = personalRepo.save(personal);
        return toResponseDTO(guardado);
    }

    public PersonalResponseDTO toResponseDTO(Personal p) {
        return PersonalResponseDTO.builder()
                .idPersonal(p.getIdPersonal())
                .nombre(p.getNombre())
                .apellido(p.getApellido())
                .correo(p.getCorreo())
                .telefono(p.getTelefono())
                .cargo(CargoResponseDTO.builder()
                        .idCargo(p.getCargo().getIdCargo())
                        .nombre(p.getCargo().getNombre())
                        .descripcion(p.getCargo().getDescripcion())
                        .activo(p.getCargo().getActivo())
                        .build())
                .activo(p.getActivo())
                .disponibilidades(p.getDisponibilidades() != null 
                        ? p.getDisponibilidades().stream().map(this::toDispResponseDTO).collect(Collectors.toList())
                        : List.of())
                .build();
    }

    private DisponibilidadResponseDTO toDispResponseDTO(Disponibilidad d) {
        return DisponibilidadResponseDTO.builder()
                .idDisponibilidad(d.getIdDisponibilidad())
                .idPersonal(d.getPersonal() != null ? d.getPersonal().getIdPersonal() : null)
                .diaSemana(d.getDiaSemana())
                .horaInicio(d.getHoraInicio())
                .horaFin(d.getHoraFin())
                .activa(d.getActiva())
                .build();
    }
}
