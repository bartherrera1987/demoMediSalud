package com.medisalud.infrastructure.web.controller;

import com.medisalud.application.dto.AppointmentDto;
import com.medisalud.application.usecase.*;
import com.medisalud.domain.model.AppointmentStatus;
import com.medisalud.infrastructure.web.request.BookAppointmentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.medisalud.domain.exception.*;

@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookAppointmentUseCase book;
    @MockBean
    private CancelAppointmentUseCase cancel;
    @MockBean
    private RescheduleAppointmentUseCase reschedule;
    @MockBean
    private FindAvailableSlotsUseCase availability;
    @MockBean
    private ListAppointmentsUseCase list;

    @Test
    void shouldBookAppointment() throws Exception {
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        LocalDateTime start = LocalDateTime.now().plusDays(1).withMinute(0).withSecond(0).withNano(0);
        BookAppointmentRequest request = new BookAppointmentRequest(doctorId, patientId, start);
        
        AppointmentDto response = new AppointmentDto(UUID.randomUUID(), doctorId, patientId, start, start.plusMinutes(30), AppointmentStatus.BOOKED);

        when(book.execute(any(), any(), any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("BOOKED"));
    }

    @Test
    void shouldCancelAppointment() throws Exception {
        UUID id = UUID.randomUUID();
        when(cancel.execute(id)).thenReturn(mock(AppointmentDto.class));

        mockMvc.perform(post("/api/v1/appointments/" + id + "/cancel"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRescheduleAppointment() throws Exception {
        UUID id = UUID.randomUUID();
        LocalDateTime newStart = LocalDateTime.now().plusDays(2).withMinute(30).withSecond(0).withNano(0);
        when(reschedule.execute(any(), any())).thenReturn(mock(AppointmentDto.class));

        mockMvc.perform(post("/api/v1/appointments/" + id + "/reschedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"start\":\"" + newStart + "\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenBookingRequestIsInvalid() throws Exception {
        BookAppointmentRequest request = new BookAppointmentRequest(null, null, LocalDateTime.now().minusDays(1));

        mockMvc.perform(post("/api/v1/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("doctorId: must not be null")))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("start: must be a future date")));
    }

    @Test
    void shouldReturn409WhenDoctorUnavailable() throws Exception {
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        BookAppointmentRequest request = new BookAppointmentRequest(doctorId, patientId, start);

        when(book.execute(any(), any(), any())).thenThrow(new DoctorUnavailableException("Médico ocupado"));

        mockMvc.perform(post("/api/v1/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("DOCTOR_UNAVAILABLE"))
                .andExpect(jsonPath("$.message").value("Médico ocupado"));
    }

    @Test
    void shouldReturn404WhenAppointmentNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(cancel.execute(id)).thenThrow(new AppointmentNotFoundException("Cita no encontrada"));

        mockMvc.perform(post("/api/v1/appointments/" + id + "/cancel"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("APPOINTMENT_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Cita no encontrada"));
    }
}
