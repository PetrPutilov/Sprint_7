import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.LoginCourierRequest;
import model.LoginCourierResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTests extends BaseCourierTest{

    @Test
    @DisplayName("can login courier")
    public void canLoginCourier() {
        //Given
        createCourier(createRequest);

        //When
        Response loginResponse = loginCourier(new LoginCourierRequest(createRequest.getLogin(), createRequest.getPassword()));

        //Then
        loginResponse.then().statusCode(200)
                .and().body("id", notNullValue());

    }

    @Test
    @DisplayName("can not login courier with no required fields")
    public void canNotLoginCourierWithNoRequiredFields() {
        //Given
        createCourier(createRequest);

        //When
        Response loginResponse = loginCourier(new LoginCourierRequest(null, createRequest.getPassword()));

        //Then
        loginResponse.then().statusCode(400)
                .and().assertThat().body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("can not login courier with improper login")
    public void canNotLoginCourierWithImproperLogin() {
        //Given
        createCourier(createRequest);

        //When
        Response loginResponse = loginCourier(new LoginCourierRequest("stark", createRequest.getPassword()));

        //Then
        loginResponse.then().statusCode(404)
                .and().assertThat().body("message", equalTo("Учетная запись не найдена"));

    }

    @Test
    @DisplayName("can not login courier if not created")
    public void canNotLoginCourierIfNotCreated() {
        //Given
        createCourier(createRequest);

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
        createCourier(createRequest);

        //When
        Response loginResponse = loginCourier(new LoginCourierRequest(createRequest.getLogin(), createRequest.getPassword()));

        //Then
        LoginCourierResponse loginResponseBody = loginResponse.body().as(LoginCourierResponse.class);
        Assertions.assertNotNull(loginResponseBody.getId());

    }
}
