import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://gorest.co.in/";
    }


    @Test
    public void User(){

        //Creating user
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer 19d48b78574c8a903980eb1f881337d424496c1bfb23c8404d559b97ffbeb678")
                .and()
                .body("{\"name\": \"Pera Peric\",\n" +
                        "    \"email\": \"perica@yahoo.net\",\n" +
                        "    \"gender\": \"male\",\n" +
                        "    \"status\": \"active\"}")
                .when()
                .post("public/v2/users");

        System.out.println(response.getBody().asString());
        assertEquals(201, response.statusCode());


        JSONObject updateUser = new JSONObject(response.getBody().asString());
        int newUserId = updateUser.getInt("id");
        //System.out.println(newUserId);
        String newUserEmail = updateUser.getString("email");
        System.out.println(newUserEmail);
        assertEquals(updateUser.getString("email"),"perica@yahoo.net");

        Response responseUpdate = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer 19d48b78574c8a903980eb1f881337d424496c1bfb23c8404d559b97ffbeb678")
                .and()
                .body("{\"email\": \"perica2@yahoo.net\"}")
                .when()
                .patch ("public/v2/users/" + newUserId)
                .then()
                .extract().response();

        System.out.println(responseUpdate.getBody().asString());
        assertEquals(201, response.statusCode());
        JSONObject updatedEmail = new JSONObject(responseUpdate.getBody().asString());
        assertEquals("perica2@yahoo.net", updatedEmail.getString("email"));

        //Delete user
        Response responseDelete = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer 19d48b78574c8a903980eb1f881337d424496c1bfb23c8404d559b97ffbeb678")
                .and()
                .when()
                .delete ("public/v2/users/" + newUserId)
                .then()
                .extract().response();

        System.out.println(responseDelete.getBody().asString());
        assertEquals(201, response.statusCode());
    }

    @Test
    public void getUser(){

        String userId = "3532";
        Response getUserResponse = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer 19d48b78574c8a903980eb1f881337d424496c1bfb23c8404d559b97ffbeb678")
                .and()
                .when()
                .get("public/v2/users/" + userId);

//        JSONObject user = new JSONObject(getUserResponse.getBody().asString());
//
//        System.out.println(user.getInt("id"));

        System.out.println(getUserResponse.getBody().asString());
    }
}
