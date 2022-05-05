import APISuport.UserAPI;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.jws.soap.SOAPBinding;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersTest {

    final String token = "45b8513e306e5cd1c19f5571f94be6ac810b1766bcc59943963f38a7116071cc";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://gorest.co.in/";
    }
/////

    @Test
    public void User(){

        Response response = UserAPI.createUser(token, "{\"name\": \"Pera Peric\",\n" +
                                                            "    \"email\": \"perica@yahoo.net\",\n" +
                                                            "    \"gender\": \"male\",\n" +
                                                            "    \"status\": \"active\"}");
        UserAPI.checkUser(201, response, "perica@yahoo.net", null);

        int id = Integer.parseInt(UserAPI.getAttribute(response,"id"));
        response = UserAPI.editUser(id, token, "{\"email\": \"perica2@yahoo.net\"}");
        UserAPI.checkUser(200, response, "perica2@yahoo.net", null);

        //delete user
        response = UserAPI.deleteUser(token, id);
        UserAPI.checkUser(204, response, null, null);

        response = UserAPI.getUser(token, id);
        UserAPI.checkUser(404, response, null, "Resource not found");
    }
}
