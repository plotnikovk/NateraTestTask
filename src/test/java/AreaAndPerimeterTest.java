import com.fasterxml.jackson.core.JsonProcessingException;
import enums.HttpCode;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import model.Triangle;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import resthelper.RestHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AreaAndPerimeterTest extends BaseTriangleServiceTest{
    private static List<String> createdTrianglesIds = new ArrayList<>();
    private static List<Triangle> initialTriangles = new ArrayList<>();
    @DataProvider
    static Object[][] provider(){
        return new Object[][]{
                {new Triangle(BigDecimal.valueOf(3.0), BigDecimal.valueOf(4.0), BigDecimal.valueOf(5.0))},
                {new Triangle(BigDecimal.valueOf(3.1), BigDecimal.valueOf(4.2), BigDecimal.valueOf(5.3))},
                {new Triangle(BigDecimal.valueOf(3.000000000000001), BigDecimal.valueOf(4.200000000000009), BigDecimal.valueOf(5.300000000000005))},
                {new Triangle(BigDecimal.valueOf(10.0), BigDecimal.valueOf(10.0), BigDecimal.valueOf(10.0000000001))},
                {new Triangle(BigDecimal.valueOf(5.0), BigDecimal.valueOf(5.0), BigDecimal.valueOf(5.0))}
        };
    }

    @DataProvider
    static Object[][] initialTriangles(){
        Object[][] result = new Object[initialTriangles.size()][1];
        for(int i=0; i<initialTriangles.size(); i++){
            result[i] = new Object[]{initialTriangles.get(i)};
        }
        return result;
    }

    @Test(dataProvider = "provider")
    void createTriangles(Triangle triangle) throws JsonProcessingException {
        Response response = RestHelper.postTriangle(triangle);
        Triangle responseTriangle = RestHelper.getTriangleFromResponse(response);
        triangle.setId(responseTriangle.getId());
        initialTriangles.add(triangle);
    }

    @Test(dataProvider = "initialTriangles", dependsOnMethods = "createTriangles")
    void checkPerimeter(Triangle triangle){
        BigDecimal calculatedPerimeter = triangle.calcPerimeter();
        BigDecimal perimeterFromService = RestHelper.getPerimeterNumber(triangle.getId());
        log.debug("Calculated:" + calculatedPerimeter.toString());
        log.debug("From Service:" + perimeterFromService.toString());
        assert calculatedPerimeter.compareTo(perimeterFromService) == 0;
    }

    @Test(dataProvider = "initialTriangles", dependsOnMethods = "createTriangles")
    void checkArea(Triangle triangle){
        BigDecimal calculatedArea = triangle.calcArea();
        BigDecimal areaFromService = RestHelper.getAreaNumber(triangle.getId());
        log.debug("Calculated:" + calculatedArea.toString());
        log.debug("From Service:" + areaFromService.toString());
        assert calculatedArea.compareTo(areaFromService) == 0;
    }
}
