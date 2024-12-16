package com.ar.duxsoftware.futbol.service;

import com.ar.duxsoftware.futbol.entity.User;
import com.ar.duxsoftware.futbol.exception.ResourceAlreadyExistsException;
import com.ar.duxsoftware.futbol.request.SignInRequest;
import com.ar.duxsoftware.futbol.request.SignUpRequest;
import com.ar.duxsoftware.futbol.response.SignInResponse;
import com.ar.duxsoftware.futbol.response.SignUpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthService(
            final AuthenticationManager authenticationManager,
            final PasswordEncoder passwordEncoder,
            final UserService userService,
            final JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public SignUpResponse signUp(final SignUpRequest request) {
        if (userService.findByUsername(request.username()).isPresent()) {
            throw new ResourceAlreadyExistsException("El usuario ya existe");
        }

        final String encryptedPassword = passwordEncoder.encode(request.password());
        final User createdUser = userService.save(new User(request.username(), encryptedPassword));

        return new SignUpResponse(createdUser.getId(), createdUser.getUsername());
    }

    public SignInResponse signIn(final SignInRequest request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password()
                )
        );

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Usuario o contraseña incorrectos");
        }

        return new SignInResponse(jwtService.generateToken(authentication.getName()));
    }
}
