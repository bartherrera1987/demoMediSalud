package com.medisalud.infrastructure.web.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Respuesta de error de la API")
public record ApiErrorResponse(
        @Schema(description = "Marca de tiempo del error", example = "2024-07-02T13:28:00Z")
        Instant timestamp,
        @Schema(description = "Código de estado HTTP", example = "400")
        int status,
        @Schema(description = "Nombre del error HTTP", example = "Bad Request")
        String error,
        @Schema(description = "Código de error interno", example = "BUSINESS_RULE_VIOLATION")
        String code,
        @Schema(description = "Mensaje detallado del error", example = "Detalle del error ocurrido")
        String message,
        @Schema(description = "Ruta del endpoint que generó el error", example = "/api/v1/resource")
        String path) {

    @Schema(name = "NotFoundErrorResponse", description = "Error cuando el recurso no existe")
    public record NotFoundErrorResponse(
            @Schema(example = "2024-07-02T13:28:00Z") Instant timestamp,
            @Schema(example = "404") int status,
            @Schema(example = "Not Found") String error,
            @Schema(example = "RESOURCE_NOT_FOUND") String code,
            @Schema(example = "El recurso solicitado no fue encontrado") String message,
            @Schema(example = "/api/v1/resource/123") String path) {}

    @Schema(name = "BusinessRuleErrorResponse", description = "Error por violación de reglas de negocio")
    public record BusinessRuleErrorResponse(
            @Schema(example = "2024-07-02T13:28:00Z") Instant timestamp,
            @Schema(example = "409") int status,
            @Schema(example = "Conflict") String error,
            @Schema(example = "BUSINESS_RULE_VIOLATION") String code,
            @Schema(example = "El paciente tiene una penalización activa") String message,
            @Schema(example = "/api/v1/appointments") String path) {}

    @Schema(name = "ValidationErrorResponse", description = "Error por datos de entrada inválidos")
    public record ValidationErrorResponse(
            @Schema(example = "2024-07-02T13:28:00Z") Instant timestamp,
            @Schema(example = "400") int status,
            @Schema(example = "Bad Request") String error,
            @Schema(example = "VALIDATION_ERROR") String code,
            @Schema(example = "El campo 'email' es obligatorio") String message,
            @Schema(example = "/api/v1/patients") String path) {}
}