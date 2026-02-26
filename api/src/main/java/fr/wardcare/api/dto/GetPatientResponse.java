package fr.wardcare.api.dto;

import java.util.UUID;

public record GetPatientResponse(UUID id, String lastName, String firstName,
                                 String email, String phone) {
}
