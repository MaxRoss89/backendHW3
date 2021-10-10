import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;


public class UploadJpegTest extends BaseTest {
    String uploadedImageId;


    @Test
    void uploadFileJPEGTest() {
        String encodedFile;
        byte[] byteArray = getFileContent("src/test/resources/logo.jpeg");
        encodedFile = Base64.getEncoder().encodeToString(byteArray);
        uploadedImageId = given()
                .headers("Authorization", token)
                .multiPart("image", encodedFile)
                .expect()
                .body("success", is(true))
                .body("data.id", is(notNullValue()))
                .when()
                .post("https://api.imgur.com/3/image")
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

    private byte[] getFileContent(String PATH_TO_IMAGE) {
        byte[] byteArray = new byte[0];
        try {
            byteArray = FileUtils.readFileToByteArray(new File(PATH_TO_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }
}