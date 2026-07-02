package com.medisalud.infrastructure.web.controller;

import com.medisalud.application.dto.DoctorDto;
import com.medisalud.application.usecase.*;
import com.medisalud.domain.model.Doctor;
import com.medisalud.infrastructure.web.request.DoctorRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterDoctorUseCase registerDoctor;
    @MockBean
    private ListDoctorsUseCase listDoctors;
    @MockBean
    private FindDoctorUseCase findDoctor;
    @MockBean
    private DeleteDoctorUseCase deleteDoctor;

    @Test
    void shouldCreateDoctor() throws Exception {
        DoctorRequest request = new DoctorRequest("Dr. Smith", "Cardiology", "smith@email.com", "123456789");
        DoctorDto responseDto = new DoctorDto(UUID.randomUUID(), "Dr. Smith", "Cardiology", "smith@email.com", "123456789");

        when(registerDoctor.execute(any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Dr. Smith"));
    }

    @Test
    void shouldGetDoctorById() throws Exception {
        UUID id = UUID.randomUUID();
        DoctorDto doctor = new DoctorDto(id, "Dr. Smith", "Cardiology", "smith@email.com", "123456789");

        when(findDoctor.execute(id)).thenReturn(doctor);

        mockMvc.perform(get("/api/v1/doctors/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldReturn404WhenDoctorNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(findDoctor.execute(id)).thenThrow(new com.medisalud.domain.exception.NotFoundException("DOCTOR_NOT_FOUND", "Médico no encontrado"));

        mockMvc.perform(get("/api/v1/doctors/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("DOCTOR_NOT_FOUND"));
    }

    @Test
    void shouldDeleteDoctor() throws Exception {
        UUID id = UUID.randomUUID();
        
        mockMvc.perform(delete("/api/v1/doctors/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn400WhenDoctorRequestIsInvalid() throws Exception {
        DoctorRequest request = new DoctorRequest("", "", "invalid-email", "123");

        mockMvc.perform(post("/api/v1/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("name: must not be blank")))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("email: must be a well-formed email address")));
    }
}
