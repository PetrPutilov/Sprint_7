import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CreateCourierRequest;
import model.LoginCourierRequest;
import model.LoginCourierResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginCourierTests extends BaseTest{

    @Test
    @DisplayName("can login courier")
    public void canLoginCourier() {
        //Given
        CreateCourierRequest createRequest = new CreateCourierRequest("snow", "12345", "john");
        createCourier(createRequest);

        //When
        Response loginResponse = loginCourier(new LoginCourierRequest(createRequest.getLogin(), createRequest.getPassword()));

        //Then
        loginResponse.then().statusCode(200);

        //Clean
        LoginCourierResponse loginResponseBody = loginResponse.body().as(LoginCourierResponse.class);
        removeCourier(loginResponseBody.getId().toString());
    }

    @Test
    @DisplayName("can not login courier with no required fields")
    public void canNotLoginCourierWithNoRequiredFields() {
        //Given
        CreateCourierRequest createRequest = new CreateCourierRequest("snow", "12345", "john");
        createCourier(createRequest);

        //When
        Response loginResponse = loginCourier(new LoginCourierRequest(createRequest.getLogin(), null));

        //Then
        loginResponse.then().statusCode(400);

        //Clean
        LoginCourierResponse loginResponseBody = loginCourier(new LoginCourierRequest(createRequest.getLogin(), createRequest.getPassword())).body().as(LoginCourierResponse.class);
        removeCourier(loginResponseBody.getId().toString());
    }

    @Test
    @DisplayName("can not login courier with improper login")
    public void canNotLoginCourierWithImproperLogin() {
        //Given
        CreateCourierRequest createRequest = new CreateCourierRequest("snow", "12345", "john");
        createCourier(createRequest);

        //When
        Response loginResponse = loginCourier(new LoginCourierRequest("stark", createRequest.getPassword()));

        //Then
        loginResponse.then().statusCode(404);

        //Clean
        LoginCourierResponse loginResponseBody = loginCourier(new LoginCourierRequest(createRequest.getLogin(), createRequest.getPassword())).body().as(LoginCourierResponse.class);
        removeCourier(loginResponseBody.getId().toString());
    }

    @Test
    @DisplayName("can not login courier if not created")
    public void canNotLoginCourierIfNotCreated() {

        //When
        Response loginResponse = loginCourier(new LoginCourierRequest("lanister", "45678"));

        //Then
        loginResponse.then().statusCode(404);

    }

    @Test
    @DisplayName("success login courier returns proper response")
    public void successLoginReturnsProperResponse() {
        //Given
        CreateCourierRequest createRequest = new CreateCourierRequest("snow", "12345", "john");
        createCourier(createRequest);

        //When
        Response loginResponse = loginCourier(new LoginCourierRequest(createRequest.getLogin(), createRequest.getPassword()));

        //Then
        LoginCourierResponse loginResponseBody = loginResponse.body().as(LoginCourierResponse.class);
        Assertions.assertNotNull(loginResponseBody.getId());

        //Clean
        removeCourier(loginResponseBody.getId().toString());
    }
}
