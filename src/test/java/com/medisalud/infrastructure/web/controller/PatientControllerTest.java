package com.medisalud.infrastructure.web.controller;

import com.medisalud.application.dto.PatientDto;
import com.medisalud.application.usecase.*;
import com.medisalud.domain.model.Patient;
import com.medisalud.infrastructure.web.request.PatientRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterPatientUseCase registerPatient;
    @MockBean
    private ListPatientsUseCase listPatients;
    @MockBean
    private FindPatientUseCase findPatient;
    @MockBean
    private DeletePatientUseCase deletePatient;

    @Test
    void shouldCreatePatient() throws Exception {
        PatientRequest request = new PatientRequest("John Doe", "CC12345", "john@email.com", "3001234567", LocalDate.of(1990,1,1));
        PatientDto responseDto = new PatientDto(UUID.randomUUID(), "John Doe", "CC12345", "john@email.com", "3001234567", LocalDate.of(1990,1,1));

        when(registerPatient.execute(any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void shouldGetPatientById() throws Exception {
        UUID id = UUID.randomUUID();
        PatientDto patient = new PatientDto(id, "John Doe", "CC12345", "john@email.com", "3001234567", LocalDate.of(1990,1,1));

        when(findPatient.execute(id)).thenReturn(patient);

        mockMvc.perform(get("/api/v1/patients/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldDeletePatient() throws Exception {
        UUID id = UUID.randomUUID();
        
        mockMvc.perform(delete("/api/v1/patients/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn400WhenPatientRequestIsInvalid() throws Exception {
        PatientRequest request = new PatientRequest("", "123", "invalid-email", "123", null);

        mockMvc.perform(post("/api/v1/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("name: must not be blank")))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("email: must be a well-formed email address")))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("document: must match")));
    }
}
