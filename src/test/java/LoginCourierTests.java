import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CreateCourierRequest;
import model.LoginCourierRequest;
import model.LoginCourierResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

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
        loginResponse.then().statusCode(200)
                .and().body("id", notNullValue());

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
        Response loginResponse = loginCourier(new LoginCourierRequest(null, createRequest.getPassword()));

        //Then
        loginResponse.then().statusCode(400)
                .and().assertThat().body("message", equalTo("Недостаточно данных для входа"));

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
        loginResponse.then().statusCode(404)
                .and().assertThat().body("message", equalTo("Учетная запись не найдена"));

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
        loginResponse.then().statusCode(404)
                .and().assertThat().body("message", equalTo("Учетная запись не найдена"));

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
