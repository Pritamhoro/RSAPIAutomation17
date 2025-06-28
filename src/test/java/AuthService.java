import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import logs.RequestPayload;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class AuthService {

    private static final String BASE_URI = "https://api.escuelajs.co";
    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    private static final String PROFILE_ENDPOINT = "/api/v1/auth/profile";

    @BeforeClass
    public void setup() {
        baseURI = BASE_URI;
    }

    /**
     * Test login and fetch user profile from Platzi Fake Store API
     */
    @Test
    public void testLoginAndGetProfile() {
        RequestPayload requestPayload = new RequestPayload();
        requestPayload.setEmail("john@mail.com");
        requestPayload.setPassword("changeme");

        // Login and get access token
        Response loginResponse = given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post(LOGIN_ENDPOINT);

        // Validate login success
        loginResponse.then()
                .statusCode(201)  // or 200 depending on API spec
                .log().body();

        String accessToken = extractToken(loginResponse);
        Assert.assertNotNull(accessToken, "Access token should not be null");

        // Use the access token to get user profile
        Response profileResponse = given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get(PROFILE_ENDPOINT);

        profileResponse.then()
                .statusCode(200)
                .log().body();
    }

    /**
     * Extracts access token from login response
     *
     * @param response login Response object
     * @return access token string
     */
    private String extractToken(Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString("access_token");
    }
}
