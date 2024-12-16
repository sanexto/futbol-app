package com.ar.duxsoftware.futbol.exception;

import com.ar.duxsoftware.futbol.response.ErrorResponse;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String NOT_FOUND_MESSAGE = "Recurso no encontrado";
    private static final String BAD_REQUEST_MESSAGE = "La solicitud es invalida";

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            final NoResourceFoundException exception,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        final ErrorResponse errorResponse = new ErrorResponse(
                NOT_FOUND_MESSAGE,
                HttpStatus.NOT_FOUND.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException exception,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        final ErrorResponse errorResponse = new ErrorResponse(
                BAD_REQUEST_MESSAGE,
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final HttpMessageNotReadableException exception,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        final ErrorResponse errorResponse = new ErrorResponse(
                BAD_REQUEST_MESSAGE,
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException exception,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        final ErrorResponse errorResponse = new ErrorResponse(
                BAD_REQUEST_MESSAGE,
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException() {
        final ErrorResponse errorResponse = new ErrorResponse(
                BAD_REQUEST_MESSAGE,
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException() {
        final ErrorResponse errorResponse = new ErrorResponse(
                BAD_REQUEST_MESSAGE,
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(final ResourceNotFoundException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(
            final ResourceAlreadyExistsException exception
    ) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException() {
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<ErrorResponse> handleJWTCreationException(final JWTCreationException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(final Exception exception) {
        final ErrorResponse errorResponse = new ErrorResponse(
                String.format("Error inesperado: %s", exception.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
