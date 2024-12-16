package com.ar.duxsoftware.futbol.service;

import com.ar.duxsoftware.futbol.request.SignInRequest;
import com.ar.duxsoftware.futbol.response.SignInResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private AuthService authService;

    @Test
    @Order(1)
    public void testSignIn_Success() {
        final SignInRequest signInRequest = new SignInRequest(
                "test",
                "12345"
        );
        final Authentication mockAuthentication = mock(Authentication.class);
        final String token = "token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);
        when(mockAuthentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(any())).thenReturn(token);

        final SignInResponse result = authService.signIn(signInRequest);

        assertNotNull(result);
        assertEquals(token, result.getToken());
    }

    @Test
    @Order(2)
    public void testSignIn_BadCredentials() {
        final SignInRequest signInRequest = new SignInRequest(
                "test",
                "12345"
        );
        final Authentication mockAuthentication = mock(Authentication.class);
        final String token = "token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);
        when(mockAuthentication.isAuthenticated()).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> authService.signIn(signInRequest));
        verify(jwtService, never()).generateToken(any());
    }
}
