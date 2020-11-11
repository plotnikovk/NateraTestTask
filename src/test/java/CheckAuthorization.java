import enums.HttpCode;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.config.HeaderConfig.headerConfig;

public class CheckAuthorization {
    @DataProvider
    static Object[][] provider(){
        return new Object[][]{
                {"No header X-User", null},
                {"Wrong X-User", "3bda64c9-6439-4ad3-829c-a6fd7b9e55fe"},
                {"Empty X-User", ""}

        };
    }

    @Test(dataProvider = "provider")
    void check(String description, String user){
        if(user != null) {
            RestAssured.requestSpecification = given()
                    .config(RestAssuredConfig.config().headerConfig(headerConfig().overwriteHeadersWithName("X-User")))
                    .header("X-User", user);
        }
        else {
            given().when().get("https://qa-quiz.natera.com/triangle/all").then().statusCode(HttpCode.Unauthorized.getCode());
        }
    }
}
