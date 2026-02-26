package fr.wardcare.api.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtTokenServiceTest {

    private static final String KEY_LOCATION_PROP = "smallrye.jwt.sign.key.location";
    private static final String ISSUER_PROP = "smallrye.jwt.new-token.issuer";
    private static final String ORIGINAL_KEY_LOCATION = System.getProperty(KEY_LOCATION_PROP);
    private static final String ORIGINAL_ISSUER = System.getProperty(ISSUER_PROP);

    @BeforeEach
    void setUp() {
        System.setProperty(KEY_LOCATION_PROP, "privateKey.pem");
        System.setProperty(ISSUER_PROP, "https://wardcare.local");
    }

    @AfterEach
    void tearDown() {
        restore(KEY_LOCATION_PROP, ORIGINAL_KEY_LOCATION);
        restore(ISSUER_PROP, ORIGINAL_ISSUER);
    }

    @Test
    void shouldCreateJwtWithExpectedClaims() {
        JwtTokenService service = new JwtTokenService();
        UUID medicId = UUID.randomUUID();
        String email = "doctor@wardcare.fr";

        String token = service.createAccessToken(medicId, email);

        String[] parts = token.split("\\.");
        assertEquals(3, parts.length);

        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
        assertTrue(payloadJson.contains("\"sub\":\"" + medicId + "\""));
        assertTrue(payloadJson.contains("\"upn\":\"" + email + "\""));
        assertTrue(payloadJson.contains("\"iss\":\"https://wardcare.local\""));
    }

    private static void restore(String key, String value) {
        if (value == null) {
            System.clearProperty(key);
            return;
        }
        System.setProperty(key, value);
    }
}
