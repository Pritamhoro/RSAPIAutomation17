import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.*;


import static io.restassured.RestAssured.*;

public class AuthService
{
    @Test
    public static void Platzi_Fake_Store_API()
    {
        Map<String,String> payload= new HashMap<String,String>();
        payload.put("email","john@mail.com");
        payload.put("password","changeme");
        baseURI="https://api.escuelajs.co";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/v1/auth/login");

    }
}
