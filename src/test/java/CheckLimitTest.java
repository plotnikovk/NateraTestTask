import com.fasterxml.jackson.core.JsonProcessingException;
import enums.HttpCode;
import io.restassured.response.Response;
import model.Triangle;
import org.testng.annotations.Test;
import resthelper.RestHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CheckLimitTest extends BaseTriangleServiceTest{
    private static final int LIMIT = 10;
    private static List<String> ids = new ArrayList<>();
    @Test(priority = 1)
    void checkLimit() throws JsonProcessingException {
        for (int i = 0; i < LIMIT; i++){
            Response response = RestHelper.postTriangle(new Triangle(BigDecimal.valueOf(5), BigDecimal.valueOf(5), BigDecimal.valueOf(5)));
            assert response.getStatusCode() == HttpCode.OK.getCode();
            ids.add(RestHelper.triangleFromResponse(response).getId());
        }
    }
    @Test(priority = 2)
    void testGetAll() throws JsonProcessingException {
        List<Triangle> triangles = RestHelper.getAllTriangles();
        assert triangles.size() == ids.size();
        for (Triangle triangle:triangles){
            assert ids.contains(triangle.getId());
        }
    }

    @Test(priority = 3)
    void addOneMore(){
        Response response = RestHelper.postTriangle(new Triangle(BigDecimal.valueOf(5), BigDecimal.valueOf(5), BigDecimal.valueOf(5)));
        assert response.getStatusCode() == HttpCode.Unprocessable.getCode();
    }
    @Test(priority = 4)
    void deleteTriangles(){
        for (String id: ids){
            Response response = RestHelper.deleteById(id);
            assert response.getStatusCode() == HttpCode.OK.getCode();
            response = RestHelper.getById(id);
            assert response.getStatusCode() == HttpCode.NotFound.getCode();
        }
    }
}
