package APISuport;

import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAPI {


    public static Response createUser(String token, String userData){
        //Creating user
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .and()
                .body(userData)
                .when()
                .post("public/v2/users")
                .then()
                .extract().response();

        return response;
    }
    
    public static Response editUser(int id, String token, String userData){
        //Creating user
        Response response = given()
                .header("Content-type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer "+token)
                .and()
                .body(userData)
                .when()
                .patch("/public/v2/users/" + id)
                .then()
                .extract().response();

        return response;
    }

    public static Response deleteUser(String token, int deleteId){

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .delete ("public/v2/users/" + deleteId)
                .then()
                .extract().response();

        return response;
    }


    public static Response getUser(String token, int userId){

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer "+ token)
                .when()
                .get("public/v2/users/" + userId)
                .then()
                .extract().response();;

        return response;
    }



    public static void checkUser(int statusCode, Response response, String email, String message){
        assertEquals(statusCode, response.statusCode());

        if(email != null){
            assertEquals(getAttribute(response, "email"), email);
        }

        if(message != null){
            assertEquals(getAttribute(response, "message"), message);
        }
    }

    public static String getAttribute(Response response, String attribute){
        JSONObject user = new JSONObject(response.getBody().asString());

        if(attribute == "id"){
            return Integer.toString(user.getInt(attribute));
        }
        return user.getString(attribute);
    }

}
