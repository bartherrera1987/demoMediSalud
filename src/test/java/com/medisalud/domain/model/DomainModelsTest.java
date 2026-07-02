package com.medisalud.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DomainModelsTest {

    @Test
    @DisplayName("Email: Debería aceptar correos válidos")
    void validEmail() {
        assertDoesNotThrow(() -> new Email("test@example.com"));
        assertEquals("test@example.com", new Email("test@example.com").value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid-email", "test@", "@example.com", ""})
    @DisplayName("Email: Debería fallar con correos inválidos")
    void invalidEmail(String email) {
        assertThrows(IllegalArgumentException.class, () -> new Email(email));
    }

    @Test
    @DisplayName("Phone: Debería aceptar teléfonos válidos")
    void validPhone() {
        assertDoesNotThrow(() -> new Phone("3001234567"));
        assertDoesNotThrow(() -> new Phone("+57 300 123-4567"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "phone123", "", "   "})
    @DisplayName("Phone: Debería fallar con teléfonos inválidos")
    void invalidPhone(String phone) {
        assertThrows(IllegalArgumentException.class, () -> new Phone(phone));
    }

    @Test
    @DisplayName("Document: Debería aceptar documentos válidos")
    void validDocument() {
        assertDoesNotThrow(() -> new Document("12345678"));
        assertEquals("CC12345", new Document("cc12345").value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "doc@123", "", "this-is-too-long-for-a-document-number"})
    @DisplayName("Document: Debería fallar con documentos inválidos")
    void invalidDocument(String doc) {
        assertThrows(IllegalArgumentException.class, () -> new Document(doc));
    }

    @Test
    @DisplayName("AppointmentDate: Debería validar fecha nula")
    void appointmentDateNull() {
        assertThrows(IllegalArgumentException.class, () -> new AppointmentDate(null));
    }

    @Test
    @DisplayName("AppointmentDate: Debería aceptar fecha válida")
    void appointmentDateValid() {
        java.time.LocalDate date = java.time.LocalDate.now();
        AppointmentDate appDate = new AppointmentDate(date);
        assertEquals(date, appDate.value());
    }
}
