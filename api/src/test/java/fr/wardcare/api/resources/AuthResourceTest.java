package fr.wardcare.api.resources;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

@QuarkusTest
class AuthResourceTest {

    private static final String LOGIN_PATH = "/api/v1/auth/login";
    private static final String PATIENTS_PATH = "/api/v1/patients";
    private static final String TEST_EMAIL = "doctor@wardcare.fr";
    private static final String TEST_PASSWORD = "123456";

    @Inject
    DataSource dataSource;

    @BeforeEach
    void seedData() {
        try (var connection = dataSource.getConnection()) {
            try (var cleanupPatients = connection.prepareStatement("DELETE FROM patients");
                 var cleanupMedics = connection.prepareStatement("DELETE FROM medics")) {
                cleanupPatients.executeUpdate();
                cleanupMedics.executeUpdate();
            }

            try (var insertMedic = connection.prepareStatement(
                    "INSERT INTO medics (id, email, password, first_name, last_name) VALUES (?, ?, ?, ?, ?)")) {
                insertMedic.setObject(1, UUID.randomUUID());
                insertMedic.setString(2, TEST_EMAIL);
                insertMedic.setString(3, BcryptUtil.bcryptHash(TEST_PASSWORD));
                insertMedic.setString(4, "Richard");
                insertMedic.setString(5, "Webber");
                insertMedic.executeUpdate();
            }

            Instant now = Instant.now();
            try (var insertPatient = connection.prepareStatement(
                    "INSERT INTO patients (id, first_name, last_name, birth_date, sex, phone, email, created_at, updated_at) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                insertPatient.setObject(1, UUID.randomUUID());
                insertPatient.setString(2, "Alice");
                insertPatient.setString(3, "Martin");
                insertPatient.setDate(4, Date.valueOf("1989-04-12"));
                insertPatient.setString(5, "F");
                insertPatient.setString(6, "+33610000001");
                insertPatient.setString(7, "alice.martin@wardcare.fr");
                insertPatient.setTimestamp(8, Timestamp.from(now));
                insertPatient.setTimestamp(9, Timestamp.from(now));
                insertPatient.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to seed test data", e);
        }
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
