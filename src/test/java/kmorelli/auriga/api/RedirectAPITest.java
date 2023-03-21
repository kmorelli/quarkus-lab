package kmorelli.auriga.api;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@QuarkusTest
public class RedirectAPITest {

    @BeforeAll
    public static void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void testRedirect() throws InterruptedException {

        Response response = given()
            .when().redirects().follow(false)
            .get("/rapi/testeredirect");

        assertTrue(response.getStatusCode() == 302);

        given()
            .when().get(response.getHeader("Location"))
            .then().statusCode(200);


    }

    @Test
    public void testRedirectSemPular() {

        given()
            .when()
            .get("/rapi/testeredirect")
            .then().statusCode(200);
    }

}