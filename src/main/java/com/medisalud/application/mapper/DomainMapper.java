package com.medisalud.application.mapper;

import com.medisalud.application.dto.AppointmentDto;
import com.medisalud.application.dto.DoctorDto;
import com.medisalud.application.dto.PatientDto;
import com.medisalud.application.dto.TimeSlotDto;
import com.medisalud.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DomainMapper {

    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    Doctor toDoctor(DoctorDto dto);

    @Mapping(target = "email", source = "email.value")
    @Mapping(target = "phone", source = "phone.value")
    DoctorDto toDoctorDto(Doctor doctor);

    @Mapping(target = "document", source = "document")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    Patient toPatient(PatientDto dto);

    @Mapping(target = "document", source = "document.value")
    @Mapping(target = "email", source = "email.value")
    @Mapping(target = "phone", source = "phone.value")
    PatientDto toPatientDto(Patient patient);

    @Mapping(target = "doctorId", source = "doctorId")
    @Mapping(target = "patientId", source = "patientId")
    @Mapping(target = "slot", expression = "java(new TimeSlot(dto.start(), dto.end()))")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "cancelledAt", ignore = true)
    @Mapping(target = "cancel", ignore = true)
    Appointment toAppointment(AppointmentDto dto);

    @Mapping(target = "start", source = "slot.start")
    @Mapping(target = "end", source = "slot.end")
    AppointmentDto toAppointmentDto(Appointment appointment);

    TimeSlotDto toTimeSlotDto(TimeSlot slot);

    default Email mapEmail(String value) {
        return value != null ? new Email(value) : null;
    }

    default Phone mapPhone(String value) {
        return value != null ? new Phone(value) : null;
    }

    default Document mapDocument(String value) {
        return value != null ? new Document(value) : null;
    }
}
