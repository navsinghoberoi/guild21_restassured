package base;


import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.AssertJUnit.assertEquals;


public class ApiBaseUtil {

    @Step("Setting the baseURI as {0}")
    public static void setBaseURI(String uri) {
        System.out.println("Setting base URI to " + uri);
        baseURI = uri;
    }

    @Step("Resetting the baseURI")
    public static void resetBaseURI() {
        System.out.println("Resetting base URI");
        RestAssured.baseURI = null;
    }

    @Step("Validating that response is within {1} ms")
    public static ValidatableResponse validateResponseTime(Response response, long timeInMillis) {
        System.out.println("API response time = " + response.getTimeIn(TimeUnit.MILLISECONDS) + "ms");
        return response.then().time(lessThan(timeInMillis));
    }

    @Step("Validating that response status code is equal to {1}")
    public static void validateStatusCode(Response response, int expectedStatusCode) {
        System.out.println("Status code of api response = " + response.getStatusCode());
        assertEquals("Status Code Check Failed", expectedStatusCode, response.getStatusCode());
    }

    public static void printResponseBody(Response response) {
        System.out.println("**************************************************");
        System.out.println("API response body = " + response.getBody().asString());
        System.out.println("**************************************************");
    }


    public Response executeGetRequest(String endpoint) {
        System.out.println("Initiating GET request at endpoint -> " + endpoint);
        Response response = given()
                .filter(new AllureRestAssured())
                .log().all()
                .when()
                .get(endpoint);
        printResponseBody(response);
        return response;
    }

    public Response executePostRequest(String endpoint, Object requestBody) {
        System.out.println("Initiating POST request with payload " + requestBody + " at endpoint -> " + endpoint);
        Response response = given()
                .filter(new AllureRestAssured())
                .log().all()
                .when()
                .body(requestBody)
                .post(endpoint);
        printResponseBody(response);
        return response;
    }


}

