package utility;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BaseAPI {

	public static Response sendPostApiRequest(String baseUri, String endPoint, JSONObject requestBody) {
        RestAssured.baseURI = baseUri;
        
		Response response = given()
                .header("Content-Type", "application/json") // Add header
                .header("x-rapidapi-ua", "RapidAPI-Playground")
                .header("x-rapidapi-key","d33f822ef4mshe84e177951f0c3dp123c0djsn19e523d56b99")
                .header("x-rapidapi-host","rapid-translate-multi-traduction.p.rapidapi.com")
                .body(requestBody.toString()) // Attach request body
                .when()
                .post(endPoint) // Specify the endpoint
                .then()
                .extract()
                .response();

        return response;
	}
}
