package resthelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Triangle;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class RestHelper {
    static ObjectMapper mapper = new ObjectMapper();

    static {
        RestAssured.baseURI = "https://qa-quiz.natera.com/triangle";
        RestAssured.requestSpecification = given()
                .header("X-User", "2d648c44-4698-43e4-b96a-89e984aee749");
    }

    public static Response getAll(){
        return get("/all");
    }

    public static List<Triangle> getAllTriangles() throws JsonProcessingException {
        List<Triangle> triangles = Arrays.asList(mapper.readValue(getAll().getBody().asString(), Triangle[].class));
        return triangles;
    }

    public static Response getById(String id){
        return get("/".concat(id));
    }

    public static Triangle getTriangleById(String id) throws JsonProcessingException {
        Triangle triangle = mapper.readValue(getById(id).getBody().asString(), Triangle.class);
        return triangle;
    }

    public static Response deleteById(String id){
        return delete("/".concat(id));
    }

    public static Response getPerimeter(String id){
        return get("/".concat(id).concat("/perimeter"));
    }

    public static BigDecimal getPerimeterNumber(String id){
        JSONObject jsonResponce = new JSONObject(getPerimeter(id).asString());
        BigDecimal perimeter = BigDecimal.valueOf(jsonResponce.getDouble("result"));
        return perimeter;
    }

    public static Response getArea(String id){
        return get("/".concat(id).concat("/area"));
    }

    public static BigDecimal getAreaNumber(String id){
        JSONObject jsonResponce = new JSONObject(getArea(id).asString());
        BigDecimal area = BigDecimal.valueOf(jsonResponce.getDouble("result"));
        return area;
    }

    public static Response postTriangle(Triangle triangle){
        return requestSpecification
                .header("Content-Type", "application/json")
                .body(triangle.jsonPost())
                .post();
    }

    public static Triangle getTriangleFromResponse(Response response) throws JsonProcessingException {
        Triangle triangle = mapper.readValue(response.getBody().asString(), Triangle.class);
        return triangle;
    }

    public static Response postTriangle(BigDecimal[] sides){
        return requestSpecification
                .header("Content-Type", "application/json")
                .body(generatePostJson(sides))
                .post();
    }

    public static Triangle triangleFromResponse(Response response) throws JsonProcessingException {
        Triangle triangle = mapper.readValue(response.getBody().asString(), Triangle.class);
        return triangle;
    }

    private static String generatePostJson(BigDecimal[] sides){
        String result = "{\"input\": \"";
        result = result.concat(StringUtils.join(sides,";"));
        result = result.concat("\"}");
        return result;
    }


}
