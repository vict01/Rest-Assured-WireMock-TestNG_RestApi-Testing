package WireMock;

import io.restassured.RestAssured;
import io.restassured.config.XmlConfig;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class WireMock_CommonValuesAndMisc {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.port = 8080;
    }

    // As we can see in the tests below, it was not necessary to indicate Base URI or port.

    @Test
    public void validateTimeResponse() {
        when()
                .get("/whatsup")
        .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(1078L), TimeUnit.MILLISECONDS);
    }

    @Test
    public void postRequestUsingJSONObject() {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("type", "electronic");
        jsonBody.put("description", "blender");
        jsonBody.put("year", "2021");
        jsonBody.put("price", "320");

        given()
                .body(jsonBody)
        .when()
                .post("/createProduct")
        .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void validateXmlNameSpace() {
        String prefixNameSpace = "perctg";
        String urlNameSpace = "https://wiremock.org/docs/stubbing";
        XmlConfig xmlConfigVar = XmlConfig.xmlConfig().declareNamespace(prefixNameSpace, urlNameSpace);

        given()
                .config(RestAssured.config().xmlConfig(xmlConfigVar))
        .when()
                .get("/student/963/score")
        .then()
                .log().all()
                .statusCode(200)
                .body("student.score[0]", equalTo("369"))
                .body("student.grouping[1]", equalTo("99.66"));
    }

    @Test
    public void  validate_response_using_response_fields(){
        Response res = when()
                      .get("/get-article/testng")
                .then()
                    .log().all()
                    .extract().response();

        String hrefValue = res.path("href");
        String articleIdValue = res.path("articleId");
        String articleUrlValue = res.path("articleUrl");

        Assert.assertEquals(hrefValue + articleIdValue, articleUrlValue);
    }

    // The test below does exactly the same as the above but in a different way:
    @Test
    public void  validate_response_using_aware_matcher(){
        when()
                .get("/get-article/testng")
        .then()
                .log().all()
                .body(
                        "articleUrl", response -> equalTo(response.path("href").toString() + response.path("articleId").toString())
                );
    }

}
