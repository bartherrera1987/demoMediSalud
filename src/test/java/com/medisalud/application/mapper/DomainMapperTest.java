package com.medisalud.application.mapper;

import com.medisalud.application.dto.AppointmentDto;
import com.medisalud.application.dto.DoctorDto;
import com.medisalud.application.dto.PatientDto;
import com.medisalud.application.dto.TimeSlotDto;
import com.medisalud.domain.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DomainMapperTest {

    @Autowired
    private DomainMapper mapper;

    @Test
    void shouldMapDoctorToDto() {
        Doctor doctor = new Doctor(UUID.randomUUID(), "Dr. House", "Diagnostics", new Email("house@test.com"), new Phone("3001234567"));
        DoctorDto dto = mapper.toDoctorDto(doctor);
        
        assertEquals(doctor.id(), dto.id());
        assertEquals(doctor.name(), dto.name());
        assertEquals(doctor.specialty(), dto.specialty());
        assertEquals(doctor.email().value(), dto.email());
        assertEquals(doctor.phone().value(), dto.phone());
    }

    @Test
    void shouldMapDtoToDoctor() {
        DoctorDto dto = new DoctorDto(UUID.randomUUID(), "Dr. House", "Diagnostics", "house@test.com", "3001234567");
        Doctor doctor = mapper.toDoctor(dto);

        assertEquals(dto.id(), doctor.id());
        assertEquals(dto.name(), doctor.name());
        assertEquals(dto.specialty(), doctor.specialty());
        assertEquals(dto.email(), doctor.email().value());
        assertEquals(dto.phone(), doctor.phone().value());
    }

    @Test
    void shouldMapPatientToDto() {
        Patient patient = new Patient(UUID.randomUUID(), "John Doe", new Document("12345678"), new Email("john@test.com"), new Phone("3001234567"), LocalDate.of(1990, 1, 1));
        PatientDto dto = mapper.toPatientDto(patient);

        assertEquals(patient.id(), dto.id());
        assertEquals(patient.name(), dto.name());
        assertEquals(patient.document().value(), dto.document());
        assertEquals(patient.email().value(), dto.email());
        assertEquals(patient.phone().value(), dto.phone());
        assertEquals(patient.birthDate(), dto.birthDate());
    }

    @Test
    void shouldMapDtoToPatient() {
        PatientDto dto = new PatientDto(UUID.randomUUID(), "John Doe", "12345678", "john@test.com", "3001234567", LocalDate.of(1990, 1, 1));
        Patient patient = mapper.toPatient(dto);

        assertEquals(dto.id(), patient.id());
        assertEquals(dto.name(), patient.name());
        assertEquals(dto.document(), patient.document().value());
        assertEquals(dto.email(), patient.email().value());
        assertEquals(dto.phone(), patient.phone().value());
        assertEquals(dto.birthDate(), patient.birthDate());
    }

    @Test
    void shouldMapAppointmentToDto() {
        UUID id = UUID.randomUUID();
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        TimeSlot slot = new TimeSlot(LocalDateTime.now().withMinute(0).withSecond(0).withNano(0), LocalDateTime.now().withMinute(0).withSecond(0).withNano(0).plusMinutes(30));
        Appointment appointment = new Appointment(id, doctorId, patientId, slot, AppointmentStatus.BOOKED, LocalDateTime.now(), null);
        
        AppointmentDto dto = mapper.toAppointmentDto(appointment);

        assertEquals(appointment.id(), dto.id());
        assertEquals(appointment.doctorId(), dto.doctorId());
        assertEquals(appointment.patientId(), dto.patientId());
        assertEquals(appointment.slot().start(), dto.start());
        assertEquals(appointment.slot().end(), dto.end());
        assertEquals(appointment.status(), dto.status());
    }

    @Test
    void shouldMapDtoToAppointment() {
        UUID id = UUID.randomUUID();
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        LocalDateTime start = LocalDateTime.now().plusDays(1).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = start.plusMinutes(30);
        AppointmentDto dto = new AppointmentDto(id, doctorId, patientId, start, end, AppointmentStatus.BOOKED);

        Appointment appointment = mapper.toAppointment(dto);

        assertEquals(dto.id(), appointment.id());
        assertEquals(dto.doctorId(), appointment.doctorId());
        assertEquals(dto.patientId(), appointment.patientId());
        assertEquals(dto.start(), appointment.slot().start());
        assertEquals(dto.end(), appointment.slot().end());
        assertEquals(dto.status(), appointment.status());
    }

    @Test
    void shouldMapTimeSlotToDto() {
        TimeSlot slot = new TimeSlot(LocalDateTime.now().withMinute(30).withSecond(0).withNano(0), LocalDateTime.now().withMinute(30).withSecond(0).withNano(0).plusMinutes(30));
        TimeSlotDto dto = mapper.toTimeSlotDto(slot);

        assertEquals(slot.start(), dto.start());
        assertEquals(slot.end(), dto.end());
    }

    @Test
    void shouldMapNulls() {
        assertNull(mapper.mapEmail(null));
        assertNull(mapper.mapPhone(null));
        assertNull(mapper.mapDocument(null));
    }
}
