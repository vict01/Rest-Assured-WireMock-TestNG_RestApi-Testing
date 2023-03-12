package org.example;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LogTest {

    String usersUrl = "https://gorest.co.in/public/v2";
    @Test
    public void logAllDetails(){
        given()
                .baseUri(usersUrl)
        .when()
                .get("/users")
        .then()
                .statusCode(200)
                .body(
                        "$.size()", greaterThan(0),
                        "$.size()", isA(Integer.class)
                )
                .log().all();
//                .log().everything(); // It's the same as .all()
//                .log().body();
//                .log().headers();
    }

    @Test
    public void logCookiesAndStatus(){
        given()
                .baseUri("https://hub.knime.com/")
        .when()
                .get("")
        .then()
                .log().status()
                .and().log().cookies();
    }

    @Test
    public void logErrors(){
        int userToBeFound = 193127;
        int wrongUser = 123;

        given()
                .baseUri(usersUrl)
        .when()
                .get("/users/"+userToBeFound)
        .then()
//                .log().ifError();
                .log().ifStatusCodeIsEqualTo(404)
                // Any assertion must be after the log-if, otherwise if the assertion fails will stop the rest of the test.
                .statusCode(404);
    }

    @Test
    public void logIfValidationFails(){
        int userToBeFound = 193127;
        int wrongUser = 123;

        given()
                .baseUri(usersUrl)
        .when()
                .get("/users/"+wrongUser)
        .then()
                // This "ValidationFails" must always be before the assertion, otherwise if it fails the log won't be shown.
                .log().ifValidationFails()
                .statusCode(404);
    }


}
