package com.medisalud.infrastructure.web.controller;

import com.medisalud.application.dto.AppointmentDto;
import com.medisalud.application.dto.TimeSlotDto;
import com.medisalud.application.usecase.*;
import com.medisalud.infrastructure.web.request.BookAppointmentRequest;
import com.medisalud.infrastructure.web.request.RescheduleAppointmentRequest;
import com.medisalud.infrastructure.web.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
@Tag(name = "Citas")
public class AppointmentController {
    private final BookAppointmentUseCase book;
    private final CancelAppointmentUseCase cancel;
    private final RescheduleAppointmentUseCase reschedule;
    private final FindAvailableSlotsUseCase availability;
    private final ListAppointmentsUseCase list;

    public AppointmentController(BookAppointmentUseCase book, CancelAppointmentUseCase cancel, RescheduleAppointmentUseCase reschedule, FindAvailableSlotsUseCase availability, ListAppointmentsUseCase list) {
        this.book = book;
        this.cancel = cancel;
        this.reschedule = reschedule;
        this.availability = availability;
        this.list = list;
    }

    @Operation(summary = "Agendar una cita", description = "Reserva una nueva cita médica validando reglas de negocio")
    @ApiResponse(responseCode = "201", description = "Cita agendada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(schema = @Schema(implementation = ApiErrorResponse.ValidationErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Violación de reglas de negocio", content = @Content(schema = @Schema(implementation = ApiErrorResponse.BusinessRuleErrorResponse.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDto book(@Valid @RequestBody BookAppointmentRequest request) { return book.execute(request.doctorId(), request.patientId(), request.start()); }

    @Operation(summary = "Cancelar una cita", description = "Cancela una cita existente mediante su ID")
    @ApiResponse(responseCode = "200", description = "Cita cancelada exitosamente")
    @ApiResponse(responseCode = "404", description = "Cita no encontrada", content = @Content(schema = @Schema(implementation = ApiErrorResponse.NotFoundErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "No se puede cancelar la cita (regla de negocio)", content = @Content(schema = @Schema(implementation = ApiErrorResponse.BusinessRuleErrorResponse.class)))
    @PostMapping("/{id}/cancel")
    public AppointmentDto cancel(@PathVariable UUID id) { return cancel.execute(id); }

    @Operation(summary = "Reprogramar una cita", description = "Cambia la fecha/hora de una cita existente")
    @ApiResponse(responseCode = "201", description = "Cita reprogramada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(schema = @Schema(implementation = ApiErrorResponse.ValidationErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Cita no encontrada", content = @Content(schema = @Schema(implementation = ApiErrorResponse.NotFoundErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Violación de reglas de negocio", content = @Content(schema = @Schema(implementation = ApiErrorResponse.BusinessRuleErrorResponse.class)))
    @PostMapping("/{id}/reschedule")
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDto reschedule(@PathVariable UUID id, @Valid @RequestBody RescheduleAppointmentRequest request) { return reschedule.execute(id, request.start()); }

    @Operation(summary = "Consultar disponibilidad", description = "Obtiene las franjas horarias disponibles de un médico para un día específico")
    @ApiResponse(responseCode = "200", description = "Disponibilidad obtenida")
    @GetMapping("/availability")
    public List<TimeSlotDto> availability(
            @Parameter(description = "ID del médico") @RequestParam UUID doctorId,
            @Parameter(description = "Fecha a consultar (ISO format)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) { return availability.execute(doctorId, date); }

    @Operation(summary = "Listar citas con filtros", description = "Obtiene una lista de citas filtradas por médico, paciente o fecha")
    @ApiResponse(responseCode = "200", description = "Lista de citas obtenida")
    @GetMapping
    public List<AppointmentDto> list(
            @Parameter(description = "ID del médico (opcional)") @RequestParam(required = false) UUID doctorId,
            @Parameter(description = "ID del paciente (opcional)") @RequestParam(required = false) UUID patientId,
            @Parameter(description = "Fecha (opcional, ISO format)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) { return list.execute(doctorId, patientId, date); }
}