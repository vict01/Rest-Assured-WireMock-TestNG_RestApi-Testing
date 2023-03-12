package org.example;

import io.restassured.http.Cookie;
import io.restassured.http.Headers;

import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class HeadersAndCookies {

    @Test
    public void sending_request_with_header() {
        given()
                .baseUri("https://reqres.in/api")
                .param("page", 2)
                .header("User-Agent", "PostmanRuntime/7.30.0")
                .header("Connection", "keep-alive")
        .when()
                .get("/users")
        .then()
                .log().headers()
                .statusCode(200);
    }

    @Test
    public void request_with_header_using_object() {
        HashMap<String, Object> headerMap = new HashMap<>();
        headerMap.put("User-Agent", "PostmanRuntime/7.30.0");
        headerMap.put("Connection", "keep-alive");

        given()
                .baseUri("https://reqres.in/api")
                .param("page", 2)
                .headers(headerMap)
        .when()
                .get("/users")
        .then()
                .log().headers()
                .statusCode(200);
    }

    @Test
    public void request_with_cookies() {
        given()
                .baseUri("https://reqres.in/api")
                .param("page", 2)
                .cookie("cookieName", "Main-cookie", "secondary-cookie")
        .when()
                .get("/users")
        .then()
                .log().headers()
                .statusCode(200);
    }

    @Test
    public void request_using_cookiesBuilding() {
        Cookie cookieApi = new Cookie.Builder("cookieName", "Main-cookie")
                .setSecured(true)
                .setComment("test cookie")
                .build();

        given()
                .baseUri("https://reqres.in/api")
                .param("page", 2)
                .cookie(cookieApi)
        .when()
                .get("/users")
        .then()
                .log().headers()
                .statusCode(200);
    }

    @Test
    public void verifying_headers() {
        given()
                .baseUri("https://postman-echo.com")
        .when()
                .get("/get")
        .then()
                .log().headers()
                .statusCode(200)
                // We con do this validation using HashMap as well
                .header("Connection", "keep-alive");
    }

    @Test
    public void extract_response_header() {
        Headers headerApi = given()
                .baseUri("https://postman-echo.com")
        .when()
                .get("/get")
        .then()
                .statusCode(200)
                .extract().headers();

//        System.out.println(headerApi);
        System.out.println(headerApi.getValue("Date"));
        System.out.println(headerApi.getValue("set-cookie"));
    }

    @Test
    public void extract_response_cookie() {
        Map<String, String> cookieApi = given()
                .baseUri("https://postman-echo.com")
        .when()
                .get("/get")
        .then()
                .statusCode(200)
                .extract().cookies();

        System.out.println(cookieApi.get("sails.sid"));
    }

}
