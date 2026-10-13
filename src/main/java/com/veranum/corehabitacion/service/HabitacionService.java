package com.veranum.corehabitacion.service;

import com.veranum.corehabitacion.dto.EstadoHabitacionRequest;
import com.veranum.corehabitacion.dto.HabitacionRequest;
import com.veranum.corehabitacion.dto.HabitacionResponse;
import com.veranum.corehabitacion.exception.BusinessException;
import com.veranum.corehabitacion.exception.ResourceNotFoundException;
import com.veranum.corehabitacion.model.Habitacion;
import com.veranum.corehabitacion.repository.HabitacionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitacionService {

    private static final Logger log =
            LoggerFactory.getLogger(HabitacionService.class);

    private final HabitacionRepository habitacionRepository;

    public HabitacionService(
            HabitacionRepository habitacionRepository) {

        this.habitacionRepository = habitacionRepository;
    }

    public HabitacionResponse crear(
            HabitacionRequest request) {

        log.info(
                "Creando habitación número {}",
                request.getNumero()
        );

        if (habitacionRepository.existsByNumero(
                request.getNumero())) {

            throw new BusinessException(
                    "Ya existe una habitación con el número "
                            + request.getNumero()
            );
        }

        Habitacion habitacion = new Habitacion();

        habitacion.setNumero(
                request.getNumero()
        );

        habitacion.setTipo(
                request.getTipo().toUpperCase()
        );

        habitacion.setPrecioPorNoche(
                request.getPrecioPorNoche()
        );

        habitacion.setEstado(
                request.getEstado().toUpperCase()
        );

        Habitacion guardada =
                habitacionRepository.save(habitacion);

        log.info(
                "Habitación creada con ID {}",
                guardada.getId()
        );

        return convertirResponse(guardada);
    }

    public List<HabitacionResponse> obtenerTodas() {

        return habitacionRepository
                .findAll()
                .stream()
                .map(this::convertirResponse)
                .toList();
    }

    public HabitacionResponse obtenerPorId(Long id) {

        return convertirResponse(
                buscarEntidad(id)
        );
    }

    public List<HabitacionResponse> obtenerDisponibles() {

        return habitacionRepository
                .findByEstadoIgnoreCase("DISPONIBLE")
                .stream()
                .map(this::convertirResponse)
                .toList();
    }

    public HabitacionResponse actualizar(
            Long id,
            HabitacionRequest request) {

        Habitacion habitacion =
                buscarEntidad(id);

        if (habitacionRepository
                .existsByNumeroAndIdNot(
                        request.getNumero(),
                        id
                )) {

            throw new BusinessException(
                    "Ya existe otra habitación con el número "
                            + request.getNumero()
            );
        }

        habitacion.setNumero(
                request.getNumero()
        );

        habitacion.setTipo(
                request.getTipo().toUpperCase()
        );

        habitacion.setPrecioPorNoche(
                request.getPrecioPorNoche()
        );

        habitacion.setEstado(
                request.getEstado().toUpperCase()
        );

        Habitacion actualizada =
                habitacionRepository.save(habitacion);

        log.info(
                "Habitación {} actualizada",
                id
        );

        return convertirResponse(actualizada);
    }

    public HabitacionResponse actualizarEstado(
            Long id,
            EstadoHabitacionRequest request) {

        Habitacion habitacion =
                buscarEntidad(id);

        habitacion.setEstado(
                request.getEstado().toUpperCase()
        );

        Habitacion actualizada =
                habitacionRepository.save(habitacion);

        log.info(
                "Estado de habitación {} cambiado a {}",
                id,
                request.getEstado()
        );

        return convertirResponse(actualizada);
    }

    public void eliminar(Long id) {

        Habitacion habitacion =
                buscarEntidad(id);

        habitacionRepository.delete(habitacion);

        log.info(
                "Habitación {} eliminada",
                id
        );
    }

    private Habitacion buscarEntidad(Long id) {

        return habitacionRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Habitación no encontrada con ID: "
                                        + id
                        )
                );
    }

    private HabitacionResponse convertirResponse(
            Habitacion habitacion) {

        HabitacionResponse response =
                new HabitacionResponse();

        response.setId(
                habitacion.getId()
        );

        response.setNumero(
                habitacion.getNumero()
        );

        response.setTipo(
                habitacion.getTipo()
        );

        response.setPrecioPorNoche(
                habitacion.getPrecioPorNoche()
        );

        response.setEstado(
                habitacion.getEstado()
        );

        return response;
    }
}
