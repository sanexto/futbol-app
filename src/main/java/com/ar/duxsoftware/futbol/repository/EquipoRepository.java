package com.ar.duxsoftware.futbol.repository;

import com.ar.duxsoftware.futbol.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {}
