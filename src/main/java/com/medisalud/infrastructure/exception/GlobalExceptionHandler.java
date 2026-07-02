package com.medisalud.infrastructure.exception;

import com.medisalud.domain.exception.*;
import com.medisalud.infrastructure.web.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiErrorResponse notFound(NotFoundException exception, HttpServletRequest request) { return error(HttpStatus.NOT_FOUND, exception.getCode(), exception.getMessage(), request); }

    @ExceptionHandler(AppointmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiErrorResponse appointmentNotFound(AppointmentNotFoundException exception, HttpServletRequest request) { return error(HttpStatus.NOT_FOUND, exception.getCode(), exception.getMessage(), request); }

    @ExceptionHandler({
            DoctorUnavailableException.class,
            PatientConflictException.class,
            PenaltyException.class,
            WorkingHoursException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    ApiErrorResponse businessConflict(BusinessException exception, HttpServletRequest request) { return error(HttpStatus.CONFLICT, exception.getCode(), exception.getMessage(), request); }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiErrorResponse business(BusinessException exception, HttpServletRequest request) { return error(HttpStatus.BAD_REQUEST, exception.getCode(), exception.getMessage(), request); }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiErrorResponse handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Validación fallida");
        return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", message, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiErrorResponse badRequest(IllegalArgumentException exception, HttpServletRequest request) { return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", exception.getMessage(), request); }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiErrorResponse unexpected(Exception exception, HttpServletRequest request) { return error(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Error interno", request); }

    private ApiErrorResponse error(HttpStatus status, String code, String message, HttpServletRequest request) {
        return new ApiErrorResponse(Instant.now(), status.value(), status.getReasonPhrase(), code, message, request.getRequestURI());
    }
}