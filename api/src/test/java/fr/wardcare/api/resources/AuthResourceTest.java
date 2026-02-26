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
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

@QuarkusTest
class AuthResourceTest {

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

        Instant now = Instant.now();
        PatientEntity patient = new PatientEntity();
        patient.firstName = "Alice";
        patient.lastName = "Martin";
        patient.birthDate = LocalDate.of(1989, 4, 12);
        patient.sex = "F";
        patient.phone = "+33610000001";
        patient.email = "alice.martin@wardcare.fr";
        patient.createdAt = now;
        patient.updatedAt = now;
        patient.persist();
    }

    @Test
    void shouldLoginWithValidCredentials() {
        given()
                .contentType("application/json")
                .body("{\"email\":\"" + TEST_EMAIL + "\",\"password\":\"" + TEST_PASSWORD + "\"}")
                .when().post(LOGIN_PATH)
                .then()
                .statusCode(200)
                .body("token", not(isEmptyOrNullString()));
    }

    @Test
    void shouldRejectLoginWithInvalidPassword() {
        given()
                .contentType("application/json")
                .body("{\"email\":\"" + TEST_EMAIL + "\",\"password\":\"wrong\"}")
                .when().post(LOGIN_PATH)
                .then()
                .statusCode(401);
    }

    @Test
    void shouldRejectLoginWithUnknownEmail() {
        given()
                .contentType("application/json")
                .body("{\"email\":\"unknown@wardcare.fr\",\"password\":\"123456\"}")
                .when().post(LOGIN_PATH)
                .then()
                .statusCode(401);
    }

    @Test
    void shouldRejectProtectedEndpointWithoutToken() {
        given()
                .when().get(PATIENTS_PATH)
                .then()
                .statusCode(401);
    }

    @Test
    void shouldAllowProtectedEndpointWithValidToken() {
        String token = given()
                .contentType("application/json")
                .body("{\"email\":\"" + TEST_EMAIL + "\",\"password\":\"" + TEST_PASSWORD + "\"}")
                .when().post(LOGIN_PATH)
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        given()
                .auth().oauth2(token)
                .when().get(PATIENTS_PATH)
                .then()
                .statusCode(200);
    }
}
