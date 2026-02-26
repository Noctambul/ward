package fr.wardcare.api.resources;

import fr.wardcare.api.entities.MedicEntity;
import fr.wardcare.api.entities.PatientEntity;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

@QuarkusTest
class PatientResourceTest {

    private static final String LOGIN_PATH = "/api/v1/auth/login";
    private static final String PATIENTS_PATH = "/api/v1/patients";
    private static final String TEST_EMAIL = "doctor@wardcare.fr";
    private static final String TEST_PASSWORD = "123456";

    @BeforeEach
    @Transactional
    void seedData() {
        PatientEntity.deleteAll();
        MedicEntity.deleteAll();

        MedicEntity medic = new MedicEntity();
        medic.email = TEST_EMAIL;
        medic.password = BcryptUtil.bcryptHash(TEST_PASSWORD);
        medic.firstName = "Richard";
        medic.lastName = "Webber";
        medic.persist();

        persistPatient("Martin", "Alice", "alice.martin@wardcare.fr", "+33610000001", "F");
        persistPatient("Bernard", "Thomas", null, "+33610000002", "M");
    }

    @Test
    void shouldRejectPatientsEndpointWithoutToken() {
        given()
                .when().get(PATIENTS_PATH)
                .then()
                .statusCode(401);
    }

    @Test
    void shouldReturnPatientsEndpointWithValidToken() {
        String token = loginAndGetToken();

        given()
                .auth().oauth2(token)
                .when().get(PATIENTS_PATH)
                .then()
                .statusCode(200)
                .body("lastName", hasItems("Martin", "Bernard"))
                .body("firstName", hasItems("Alice", "Thomas"));
    }

    private static String loginAndGetToken() {
        return given()
                .contentType("application/json")
                .body("{\"email\":\"" + TEST_EMAIL + "\",\"password\":\"" + TEST_PASSWORD + "\"}")
                .when().post(LOGIN_PATH)
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    private static void persistPatient(String lastName, String firstName, String email, String phone, String sex) {
        Instant now = Instant.now();
        PatientEntity patient = new PatientEntity();
        patient.lastName = lastName;
        patient.firstName = firstName;
        patient.birthDate = LocalDate.of(1990, 1, 1);
        patient.sex = sex;
        patient.email = email;
        patient.phone = phone;
        patient.createdAt = now;
        patient.updatedAt = now;
        patient.persist();
    }
}
