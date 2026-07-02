package com.medisalud.infrastructure.web.controller;

import com.medisalud.application.dto.DoctorDto;
import com.medisalud.application.usecase.*;
import com.medisalud.infrastructure.web.request.DoctorRequest;
import com.medisalud.infrastructure.web.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctors")
@Tag(name = "Médicos")
public class DoctorController {
    private final RegisterDoctorUseCase registerDoctor;
    private final ListDoctorsUseCase listDoctors;
    private final FindDoctorUseCase findDoctor;
    private final DeleteDoctorUseCase deleteDoctor;

    public DoctorController(RegisterDoctorUseCase registerDoctor, ListDoctorsUseCase listDoctors, FindDoctorUseCase findDoctor, DeleteDoctorUseCase deleteDoctor) {
        this.registerDoctor = registerDoctor;
        this.listDoctors = listDoctors;
        this.findDoctor = findDoctor;
        this.deleteDoctor = deleteDoctor;
    }

    @Operation(summary = "Registrar un nuevo médico", description = "Crea un registro de médico en el sistema")
    @ApiResponse(responseCode = "201", description = "Médico creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(schema = @Schema(implementation = ApiErrorResponse.ValidationErrorResponse.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto create(@Valid @RequestBody DoctorRequest request) {
        return registerDoctor.execute(new DoctorDto(null, request.name(), request.specialty(), request.email(), request.phone()));
    }

    @Operation(summary = "Listar todos los médicos", description = "Obtiene una lista de todos los médicos registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public List<DoctorDto> list() { 
        return listDoctors.execute(); 
    }

    @Operation(summary = "Obtener un médico por ID", description = "Busca un médico específico mediante su identificador único")
    @ApiResponse(responseCode = "200", description = "Médico encontrado")
    @ApiResponse(responseCode = "404", description = "Médico no encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.NotFoundErrorResponse.class)))
    @GetMapping("/{id}")
    public DoctorDto get(@PathVariable UUID id) { 
        return findDoctor.execute(id); 
    }

    @Operation(summary = "Actualizar un médico", description = "Actualiza la información de un médico existente")
    @ApiResponse(responseCode = "200", description = "Médico actualizado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(schema = @Schema(implementation = ApiErrorResponse.ValidationErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Médico no encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.NotFoundErrorResponse.class)))
    @PutMapping("/{id}")
    public DoctorDto update(@PathVariable UUID id, @Valid @RequestBody DoctorRequest request) {
        return registerDoctor.execute(new DoctorDto(id, request.name(), request.specialty(), request.email(), request.phone()));
    }

    @Operation(summary = "Eliminar un médico", description = "Elimina el registro de un médico del sistema")
    @ApiResponse(responseCode = "204", description = "Médico eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Médico no encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.NotFoundErrorResponse.class)))
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) { 
        deleteDoctor.execute(id); 
    }
}