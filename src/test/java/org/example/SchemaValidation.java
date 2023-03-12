package org.example;

import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class SchemaValidation {

    // To use https://api.openweathermap.org you need to go there and create an account so that to get the AppId
    String openWeatherApiAppId = "";

    @Test(groups = {"schemeValidation"} )
    public void verifyJsonSchema() {
        File file = new File("resources/json_schema_emp_by_id.json.json");

        given()
                .baseUri("https://dummy.restapiexample.com/api/v1")
        .when()
                .get("/employee/1")
        .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchema(file));
    }

    @Test(groups = {"schemeValidation", "flaky"} )
    public void xml_dtd_schema_validation() {
        /*
        * It was not necessary to install any lib in the pom. Since matchesDtd ins within the import RestAssuredMatchers.*
        * We created the xml file in resources so that by using "Tools" option in the IDE get the dtd file.
        */
        File file = new File("resources/xml_dtd_schema.dtd");

        given()
                .baseUri("https://api.openweathermap.org/data/2.5")
                .queryParam("APPID",openWeatherApiAppId)
                .queryParam("q", "London,uk")
                .queryParam("mode","xml")
        .when()
                .get("/weather")
        .then()
                .log().body()
                .statusCode(200)
                .body(matchesDtd(file));
    }

    @Test(groups = {"schemeValidation", "flaky"} )
    public void xml_xsd_schema_validation() {
        // Here we did the same with IntelliJ IDE as the test above, but for xsd_schema
        File file = new File("resources/xml_xsd_schema.xsd");

        given()
                .baseUri("https://api.openweathermap.org/data/2.5")
                .queryParam("APPID", openWeatherApiAppId)
                .queryParam("q", "London,uk")
                .queryParam("mode", "xml").
        when()
                .get("/weather").
        then()
                .log().all()
                .body(matchesXsd(file))
                .statusCode(200);
    }

}
