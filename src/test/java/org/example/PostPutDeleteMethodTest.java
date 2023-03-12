package org.example;

import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

public class PostPutDeleteMethodTest {

    // Beware the Rest-Api https://dummy.restapiexample is sensible to too many request, so we could get 429 error by running multiple tests at once.
    @Test
    public void post_request() {
        File file = new File("resources/create_employee.json");

        String id = given()
                .baseUri("https://reqres.in/api")
                .contentType(ContentType.JSON)
                .body(file)
        .when()
                .post("/users")
        .then()
                .statusCode(201)
                .body(
                        "name", equalTo("Mark"),
                        "job", isA(String.class),
                        "id", not(isA(Integer.class))
                )
                .extract().path("id");

        System.out.println(id);
        Assert.assertTrue(Integer.parseInt(id)> 0);
    }

    @Test
    public void put_request_using_JsonObject() {
        int idToBeUpdated = 2;
        String newName = "Jason";

        JSONObject putBody = new JSONObject();
        putBody.put("name", newName);
        putBody.put("job", "Engineer");

        given()
                .baseUri("https://reqres.in/api")
                .contentType(ContentType.JSON)
                .body(putBody.toString())
        .when()
                .put("/users/"+idToBeUpdated)
        .then()
                .statusCode(200)
                .body(
                        "job", isA(String.class),
                        "name", equalTo(newName)
                );
    }

    @Test
    public void deleteEmployee() {
        int idToBeFound = 1;

        String idEmp = given()
                .baseUri("https://dummy.restapiexample.com/api/v1")
                .contentType(ContentType.JSON)
        .when()
                .delete("/delete/"+idToBeFound)
        .then()
                .statusCode(200)
                .body(
                        "status",equalTo("success"),
                        "data", isA(String.class),
                        "message", equalTo("Successfully! Record has been deleted")
                )
                .extract().path("message");

        System.out.println("\nThe message is:\n"+idEmp);
    }


}
