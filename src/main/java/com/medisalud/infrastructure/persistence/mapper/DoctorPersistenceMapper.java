package com.medisalud.infrastructure.persistence.mapper;

import com.medisalud.domain.model.Doctor;
import com.medisalud.domain.model.Email;
import com.medisalud.domain.model.Phone;
import com.medisalud.infrastructure.persistence.entity.DoctorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorPersistenceMapper {
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    Doctor toDomain(DoctorEntity entity);

    @Mapping(target = "email", source = "email.value")
    @Mapping(target = "phone", source = "phone.value")
    DoctorEntity toEntity(Doctor doctor);

    default Email mapEmail(String value) {
        return value != null ? new Email(value) : null;
    }

    default Phone mapPhone(String value) {
        return value != null ? new Phone(value) : null;
    }
}