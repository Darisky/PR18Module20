package testAPI;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.given;

public class mainTest {
    public static final String gorestToken =
            "2f9e89e71919f4d431604092fcca4117a2ece960acac99f437d62598c431b8f7";
    public static final String existingUser =
            "7936303";

    @Test(priority = 1)
    public void testDelete() {
        given().log().all()
                .header("Authorization", "Bearer " + gorestToken)
                .when()
                .delete("https://gorest.co.in/public/v2/users/" + existingUser)
                .then().log().all()
                .statusCode(204);
        System.out.println("<--------------- User Deleted ------------->");

    }

    @Test(priority = 2)
    public void postNew() {

        String userName = "The Name is Den";
        String newEmail = "Den@example.co.in";
        String theGender = "male";
        String theStatus = "inactive";

        JSONObject postRequestbody = new JSONObject();
        postRequestbody.put("name", userName);
        postRequestbody.put("email", newEmail);
        postRequestbody.put("gender", theGender);
        postRequestbody.put("status", theStatus);

        given().log().all()
                .header("Authorization", "Bearer " + gorestToken)
                .header("content-type", "application/json")
                .header("accept", "application/json")
                .body(postRequestbody.toString())
                .when()
                .post("https://gorest.co.in/public/v2/users")
                .then().log().all()
                .statusCode(201);
        System.out.println("<--------------------POST SUCCEED------------------->");
    }

    @Test(priority = 3)
    public void negativePost(){
//        Negative Case : Jika tidak mengisi email, maka Gorest tidak akan menerima upload
//        Expected Status Code 422

       String theName = "Jason Timberlake";
       String theMail = "";
       String theGender = "male";
       String theStatus = "inactive";

       JSONObject theJasonObject = new JSONObject();
       theJasonObject.put("name", theName);
       theJasonObject.put("email", theMail);
       theJasonObject.put("gender", theGender);
       theJasonObject.put("status", theStatus);

       given().log().all()
               .header("Authorization", "Bearer " + gorestToken)
               .header("content-type", "application/json")
               .header("accept", "application/json")
               .body(theJasonObject.toString())
               .when()
               .post("https://gorest.co.in/public/v2/users")
               .then().log().all()
               .statusCode(422);
        System.out.println("<----------------------- New User Not Posted -----------------------> ");
    }

    @Test(priority = 4)
    public void apiGetlist() {

        File getResult = new File("src/main/resources/checkGet.Json");

        given()
                .when().get("https://gorest.co.in/public/v2/users")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", Matchers.hasSize(10))
                .body(JsonSchemaValidator.matchesJsonSchema(getResult));
    }
}