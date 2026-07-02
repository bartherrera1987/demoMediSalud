package com.medisalud.infrastructure.persistence.mapper;

import com.medisalud.domain.model.Appointment;
import com.medisalud.domain.model.TimeSlot;
import com.medisalud.infrastructure.persistence.entity.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface AppointmentPersistenceMapper {
    @Mapping(target = "slot", expression = "java(mapSlot(entity.getStartAt(), entity.getEndAt()))")
    @Mapping(target = "cancel", ignore = true)
    Appointment toDomain(AppointmentEntity entity);

    @Mapping(target = "startAt", source = "slot.start")
    @Mapping(target = "endAt", source = "slot.end")
    AppointmentEntity toEntity(Appointment appointment);

    default TimeSlot mapSlot(LocalDateTime start, LocalDateTime end) {
        return (start != null && end != null) ? new TimeSlot(start, end) : null;
    }
}