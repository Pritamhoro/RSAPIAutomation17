import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import logs.RequestPayload;
import org.testng.annotations.Test;

import java.net.StandardSocketOptions;
import java.util.*;


import static io.restassured.RestAssured.*;

public class AuthService
{
    @Test
    public static void Platzi_Fake_Store_API()
    {
        RequestPayload requestPayload=new RequestPayload();
        requestPayload.setEmail("john@mail.com");
        requestPayload.setPassword("changeme");
        baseURI="https://api.escuelajs.co";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post("/api/v1/auth/login");
        response.then()
                .log()
                .body();
        System.out.println("===================><=================");
        JsonPath jsonpath = response.then()
                .extract()
                .jsonPath();
        Object token = jsonpath.get("access_token");
        given()
                .header("Authorization","Bearer "+token)
                .when()
                .get("/api/v1/auth/profile")
                .then()
                .log()
                .body();

    }
}
