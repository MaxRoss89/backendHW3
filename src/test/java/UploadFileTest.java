import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static io.restassured.RestAssured.given;


public class UploadFileTest extends BaseTest {
    String uploadedImageId;

    @Test
    void uploadFileImageTest() {
        uploadedImageId = given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/logo.jpeg"))
                .expect()
                .statusCode(200)
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");

    }


    @AfterEach
    void tearDown() {
        given()
                .headers("Authorization", token)
                .when()
                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", username, uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}