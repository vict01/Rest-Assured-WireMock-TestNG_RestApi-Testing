package org.example;

import org.testng.TestNG;
import org.testng.annotations.*;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RequestParameters {


    // To use https://gateway.marvel.com you need to go there and create an account so that to get the api key and hash
    String marvelApiKey = "";
    String marvelApiHash = "";

    @BeforeSuite
    public void name() {
        TestNG myTestNG = new TestNG();
        myTestNG.setUseDefaultListeners(true);
        System.out.println("Default reports are enabled");
    }

    @Test
    @Ignore
    public void getRequestUsingMultipleParameters() {
        given()
                .baseUri("https://gateway.marvel.com/v1")
                // .param and .queryParam do pretty much the same. So, we can use them interchangeably.
                .param("ts", "1")
                .queryParam("apikey", marvelApiKey)
                .queryParam("hash", marvelApiHash)
                .queryParam("limit", "2")
        .when()
                .get("/public/characters")
        .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    @Ignore
    public void getRequestUsingHashMap() {
        HashMap<String, Object> parameters = new HashMap<>();
        // In case the parameter below (ts) admits several values, we can do it by writing "1,3,5,7" instead of only "1". Applying the separator according the doc.
        parameters.put("ts", "1");
        parameters.put("apikey", marvelApiKey);
        parameters.put("hash", marvelApiHash);
        parameters.put("limit", "2");

        given()
                .baseUri("https://gateway.marvel.com/v1")
                // We use .params instead of .param when it comes a set of parameters and not only one.
                .params(parameters)
        .when()
                .get("/public/characters")
        .then()
                .log().body()
                .statusCode(200)
                .body(
                        "data.limit", equalTo(2),
                        "data.results", isA(Object.class),
                        "data.results[0].id", isA(Integer.class)
                );
    }

    // Examples below are simulating endpoints which uses path parameters:
    //https://restcountries.eu/rest/v2/currency/{currency}

    @Test
    public void handling_path_parameter() {
        given()
                .baseUri("https://restcountries.com/v2")
                .pathParam("currency", "USD")
        .when()
                .get("/currency/{currency}")
        .then()
                .log().all()
                .statusCode(200);
    }

    //For Form Parameters: https://postman-echo.com/post
    //multipart/form-data
    //application/x-www-form-urlencoded
    //application/json

    @Test
    public void handling_form_data() {
        given()
                .baseUri("https://postman-echo.com")
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .formParam("First Name", "Shravya")
                .formParam("Last Name", "Deshmukh")
        .when()
                .post("/post")
        .then()
                .log().cookies()
                .statusCode(200);
    }

}
