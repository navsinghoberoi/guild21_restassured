package tests;

import base.ApiBaseUtil;
import config.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CreateUserTests extends ApiBaseUtil {

    private Response response;

    @Description("Hitting the api/users endpoint to fetch user data")
    @Severity(SeverityLevel.BLOCKER)
    @Test()
    public void validateApiResponseCode() {
        String reqBody = "{\n" +
                "    \"name\": \"Thomas\",\n" +
                "    \"job\": \"Shelby\"\n" +
                "}";

        response = executePostRequest(Constants.BASE_URL + "/api/users", reqBody);
        validateStatusCode(response, 201);
    }

    @Description("Validating api response time is within accepted limits")
    @Severity(SeverityLevel.NORMAL)
    @Test(dependsOnMethods = "validateApiResponseCode")
    public void validateApiResponseTime() {
        validateResponseTime(response, 1500);
    }

}
