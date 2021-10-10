import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;



public class GetNullImageTest extends BaseTest {


    @Test
    void getImageNULLNegativeTest() {
        given()
                .header("Authorization", token)
                .when()
                .get("https://api.imgur.com/3/image/1")
                .prettyPeek()
                .then()
                .statusCode(404);
    }
}

