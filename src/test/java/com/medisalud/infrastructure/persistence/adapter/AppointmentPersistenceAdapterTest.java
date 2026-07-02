package com.medisalud.infrastructure.persistence.adapter;

import com.medisalud.domain.model.*;
import com.medisalud.infrastructure.persistence.entity.AppointmentEntity;
import com.medisalud.infrastructure.persistence.mapper.AppointmentPersistenceMapper;
import com.medisalud.infrastructure.persistence.repository.AppointmentJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentPersistenceAdapterTest {

    @Mock
    private AppointmentJpaRepository repository;

    @Mock
    private AppointmentPersistenceMapper mapper;

    @InjectMocks
    private AppointmentPersistenceAdapter adapter;

    private final UUID doctorId = UUID.randomUUID();
    private final UUID patientId = UUID.randomUUID();
    private final LocalDateTime start = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);

    @Test
    @DisplayName("Debería guardar una cita")
    void save() {
        Appointment appointment = new Appointment(null, doctorId, patientId, TimeSlot.of(start), AppointmentStatus.BOOKED, LocalDateTime.now(), null);
        AppointmentEntity entity = new AppointmentEntity();
        when(mapper.toEntity(appointment)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(appointment);

        Appointment result = adapter.save(appointment);

        assertNotNull(result);
        verify(repository).save(any());
    }

    @Test
    @DisplayName("Debería buscar por ID")
    void findById() {
        UUID id = UUID.randomUUID();
        AppointmentEntity entity = new AppointmentEntity();
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(mock(Appointment.class));

        Optional<Appointment> result = adapter.findById(id);

        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Debería verificar existencia por doctor y inicio")
    void existsBookedByDoctorAndStart() {
        when(repository.existsByDoctorIdAndStartAtAndStatus(doctorId, start, AppointmentStatus.BOOKED)).thenReturn(true);
        assertTrue(adapter.existsBookedByDoctorAndStart(doctorId, start));
    }

    @Test
    @DisplayName("Debería verificar existencia por doctor, paciente y inicio")
    void existsBookedByDoctorPatientAndStart() {
        when(repository.existsByDoctorIdAndPatientIdAndStartAtAndStatus(doctorId, patientId, start, AppointmentStatus.BOOKED)).thenReturn(true);
        assertTrue(adapter.existsBookedByDoctorPatientAndStart(doctorId, patientId, start));
    }

    @Test
    @DisplayName("Debería buscar todas con filtros")
    void findAllWithFilters() {
        LocalDate date = LocalDate.now();
        when(repository.search(eq(doctorId), eq(patientId), any(), any())).thenReturn(List.of(new AppointmentEntity()));
        when(mapper.toDomain(any())).thenReturn(mock(Appointment.class));

        List<Appointment> result = adapter.findAll(doctorId, patientId, date);

        assertEquals(1, result.size());
    }
}
