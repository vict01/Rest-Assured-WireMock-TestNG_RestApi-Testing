package org.example;

import io.restassured.response.Response;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isA;

public class GetMethodTest {

    String reqResUrl = "https://reqres.in/api";
    String countriesUrl = "https://restcountries.com/v2";

    // To use https://api.openweathermap.org you need to go there and create an account so that to get the AppId
    String openWeatherApiAppId = "";

    @Test
    public void testReqResGetMethod() {
        given()
                .baseUri(reqResUrl)
                .param("page", 2)
                .param("id", 7)
        .when()
                .get("/users")
        .then()
                .log().body()
                .statusCode(200)
                .assertThat().body("data.id", equalTo(7)) // This is not the best way to validate
                .and().body("data.first_name", is("Michael"));
    }

    @Test
    public void testCountriesGetMethod() {
        given()
                .baseUri(countriesUrl)
        .when()
                .get("/all")
        .then()
                .log().body()
                .statusCode(200)
                .body(
                        "$.size()", greaterThan(0),
                        "$.size()", isA(Integer.class)
                );
    }

    @Test
    public void testCountriesByCodeGetMethod() {
        given()
                .baseUri(countriesUrl)
        .when()
                .get("/alpha/USA")
        .then()
                .log().body()
                .statusCode(200)
                .body(
                        "name", equalTo("United States of America"),
                        "altSpellings", hasItem("US"),
                        "currencies[0].symbol", equalTo("$"),
                        "languages[0].name", equalTo("English")
                );
    }

    @Test
    @Ignore
    public void testXmlResponse(){
        given()
                .baseUri("https://api.openweathermap.org/data/2.5")
                .queryParam("q", "London,uk")
                .queryParam("APPID", openWeatherApiAppId)
                .queryParam("mode","xml")
        .when()
                .get("/weather")
        .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .body(
                  "current.city.@name",equalTo("London"),
                        "current.city.country", equalTo("GB")
                );
    }

    @Test
    public void verifyStatusLine(){
        given()
                .baseUri("https://api.openweathermap.org/data/2.5")
                .queryParam("q", "London,uk")
                .queryParam("APPID","wrong_key")
                .queryParam("mode","xml")
        .when()
                .get("/weather")
        .then()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
    }

    @Test
    public void getFullResponseData() {
        Response resp = given()
                .baseUri(countriesUrl)
        .when()
                .get("/alpha/USA")
        .then()
                .statusCode(200)
                .body(
                        "nativeName", isA(String.class),
                        "name", equalTo("United States of America")
                )
                .extract().response();

        System.out.println(resp.asString());
    }

    @Test
    public void getFullResponseData_V2() {
        Response resp = given()
                .baseUri("https://postman-echo.com")
        .when()
                .get("/get")
        .then()
                .extract().response();

        System.out.println(resp);
    }

    @Test
    @Ignore
    public void saveSingleFieldFromResponse() {
        String temp = given()
                .baseUri("https://api.openweathermap.org/data/2.5")
                .queryParam("q", "London,uk")
                .queryParam("APPID", openWeatherApiAppId)
                .queryParam("mode","xml")
        .when()
                .get("/weather")
        .then()
                .statusCode(200)
                .body(
                        "current.city.@name",equalTo("London"),
                        "current.city.country", equalTo("GB")
                )
                .extract().path("current.temperature.@value");

        System.out.println(temp);
    }

}
