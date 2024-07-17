import api.client.CourierClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.CreateCourierRequest;
import model.LoginCourierRequest;
import model.LoginCourierResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseCourierTest extends BaseTest {
    private final CourierClient courierClient = new CourierClient();

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
    public void createCourierRequest() {
        createRequest = new CreateCourierRequest("snow", "12345", "john");
    }

    @Step("create courier")
    public Response createCourier(CreateCourierRequest request) {
        return courierClient.createCourier(request);
    }

    @Step("login courier")
    public Response loginCourier(LoginCourierRequest request) {
        return courierClient.loginCourier(request);
    }

    @Step("remove courier")
    public Response removeCourier(String courierId) {
        return courierClient.removeCourier(courierId);
    }

}
