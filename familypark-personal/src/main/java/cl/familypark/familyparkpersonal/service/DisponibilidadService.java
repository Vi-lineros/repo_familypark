package cl.familypark.familyparkpersonal.service;

import cl.familypark.familyparkpersonal.dto.request.DisponibilidadRequestDTO;
import cl.familypark.familyparkpersonal.dto.response.DisponibilidadResponseDTO;
import cl.familypark.familyparkpersonal.exception.BadRequestException;
import cl.familypark.familyparkpersonal.exception.ConflictException;
import cl.familypark.familyparkpersonal.exception.ResourceNotFoundException;
import cl.familypark.familyparkpersonal.model.Disponibilidad;
import cl.familypark.familyparkpersonal.model.Personal;
import cl.familypark.familyparkpersonal.repository.DisponibilidadRepository;
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
public class DisponibilidadService {

    private final DisponibilidadRepository disponibilidadRepo;
    private final PersonalRepository personalRepo;

    @Transactional(readOnly = true)
    public List<DisponibilidadResponseDTO> listarTodas() {
        log.debug("Listando todas las disponibilidades.");
        return disponibilidadRepo.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DisponibilidadResponseDTO> listarPorPersonal(Long idPersonal, boolean soloActivas) {
        log.debug("Listando disponibilidades por personal: {}, soloActivas: {}", idPersonal, soloActivas);
        List<Disponibilidad> lista = soloActivas 
                ? disponibilidadRepo.findByPersonal_IdPersonalAndActivaTrue(idPersonal)
                : disponibilidadRepo.findByPersonal_IdPersonal(idPersonal);
        return lista.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DisponibilidadResponseDTO obtenerPorId(Long id) {
        log.debug("Obteniendo disponibilidad por ID: {}", id);
        Disponibilidad disp = disponibilidadRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disponibilidad", id));
        return toResponseDTO(disp);
    }

    public DisponibilidadResponseDTO crear(DisponibilidadRequestDTO dto) {
        log.info("Creando disponibilidad para personal ID: {} en día {}", dto.getIdPersonal(), dto.getDiaSemana());
        Personal personal = personalRepo.findById(dto.getIdPersonal())
                .orElseThrow(() -> new ResourceNotFoundException("Personal", dto.getIdPersonal()));

        if (!personal.getActivo()) {
            throw new BadRequestException("No se puede registrar disponibilidad para un trabajador inactivo.");
        }

        if (dto.getHoraFin().isBefore(dto.getHoraInicio()) || dto.getHoraFin().equals(dto.getHoraInicio())) {
            throw new BadRequestException("La hora de fin debe ser posterior a la de inicio.");
        }

        // Validar traslape/solapamiento horario para el mismo trabajador y día de la semana
        validarTraslape(dto.getIdPersonal(), null, dto);

        Disponibilidad disp = Disponibilidad.builder()
                .personal(personal)
                .diaSemana(dto.getDiaSemana())
                .horaInicio(dto.getHoraInicio())
                .horaFin(dto.getHoraFin())
                .activa(true)
                .build();

        Disponibilidad guardada = disponibilidadRepo.save(disp);
        return toResponseDTO(guardada);
    }

    public DisponibilidadResponseDTO actualizar(Long id, DisponibilidadRequestDTO dto) {
        log.info("Actualizando disponibilidad con ID: {}", id);
        Disponibilidad disp = disponibilidadRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disponibilidad", id));

        if (dto.getHoraFin().isBefore(dto.getHoraInicio()) || dto.getHoraFin().equals(dto.getHoraInicio())) {
            throw new BadRequestException("La hora de fin debe ser posterior a la de inicio.");
        }

        // Si se cambia de personal
        if (!disp.getPersonal().getIdPersonal().equals(dto.getIdPersonal())) {
            Personal personal = personalRepo.findById(dto.getIdPersonal())
                    .orElseThrow(() -> new ResourceNotFoundException("Personal", dto.getIdPersonal()));
            if (!personal.getActivo()) {
                throw new BadRequestException("No se puede asignar disponibilidad a un trabajador inactivo.");
            }
            disp.setPersonal(personal);
        }

        // Validar traslape
        validarTraslape(dto.getIdPersonal(), id, dto);

        disp.setDiaSemana(dto.getDiaSemana());
        disp.setHoraInicio(dto.getHoraInicio());
        disp.setHoraFin(dto.getHoraFin());

        Disponibilidad actualizada = disponibilidadRepo.save(disp);
        return toResponseDTO(actualizada);
    }

    public DisponibilidadResponseDTO desactivar(Long id) {
        log.info("Desactivando disponibilidad con ID: {}", id);
        Disponibilidad disp = disponibilidadRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disponibilidad", id));
        disp.setActiva(false);
        Disponibilidad guardada = disponibilidadRepo.save(disp);
        return toResponseDTO(guardada);
    }

    public DisponibilidadResponseDTO activar(Long id) {
        log.info("Activando disponibilidad con ID: {}", id);
        Disponibilidad disp = disponibilidadRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disponibilidad", id));
        if (!disp.getPersonal().getActivo()) {
            throw new BadRequestException("No se puede activar disponibilidad si el trabajador está inactivo.");
        }
        disp.setActiva(true);
        Disponibilidad guardada = disponibilidadRepo.save(disp);
        return toResponseDTO(guardada);
    }

    private void validarTraslape(Long idPersonal, Long idDisponibilidadActual, DisponibilidadRequestDTO dto) {
        List<Disponibilidad> disponibilidades = disponibilidadRepo.findByPersonal_IdPersonalAndActivaTrue(idPersonal);
        for (Disponibilidad d : disponibilidades) {
            // Ignorar la disponibilidad actual que se está actualizando
            if (idDisponibilidadActual != null && d.getIdDisponibilidad().equals(idDisponibilidadActual)) {
                continue;
            }
            if (d.getDiaSemana() == dto.getDiaSemana()) {
                // Hay traslape si (inicio1 < fin2) y (inicio2 < fin1)
                boolean traslapa = dto.getHoraInicio().isBefore(d.getHoraFin()) 
                        && d.getHoraInicio().isBefore(dto.getHoraFin());
                if (traslapa) {
                    throw new ConflictException(String.format(
                            "Hay un conflicto horario: ya existe disponibilidad registrada de %s a %s para el día %s.",
                            d.getHoraInicio(), d.getHoraFin(), d.getDiaSemana()));
                }
            }
        }
    }

    public DisponibilidadResponseDTO toResponseDTO(Disponibilidad d) {
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
