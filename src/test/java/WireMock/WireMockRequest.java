package WireMock;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.Matchers.*;

public class WireMockRequest {

    String wireMockURL = "http://localhost:8080";

    @Test
    public void specifyingPortInRequest() {
        given()
                .baseUri(wireMockURL)
                .port(8080)
        .when()
                .get("/whatsup")
        .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void verifyFullResponseGetMethod() {
        Response resp = given()
                .baseUri(wireMockURL)
                .header("content-type", "text/plain")
        .when()
                .get("/whatsup")
        .then()
                .statusCode(200)
        .extract().response();

        String response = resp.asString();
        System.out.println(response);
        Assert.assertEquals(response, "Hey! this is a mocked response");
    }

    @Test
    public void verifyFieldResponsePostMethod() {
        File file = new File("resources/create_product.json");

        String resp = given()
                .baseUri(wireMockURL)
                .contentType(ContentType.JSON)
                .body(file)
        .when()
                .post("/createProduct")
        .then()
                .log().body()
                .statusCode(201)
                .statusLine("HTTP/1.1 201 Created")
                .body(
                        "$", isA(Object.class),
                        "msg", isA(String.class),
                        "productId", isA(Integer.class),
                        "productId",equalTo(28),
                        "msg", equalTo("Product created successfully")
                )
                .extract().path("msg");

        System.out.println(resp);
    }


}
