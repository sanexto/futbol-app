package com.ar.duxsoftware.futbol.service;

import com.ar.duxsoftware.futbol.entity.Equipo;
import com.ar.duxsoftware.futbol.exception.ResourceNotFoundException;
import com.ar.duxsoftware.futbol.repository.EquipoRepository;
import com.ar.duxsoftware.futbol.request.EquipoRequest;
import com.ar.duxsoftware.futbol.response.EquipoResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EquipoServiceTest {
    @Mock
    private EquipoRepository equipoRepository;
    @InjectMocks
    private EquipoService equipoService;

    @Test
    @Order(1)
    public void testGetAllEquipos() {
        final List<Equipo> equipos = Arrays.asList(
            new Equipo(1, "Real Madrid", "La Liga", "España"),
            new Equipo(2, "FC Barcelona", "La Liga", "España"),
            new Equipo(3, "Manchester United", "Premier League", "Inglaterra")
        );

        when(equipoRepository.findAll()).thenReturn(equipos);

        final List<EquipoResponse> result = equipoService.getAllEquipos();

        assertEquals(equipos.size(), result.size());

        for (int i = 0; i < result.size(); i++) {
            assertEquals(equipos.get(i).getId(), result.get(i).getId());
            assertEquals(equipos.get(i).getNombre(), result.get(i).getNombre());
            assertEquals(equipos.get(i).getLiga(), result.get(i).getLiga());
            assertEquals(equipos.get(i).getPais(), result.get(i).getPais());
        }

        verify(equipoRepository, times(1)).findAll();
    }

    @Test
    @Order(2)
    public void testGetEquipoById_Success() {
        final Integer id = 2;
        final Equipo equipo = new Equipo(id, "FC Barcelona", "La Liga", "España");

        when(equipoRepository.findById(id)).thenReturn(Optional.of(equipo));

        final EquipoResponse result = equipoService.getEquipoById(id);

        assertNotNull(result);
        assertEquals(equipo.getId(), result.getId());
        assertEquals(equipo.getNombre(), result.getNombre());
        assertEquals(equipo.getLiga(), result.getLiga());
        assertEquals(equipo.getPais(), result.getPais());
        verify(equipoRepository, times(1)).findById(id);
    }

    @Test
    @Order(3)
    public void testGetEquipoById_EquipoNotFound() {
        final Integer id = 31;

        when(equipoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> equipoService.getEquipoById(id));
        verify(equipoRepository, times(1)).findById(id);
    }

    @Test
    @Order(4)
    public void testGetEquiposByNombre() {
        final String nombre = "es";
        final List<Equipo> equipos = Arrays.asList(
                new Equipo(3, "Manchester United", "Premier League", "Inglaterra"),
                new Equipo(22, "Besiktas JK", "Süper Lig", "Turquía")
        );

        when(equipoRepository.findAll(any(Example.class))).thenReturn(equipos);

        final List<EquipoResponse> result = equipoService.getEquiposByNombre(nombre);

        assertEquals(equipos.size(), result.size());

        for (int i = 0; i < result.size(); i++) {
            assertEquals(equipos.get(i).getId(), result.get(i).getId());
            assertEquals(equipos.get(i).getNombre(), result.get(i).getNombre());
            assertEquals(equipos.get(i).getLiga(), result.get(i).getLiga());
            assertEquals(equipos.get(i).getPais(), result.get(i).getPais());
        }

        verify(equipoRepository, times(1)).findAll(any(Example.class));
    }

    @Test
    @Order(5)
    public void testCreateEquipo() {
        final EquipoRequest equipoRequest = new EquipoRequest(
                "Nuevo Equipo FC",
                "Nueva Liga",
                "Nuevo País"
        );
        final Equipo createdEquipo = new Equipo(
                26,
                equipoRequest.nombre(),
                equipoRequest.liga(),
                equipoRequest.pais()
        );

        when(equipoRepository.save(any(Equipo.class))).thenReturn(createdEquipo);

        final EquipoResponse result = equipoService.createEquipo(equipoRequest);

        assertNotNull(result);
        assertEquals(createdEquipo.getId(), result.getId());
        assertEquals(equipoRequest.nombre(), result.getNombre());
        assertEquals(equipoRequest.liga(), result.getLiga());
        assertEquals(equipoRequest.pais(), result.getPais());
        verify(equipoRepository, times(1)).save(any(Equipo.class));
    }

    @Test
    @Order(6)
    public void testUpdateEquipo_Success() {
        final Integer id = 17;
        final EquipoRequest equipoRequest = new EquipoRequest(
                "Nuevo Nombre",
                "Nueva Liga",
                "Nuevo País"
        );
        final Equipo equipo = new Equipo(id, "Galatasaray SK", "Süper Lig", "Turquía");
        final Equipo updatedEquipo = new Equipo(id, equipoRequest.nombre(), equipoRequest.liga(), equipoRequest.pais());

        when(equipoRepository.findById(id)).thenReturn(Optional.of(equipo));
        when(equipoRepository.save(equipo)).thenReturn(updatedEquipo);

        final EquipoResponse result = equipoService.updateEquipo(id, equipoRequest);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(equipoRequest.nombre(), result.getNombre());
        assertEquals(equipoRequest.liga(), result.getLiga());
        assertEquals(equipoRequest.pais(), result.getPais());
        verify(equipoRepository, times(1)).findById(id);
        verify(equipoRepository, times(1)).save(equipo);
    }

    @Test
    @Order(7)
    public void testUpdateEquipo_EquipoNotFound() {
        final Integer id = 66;
        final EquipoRequest equipoRequest = new EquipoRequest(
                "Nuevo Nombre",
                "Nueva Liga",
                "Nuevo País"
        );

        when(equipoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> equipoService.updateEquipo(id, equipoRequest));
        verify(equipoRepository, times(1)).findById(id);
        verify(equipoRepository, never()).save(any(Equipo.class));
    }

    @Test
    @Order(8)
    public void testDeleteEquipo_Success() {
        final Integer id = 23;
        final Equipo equipo = new Equipo(id, "SSC Napoli", "Serie A", "Italia");

        when(equipoRepository.findById(id)).thenReturn(Optional.of(equipo));

        equipoService.deleteEquipo(id);

        verify(equipoRepository, times(1)).findById(id);
        verify(equipoRepository, times(1)).deleteById(id);
    }

    @Test
    @Order(9)
    public void testDeleteEquipo_EquipoNotFound() {
        final Integer id = 23;

        when(equipoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> equipoService.deleteEquipo(id));
        verify(equipoRepository, times(1)).findById(id);
        verify(equipoRepository, never()).deleteById(id);
    }
}
