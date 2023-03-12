package org.example;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class WorkingWithList {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void getUserById_byGettingDataFromList() {
        List<Integer> resp =  when()
                .get("/users")
        .then()
                .log().status()
                .statusCode(200)
                .extract().path("data.id");

        System.out.println("The list of users id is: "+resp);

        for(int element : resp){
            System.out.println("\nQuerying user id: "+element);

            when()
                    .get("users/"+element)
            .then()
                    .log().body()
                    .statusCode(200)
                    .body("data.id", equalTo(resp.get(element-1)));
        }
    }

}
