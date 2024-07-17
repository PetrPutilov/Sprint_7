import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.given;

public class BaseCourierTest extends BaseTest {

    protected CreateCourierRequest createRequest;

    @AfterEach
    public void cleanCourier() {
        Response loginResponse = loginCourier(new LoginCourierRequest(createRequest.getLogin(), createRequest.getPassword()));
        if (loginResponse.statusCode() == 200) {
            LoginCourierResponse loginCourierResponseBody = loginResponse.body().as(LoginCourierResponse.class);
            removeCourier(loginCourierResponseBody.getId().toString());
        }
    }

    @BeforeEach
    public void createCourierRequest(){
        createRequest = new CreateCourierRequest("snow", "12345", "john");
    }

    @Step("create courier")
    public Response createCourier(CreateCourierRequest request) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(request)
                .when()
                .post("/api/v1/courier");
    }

    @Step("login courier")
    public Response loginCourier(LoginCourierRequest request) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(request)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("remove courier")
    public Response removeCourier(String courierId) {
        return given()
                .header("Content-type", "application/json")
                .pathParam("id", courierId)
                .when()
                .delete("/api/v1/courier/{id}");
    }

}
