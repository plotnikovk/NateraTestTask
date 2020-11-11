import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import model.Triangle;
import org.testng.annotations.BeforeClass;
import resthelper.RestHelper;
import java.util.List;

@Slf4j
public class BaseTriangleServiceTest {
    Triangle initialTriangle;
    Triangle responseTriangle;

    @BeforeClass
    void clear() throws JsonProcessingException {
        List<Triangle> triangles = RestHelper.getAllTriangles();
        triangles.forEach(triangle -> RestHelper.deleteById(triangle.getId()));
    }

    static boolean equal(Triangle triangle1, Triangle triangle2){
        return triangle1.getFirstSide().compareTo(triangle2.getFirstSide()) == 0 &&
                triangle1.getSecondSide().compareTo(triangle2.getSecondSide()) == 0 &&
                triangle1.getThirdSide().compareTo(triangle2.getThirdSide()) == 0;
    }
}
