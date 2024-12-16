package com.ar.duxsoftware.futbol.controller;

import com.ar.duxsoftware.futbol.request.SignInRequest;
import com.ar.duxsoftware.futbol.request.SignUpRequest;
import com.ar.duxsoftware.futbol.response.ErrorResponse;
import com.ar.duxsoftware.futbol.response.SignInResponse;
import com.ar.duxsoftware.futbol.response.SignUpResponse;
import com.ar.duxsoftware.futbol.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Registro de usuario",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo usuario.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SignUpRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"username\": \"test\", \"password\": \"12345\"}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Registro exitoso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SignUpResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"id\": 1, \"username\": \"test\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "El usuario ya existe.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"mensaje\": \"El usuario ya existe\", \"codigo\": 409}"
                                    )
                            )
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid final SignUpRequest request) {
        return new ResponseEntity<>(authService.signUp(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Autenticación de usuario",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciales del usuario.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SignInRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"username\": \"test\", \"password\": \"12345\"}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticación exitosa.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SignInResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNzM0MzY0NDg5fQ.-xgLWSfGpZZOcS3b_7lnEn6uVa9EVkNkKILd4n7Xy4M\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Autenticación fallida.",
                            content = @Content()
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid final SignInRequest request) {
        return new ResponseEntity<>(authService.signIn(request), HttpStatus.OK);
    }
}
