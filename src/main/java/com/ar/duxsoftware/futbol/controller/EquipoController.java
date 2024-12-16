package com.ar.duxsoftware.futbol.controller;

import com.ar.duxsoftware.futbol.request.EquipoRequest;
import com.ar.duxsoftware.futbol.response.EquipoResponse;
import com.ar.duxsoftware.futbol.response.ErrorResponse;
import com.ar.duxsoftware.futbol.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/equipos")
public class EquipoController {
    private final EquipoService equipoService;

    public EquipoController(final EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @Operation(
            summary = "Consulta de Todos los Equipos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Devuelve la lista de todos los equipos de fútbol registrados.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = EquipoResponse.class)),
                                    examples = @ExampleObject(
                                            value = "[{\"id\": 2, \"nombre\": \"FC Barcelona\", \"liga\": \"La Liga\", \"pais\": \"España\"}]"
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<EquipoResponse>> getAllEquipos() {
        return new ResponseEntity<>(equipoService.getAllEquipos(), HttpStatus.OK);
    }

    @Operation(
            summary = "Consulta de un Equipo por ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Devuelve la información del equipo correspondiente al ID proporcionado.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EquipoResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"id\": 2, \"nombre\": \"FC Barcelona\", \"liga\": \"La Liga\", \"pais\": \"España\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Si el equipo no existe.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"mensaje\": \"Equipo no encontrado\", \"codigo\": 404}"
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponse> getEquipoById(@PathVariable final Integer id) {
        return new ResponseEntity<>(equipoService.getEquipoById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Búsqueda de Equipos por Nombre",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Devuelve la lista de equipos cuyos nombres contienen el valor proporcionado en el parámetro de búsqueda.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = EquipoResponse.class)),
                                    examples = @ExampleObject(
                                            value = "[{\"id\": 1, \"nombre\": \"Real Madrid\", \"liga\": \"La Liga\", \"pais\": \"España\"}]"
                                    )
                            )
                    )
            }
    )
    @GetMapping("/buscar")
    public ResponseEntity<List<EquipoResponse>> getEquiposByNombre(@RequestParam final String nombre) {
        return new ResponseEntity<>(equipoService.getEquiposByNombre(nombre), HttpStatus.OK);
    }

    @Operation(
            summary = "Creación de un equipo",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo equipo.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EquipoRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"nombre\": \"Nuevo Equipo FC\", \"liga\": \"Nueva Liga\", \"pais\": \"Nuevo País\"}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Devuelve la información del equipo creado.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EquipoResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"id\": 26, \"nombre\": \"Nuevo Equipo FC\", \"liga\": \"Nueva Liga\", \"pais\": \"Nuevo País\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "La solicitud es inválida.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"mensaje\": \"La solicitud es invalida\", \"codigo\": 400}"
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<EquipoResponse> createEquipo(@RequestBody @Valid final EquipoRequest request) {
        return new ResponseEntity<>(equipoService.createEquipo(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Actualización de Información de un Equipo",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del equipo.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EquipoRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"nombre\": \"Nuevo Nombre\", \"liga\": \"Nueva Liga\", \"pais\": \"Nuevo País\"}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Devuelve la información actualizada del equipo.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EquipoResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"id\": 1, \"nombre\": \"Nuevo Nombre\", \"liga\": \"Nueva Liga\", \"pais\": \"Nuevo País\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Si el equipo no existe.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"mensaje\": \"Equipo no encontrado\", \"codigo\": 404}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "La solicitud es inválida.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"mensaje\": \"La solicitud es invalida\", \"codigo\": 400}"
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<EquipoResponse> updateEquipo(
            @PathVariable final Integer id,
            @RequestBody @Valid final EquipoRequest request
    ) {
        return new ResponseEntity<>(equipoService.updateEquipo(id, request), HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminación de un Equipo",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Sin contenido."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Si el equipo no existe.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"mensaje\": \"Equipo no encontrado\", \"codigo\": 404}"
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipo(@PathVariable final Integer id) {
        equipoService.deleteEquipo(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
