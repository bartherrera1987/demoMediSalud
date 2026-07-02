package com.medisalud.infrastructure.persistence.mapper;

import com.medisalud.domain.model.Document;
import com.medisalud.domain.model.Email;
import com.medisalud.domain.model.Patient;
import com.medisalud.domain.model.Phone;
import com.medisalud.infrastructure.persistence.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientPersistenceMapper {
    @Mapping(target = "document", source = "document")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    Patient toDomain(PatientEntity entity);

    @Mapping(target = "document", source = "document.value")
    @Mapping(target = "email", source = "email.value")
    @Mapping(target = "phone", source = "phone.value")
    PatientEntity toEntity(Patient patient);

    default Document mapDocument(String value) {
        return value != null ? new Document(value) : null;
    }

    default Email mapEmail(String value) {
        return value != null ? new Email(value) : null;
    }

    default Phone mapPhone(String value) {
        return value != null ? new Phone(value) : null;
    }
}