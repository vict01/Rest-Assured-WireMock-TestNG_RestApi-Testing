package org.example;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthTest {

        /*
         * Authentication:
         *  1. Basic (encoded) VS Digest (encrypted)
         *  2. Preemptive (encryption before server asking) VS Challenged (encryption only if server asks)
         *
         *  Example
         *   URI: https://postman-echo.com
         *   End-point: /digest-auth
         */

        @Test(groups = {"basicAuth"} )
        public void digest_authentication() {
            given()
                    .baseUri("https://postman-echo.com")
                    .auth().digest("postman", "password")
//                   .auth().basic("postman", "password") // Just example. This API doesn't accept basic auth
//                   .auth().preemptive().basic("postman", "password")  //If we don't indicate preemptive, then by default it's Challenged
            .when()
                    .get("/digest-auth")
            .then()
                    .log().all()
                    .statusCode(200)
                    .body("authenticated", equalTo(true));
        }


    /*
     * In the next test you need to create you own Twitter account, to get your own keys and tokens
     * oAuth 1.0
     *
     * Consumer Key (API Key),
     * Consumer Secret (API Secret Key),
     * Access Token,
     * Access Token Secret
     *
     * https://api.twitter.com/1.1/statuses/update.json
     */

    @Test(groups = {"requiredAuth"} )
    public void post_A_Tweet() {
        String apiUri = "https://api.twitter.com/2";
        String endPoint = "/tweets";

        String apiUri2 = "https://api.twitter.com/1.1";
        String endPoint2 = "/statuses/update.json";

        String tweet = "This is a test tweet from Rest Assured.";
        String apiKey = "";
        String apiSecretKey = "";
        String apiToken = "";
        String apiSecretToken = "";

        given()
                .baseUri(apiUri2)
                .auth().oauth(apiKey, apiSecretKey, apiToken, apiSecretToken)
                .param("status", tweet).
        when()
                .post(endPoint2).
        then()
                .log().all();
//                .statusCode(200);
    }

    /*
     * In the next test you need to create you own Twitter account, to get your own keys and tokens
     * oAuth 2.0
     *
     * Consumer Key (API Key)
     * Consumer Secret (API Secret Key)
     * Access Token - On the fly
     *
     */

    @Test(groups = {"requiredAuth"} )
    public void GenerateInvoiceAfterCatchingToken() {
        String clientId = "";
        String apiSecret = "";

        String accessToken = given()
                .baseUri("https://api.sandbox.paypal.com/v1")
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .header("Accept-Language","en_US")
                .param("grant_type", "client_credentials")
                .auth().preemptive().basic(clientId, apiSecret).
        when()
                .post("/oauth2/token").
        then()
                 // At the beginning we use this only to see the path of the access_token and catch it later.
//                .log().all()
                .statusCode(200)
                .extract().path("access_token");

        System.out.println("\nToken:\n"+accessToken);

        //Generate next invoice:
        given()
                .baseUri("https://api.sandbox.paypal.com/v2")
                .contentType("application/json")
                .auth().oauth2(accessToken).
        when()
                .post("/invoicing/generate-next-invoice-number").
        then()
                .log().all()
                .statusCode(200)
                .body(
                        "invoice_number.size()", greaterThan(0),
                        "invoice_number", isA(String.class)
                );
    }

}
