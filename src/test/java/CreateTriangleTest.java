import com.fasterxml.jackson.core.JsonProcessingException;
import enums.HttpCode;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import model.Triangle;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import resthelper.RestHelper;

import java.math.BigDecimal;

@Slf4j
public class CreateTriangleTest extends BaseTriangleServiceTest{
    @Test(dataProvider = "provider")
    void createTriangle(String description, BigDecimal[] sides, HttpCode expectedCode) throws JsonProcessingException {
        Response response = RestHelper.postTriangle(sides);
        assert (response.getStatusCode() == expectedCode.getCode());
        if(expectedCode.equals(HttpCode.OK)){
            initialTriangle = new Triangle(sides[0], sides[1], sides[2]);
            responseTriangle = RestHelper.triangleFromResponse(response);
            assert equal(initialTriangle, responseTriangle);

            response = RestHelper.getById(responseTriangle.getId());
            assert response.getStatusCode() == HttpCode.OK.getCode();
            responseTriangle = RestHelper.triangleFromResponse(response);
            assert equal(initialTriangle, responseTriangle);
        }
    }

    @DataProvider
    static Object[][] provider(){
        return new Object[][]{
                {"Positive Integer numbers, triangle inequality performed", new BigDecimal[]{BigDecimal.valueOf(3.0), BigDecimal.valueOf(4.0), BigDecimal.valueOf(5.0)}, HttpCode.OK},
                {"Positive sides, triangle inequality performed", new BigDecimal[]{BigDecimal.valueOf(3.1), BigDecimal.valueOf(4.2), BigDecimal.valueOf(5.3)}, HttpCode.OK},
                {"Positive sides, 15 decimal places, triangle inequality performed", new BigDecimal[]{BigDecimal.valueOf(3.000000000000001), BigDecimal.valueOf(4.200000000000009), BigDecimal.valueOf(5.300000000000005)}, HttpCode.OK},
                {"a+b=c, triangle inequality not performed", new BigDecimal[]{BigDecimal.valueOf(3.0), BigDecimal.valueOf(4.0), BigDecimal.valueOf(7.0)}, HttpCode.Unprocessable},
                {"Triangle inequality not performed", new BigDecimal[]{BigDecimal.valueOf(3.0), BigDecimal.valueOf(4.0), BigDecimal.valueOf(8.0)}, HttpCode.Unprocessable},
                {"Zero side", new BigDecimal[]{BigDecimal.ZERO, BigDecimal.valueOf(5.0), BigDecimal.valueOf(5.0)}, HttpCode.Unprocessable},
                {"Zero all sides", new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO}, HttpCode.Unprocessable},
                {"Negative side", new BigDecimal[]{BigDecimal.valueOf(3.0), BigDecimal.valueOf(4.0), BigDecimal.valueOf(-5.0)}, HttpCode.Unprocessable},
                {"Negative all sides", new BigDecimal[]{BigDecimal.valueOf(-3.0), BigDecimal.valueOf(-4.0), BigDecimal.valueOf(-5.0)}, HttpCode.Unprocessable},
                {"2 sides", new BigDecimal[]{BigDecimal.valueOf(3.0), BigDecimal.valueOf(4.0)}, HttpCode.Unprocessable},
                {"4 sides", new BigDecimal[]{BigDecimal.valueOf(3.0), BigDecimal.valueOf(4.0), BigDecimal.valueOf(5.0), BigDecimal.valueOf(3.0)}, HttpCode.Unprocessable},
        };
    }
}
