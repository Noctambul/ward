package fr.wardcare.api.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatientCreateRequest(
        @NotBlank(message = "lastName is required") String lastName,
        @NotBlank(message = "firstName is required") String firstName,
        @NotNull(message = "birthDate is required") LocalDate birthDate,
        @Pattern(regexp = "M|F", message = "sex must be one of: M, F") String sex,
        String phone,
        @Email(message = "email must be a valid email") String email
) {

    private static boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    @AssertTrue(message = "phone or email is required")
    public boolean isContactProvided() {
        return isNotBlank(phone) || isNotBlank(email);
    }
}
