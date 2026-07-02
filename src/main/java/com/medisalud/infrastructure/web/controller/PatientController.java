package com.medisalud.infrastructure.web.controller;

import com.medisalud.application.dto.PatientDto;
import com.medisalud.application.usecase.*;
import com.medisalud.infrastructure.web.request.PatientRequest;
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
@RequestMapping("/api/v1/patients")
@Tag(name = "Pacientes")
public class PatientController {
    private final RegisterPatientUseCase registerPatient;
    private final ListPatientsUseCase listPatients;
    private final FindPatientUseCase findPatient;
    private final DeletePatientUseCase deletePatient;

    public PatientController(RegisterPatientUseCase registerPatient, ListPatientsUseCase listPatients, FindPatientUseCase findPatient, DeletePatientUseCase deletePatient) {
        this.registerPatient = registerPatient;
        this.listPatients = listPatients;
        this.findPatient = findPatient;
        this.deletePatient = deletePatient;
    }

    @Operation(summary = "Registrar un nuevo paciente", description = "Crea un registro de paciente en el sistema")
    @ApiResponse(responseCode = "201", description = "Paciente creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(schema = @Schema(implementation = ApiErrorResponse.ValidationErrorResponse.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto create(@Valid @RequestBody PatientRequest request) { 
        return registerPatient.execute(toDto(null, request)); 
    }

    @Operation(summary = "Listar todos los pacientes", description = "Obtiene una lista de todos los pacientes registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public List<PatientDto> list() { 
        return listPatients.execute(); 
    }

    @Operation(summary = "Obtener un paciente por ID", description = "Busca un paciente específico mediante su identificador único")
    @ApiResponse(responseCode = "200", description = "Paciente encontrado")
    @ApiResponse(responseCode = "404", description = "Paciente no encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.NotFoundErrorResponse.class)))
    @GetMapping("/{id}")
    public PatientDto get(@PathVariable UUID id) { 
        return findPatient.execute(id); 
    }

    @Operation(summary = "Actualizar un paciente", description = "Actualiza la información de un paciente existente")
    @ApiResponse(responseCode = "200", description = "Paciente actualizado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content(schema = @Schema(implementation = ApiErrorResponse.ValidationErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Paciente no encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.NotFoundErrorResponse.class)))
    @PutMapping("/{id}")
    public PatientDto update(@PathVariable UUID id, @Valid @RequestBody PatientRequest request) { 
        return registerPatient.execute(toDto(id, request)); 
    }

    @Operation(summary = "Eliminar un paciente", description = "Elimina el registro de un paciente del sistema")
    @ApiResponse(responseCode = "204", description = "Paciente eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Paciente no encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.NotFoundErrorResponse.class)))
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) { 
        deletePatient.execute(id); 
    }

    private PatientDto toDto(UUID id, PatientRequest request) { 
        return new PatientDto(id, request.name(), request.document(), request.email(), request.phone(), request.birthDate()); 
    }
}