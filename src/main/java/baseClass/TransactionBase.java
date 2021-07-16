package baseClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.*;

import static io.restassured.RestAssured.given;

public class TransactionBase {

    private String userToken = null;
    private String cardProductToken = null;
    private String cardToken =  null;
    private String user = "18da7496-be57-4d36-abb5-00f53dd8bb17";
    private String password = "acda937f-6b95-4789-a1c8-613be4117c2b";

    public void createUser(){
        String data = "{\"first_name\": \"Abhishek\",\"last_name\": \"Krishna\",\"active\": true}";
        String baseUrl = "https://sandbox-api.marqeta.com/v3/users";
        RestAssured.baseURI = baseUrl;
        //Step - 1
        //Test will start from generating user Token

        Response response = given()
                .auth()
                .preemptive()
                .basic(user,password)
                .header("Content-Type", "application/json")
                .body(data)
                .post()
                .then().extract().response();
        Assert.assertEquals(response.getStatusCode(), 201);

        String jsonString = response.asString();
        Assert.assertTrue(jsonString.contains("token"));

        //This token will be used in later requests
        userToken = JsonPath.from(jsonString).get("token");
    }

    public void retrieveUser(){
        //Step - 1
        //Test will retrive the user based on user token
        String baseUrl = "https://sandbox-api.marqeta.com/v3/users/" + userToken;
        RestAssured.baseURI = baseUrl;
        Response response = given()
                .auth()
                .preemptive()
                .basic(user,password)
                .header("Content-Type", "application/json")
                .get()
                .then().extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    public void getCardProduct(){
        String data = "{}";
        String baseUrl = "https://sandbox-api.marqeta.com/v3/cardproducts";
        RestAssured.baseURI = baseUrl;
        //Step - 1
        //Test will start from generating Token for card product

        Response response = given()
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .header("Authorization", "Basic MThkYTc0OTYtYmU1Ny00ZDM2LWFiYjUtMDBmNTNkZDhiYjE3OmFjZGE5MzdmLTZiOTUtNDc4OS1hMWM4LTYxM2JlNDExN2MyYg==")
                .body(data)
                .get()
                .then().extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);

        String jsonString = response.asString();
        Assert.assertTrue(jsonString.contains("token"));
        //This token will be used in later requests
        ArrayList<List> dataList = JsonPath.from(jsonString).get("data");
        LinkedHashMap dataHashMap = (LinkedHashMap) dataList.get(0);
        cardProductToken = (String) dataHashMap.get("token");

    }

    public void CreateCard(){
        String data = "{\"user_token\": \"" + userToken + "\" ,\"card_product_token\": \"" + cardProductToken + "\"}";
        String baseUrl = "https://sandbox-api.marqeta.com/v3/cards";
        RestAssured.baseURI = baseUrl;
        //Step - 1
        //Test will start from generating Token for card product

        Response response = given()
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .header("Authorization", "Basic MThkYTc0OTYtYmU1Ny00ZDM2LWFiYjUtMDBmNTNkZDhiYjE3OmFjZGE5MzdmLTZiOTUtNDc4OS1hMWM4LTYxM2JlNDExN2MyYg==")
                .body(data)
                .post()
                .then().extract().response();
        Assert.assertEquals(response.getStatusCode(), 201);
        String jsonString = response.asString();
        Assert.assertTrue(jsonString.contains("token"));

        //This token will be used in later requests
        cardToken = JsonPath.from(jsonString).get("token");
    }

    public void CreateTransaction(){
        String data = "{\"amount\": \"10\", \"mid\": \"123456890\",\"card_token\": \"" + cardToken + "\"}";
        String baseUrl = "https://sandbox-api.marqeta.com/v3/simulate/authorization";
        RestAssured.baseURI = baseUrl;
        //Step - 1
        //Test will start from generating Token for card product

        Response response = given()
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .header("Authorization", "Basic MThkYTc0OTYtYmU1Ny00ZDM2LWFiYjUtMDBmNTNkZDhiYjE3OmFjZGE5MzdmLTZiOTUtNDc4OS1hMWM4LTYxM2JlNDExN2MyYg==")
                .body(data)
                .post()
                .then().extract().response();
        System.out.println(response.body().prettyPrint());
        Assert.assertEquals(response.getStatusCode(), 201);
    }

}
