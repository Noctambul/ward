package fr.wardcare.api.services;

import fr.wardcare.api.dto.PatientCreateRequest;
import fr.wardcare.api.dto.PatientResponse;
import fr.wardcare.api.entities.PatientEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class PatientService {

    @Transactional
    public PatientResponse createPatient(PatientCreateRequest req) {
        PatientEntity p = new PatientEntity();
        p.lastName = req.lastName().trim();
        p.firstName = req.firstName().trim();
        p.birthDate = req.birthDate();
        p.phone = normalize(req.phone());
        p.email = normalize(req.email());
        p.sex = req.sex();
        Instant now = Instant.now();
        p.createdAt = now;
        p.updatedAt = now;

        p.persist();
        return PatientResponse.from(p);
    }

    public List<PatientResponse> listPatients() {
        return PatientEntity.<PatientEntity>findAll().list().stream().map(PatientResponse::from).toList();
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
