package com.veranum.corehabitacion.repository;

import com.veranum.corehabitacion.model.Habitacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    boolean existsByNumero(String numero);

    boolean existsByNumeroAndIdNot(String numero, Long id);

    List<Habitacion> findByEstadoIgnoreCase(String estado);
}
