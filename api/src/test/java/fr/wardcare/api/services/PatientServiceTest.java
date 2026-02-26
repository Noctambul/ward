package fr.wardcare.api.services;

import fr.wardcare.api.dto.PatientCreateRequest;
import fr.wardcare.api.dto.PatientResponse;
import fr.wardcare.api.entities.PatientEntity;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class PatientServiceTest {

    @Inject
    PatientService patientService;

    @BeforeEach
    @Transactional
    void cleanData() {
        PatientEntity.deleteAll();
    }

    @Test
    void createPatientShouldPersistAndTrimFields() {
        PatientCreateRequest request = new PatientCreateRequest(
                "  Dupont  ",
                "  Jeanne  ",
                LocalDate.of(1992, 7, 18),
                "F",
                "  +33612345678  ",
                "  jeanne.dupont@wardcare.fr  "
        );

        PatientResponse response = patientService.createPatient(request);

        assertNotNull(response.id());
        assertEquals("Dupont", response.lastName());
        assertEquals("Jeanne", response.firstName());
        assertEquals("+33612345678", response.phone());
        assertEquals("jeanne.dupont@wardcare.fr", response.email());
        assertNotNull(response.createdAt());
        assertNotNull(response.updatedAt());

        PatientEntity persisted = PatientEntity.findById(response.id());
        assertNotNull(persisted);
        assertEquals("Dupont", persisted.lastName);
        assertEquals("Jeanne", persisted.firstName);
    }

    @Test
    void createPatientShouldAllowPhoneWithoutEmail() {
        PatientCreateRequest request = new PatientCreateRequest(
                "Martin",
                "Alice",
                LocalDate.of(1989, 4, 12),
                "F",
                " +33610000001 ",
                null
        );

        PatientResponse response = patientService.createPatient(request);

        assertEquals("+33610000001", response.phone());
        assertNull(response.email());
    }

    @Test
    @Transactional
    void listPatientsShouldReturnMappedPatients() {
        persistPatient("Martin", "Alice", "alice.martin@wardcare.fr", "+33610000001");
        persistPatient("Bernard", "Thomas", null, "+33610000002");

        List<PatientResponse> patients = patientService.listPatients();

        assertEquals(2, patients.size());
        assertTrue(patients.stream().anyMatch(p -> p.lastName().equals("Martin") && p.firstName().equals("Alice")));
        assertTrue(patients.stream().anyMatch(p -> p.lastName().equals("Bernard") && p.firstName().equals("Thomas")));
    }

    private static void persistPatient(String lastName, String firstName, String email, String phone) {
        Instant now = Instant.now();
        PatientEntity patient = new PatientEntity();
        patient.lastName = lastName;
        patient.firstName = firstName;
        patient.birthDate = LocalDate.of(1990, 1, 1);
        patient.sex = "F";
        patient.email = email;
        patient.phone = phone;
        patient.createdAt = now;
        patient.updatedAt = now;
        patient.persist();
    }
}
