package testAPI;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.io.File;

public class mainTest {

    public void keyReqress(String keyAPi, String xkeyApi){
    keyAPi = "reqres-free-v1";
    xkeyApi = "x-api-key: reqres-free-v1";
    }
    @Test
    public void apiGetlist(){

        File getResult = new File("src/main/resources/checkGet.Json");

        RestAssured.given()
                .when().get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .body("page", Matchers.equalTo(2))
                .body("data.id", Matchers.hasSize(6))
                .body(JsonSchemaValidator.matchesJsonSchema(getResult));
    }
}
