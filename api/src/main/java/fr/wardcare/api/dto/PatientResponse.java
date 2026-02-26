package fr.wardcare.api.dto;

import fr.wardcare.api.entities.PatientEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record PatientResponse(UUID id, String lastName, String firstName,
                              LocalDate birthDate, String sex,
                              String phone, String email, Instant createdAt,
                              Instant updatedAt) {

    public static PatientResponse from(PatientEntity p) {
        return new PatientResponse(
                p.id,
                p.lastName,
                p.firstName,
                p.birthDate,
                p.sex,
                p.phone,
                p.email,
                p.createdAt,
                p.updatedAt
        );
    }
}
