package com.ar.duxsoftware.futbol.service;

import com.ar.duxsoftware.futbol.entity.Equipo;
import com.ar.duxsoftware.futbol.exception.ResourceNotFoundException;
import com.ar.duxsoftware.futbol.repository.EquipoRepository;
import com.ar.duxsoftware.futbol.request.EquipoRequest;
import com.ar.duxsoftware.futbol.response.EquipoResponse;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@Service
public class EquipoService {
    private final EquipoRepository equipoRepository;

    public EquipoService(final EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public List<EquipoResponse> getAllEquipos() {
        return equipoRepository.findAll()
                .stream()
                .map(equipo -> new EquipoResponse(
                        equipo.getId(),
                        equipo.getNombre(),
                        equipo.getLiga(),
                        equipo.getPais()
                ))
                .collect(Collectors.toList());
    }

    public EquipoResponse getEquipoById(final Integer id) {
        final Equipo equipo = getEquipo(id);

        return new EquipoResponse(equipo.getId(), equipo.getNombre(), equipo.getLiga(), equipo.getPais());
    }

    public List<EquipoResponse> getEquiposByNombre(final String nombre) {
        final ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withMatcher("nombre", contains().ignoreCase());
        final Equipo probe = new Equipo();

        probe.setNombre(nombre);

        return equipoRepository.findAll(Example.of(probe, matcher))
                .stream()
                .map(equipo -> new EquipoResponse(
                        equipo.getId(),
                        equipo.getNombre(),
                        equipo.getLiga(),
                        equipo.getPais()
                ))
                .collect(Collectors.toList());
    }

    public EquipoResponse createEquipo(final EquipoRequest request) {
        final Equipo createdEquipo = equipoRepository.save(new Equipo(
                request.nombre(),
                request.liga(),
                request.pais()
        ));

        return new EquipoResponse(
                createdEquipo.getId(),
                createdEquipo.getNombre(),
                createdEquipo.getLiga(),
                createdEquipo.getPais()
        );
    }

    public EquipoResponse updateEquipo(final Integer id, final EquipoRequest request) {
        final Equipo equipo = getEquipo(id);

        equipo.setNombre(request.nombre());
        equipo.setLiga(request.liga());
        equipo.setPais(request.pais());

        final Equipo updatedEquipo = equipoRepository.save(equipo);

        return new EquipoResponse(
                updatedEquipo.getId(),
                updatedEquipo.getNombre(),
                updatedEquipo.getLiga(),
                updatedEquipo.getPais()
        );
    }

    public void deleteEquipo(final Integer id) {
        final Equipo equipo = getEquipo(id);

        equipoRepository.deleteById(equipo.getId());
    }

    private Equipo getEquipo(final Integer id) {
        return equipoRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado"));
    }
}
