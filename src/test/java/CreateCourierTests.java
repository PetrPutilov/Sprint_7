import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CreateCourierRequest;
import model.CreateCourierResponse;
import model.LoginCourierRequest;
import model.LoginCourierResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTests extends BaseTest {

    @Test
    @DisplayName("can create courier")
    public void canCreateCourier() {
        //Given
        CreateCourierRequest createRequest = new CreateCourierRequest("snow", "12345", "john");
        //When
        Response createCourierResponse = createCourier(createRequest);
        //Then
        createCourierResponse.then().statusCode(201)
                .and().assertThat().body("ok", equalTo(true));

        //Clean
        LoginCourierResponse loginResponse = loginCourier(new LoginCourierRequest(createRequest.getLogin(), createRequest.getPassword())).body().as(LoginCourierResponse.class);
        removeCourier(loginResponse.getId().toString());
    }

    @Test
    @DisplayName("can not create the same courier")
    public void canNotCreateTheSameCourier() {
        //Given
        CreateCourierRequest createRequest = new CreateCourierRequest("snow", "12345", "john");
        createCourier(createRequest);
        //When
        Response createCourierResponse = createCourier(createRequest);
        //Then
        createCourierResponse.then().statusCode(409)
                .and().assertThat().body("message", equalTo("Этот логин уже используется"));

        //Clean
        LoginCourierResponse loginResponse = loginCourier(new LoginCourierRequest(createRequest.getLogin(), createRequest.getPassword())).body().as(LoginCourierResponse.class);
        removeCourier(loginResponse.getId().toString());
    }

    @Test
    @DisplayName("can not create courier with same login")
    public void canNotCreateCourierWithSameLogin() {
        //Given
        CreateCourierRequest createRequest = new CreateCourierRequest("snow", "12345", "john");
        CreateCourierRequest createSameLoginRequest = new CreateCourierRequest("snow", "67890", "edward");
        createCourier(createRequest);
        //When
        Response createCourierResponse = createCourier(createSameLoginRequest);
        //Then
        createCourierResponse.then().statusCode(409)
                .and().assertThat().body("message", equalTo("Этот логин уже используется"));

        //Clean
        LoginCourierResponse loginResponse = loginCourier(new LoginCourierRequest(createRequest.getLogin(), createRequest.getPassword())).body().as(LoginCourierResponse.class);
        removeCourier(loginResponse.getId().toString());
    }

    @Test
    @DisplayName("can not create courier with no required field")
    public void canNotCreateCourierWithNoRequiredField() {
        //Given
        CreateCourierRequest createRequest = new CreateCourierRequest("snow", null, null);
        //When
        Response createCourierResponse = createCourier(createRequest);
        //Then
        createCourierResponse.then().statusCode(400)
                .and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

        //Clean
        Response loginResponse = loginCourier(new LoginCourierRequest(createRequest.getLogin(), createRequest.getPassword()));
        if (loginResponse.statusCode() == 200) {
            LoginCourierResponse loginCourierResponseBody= loginResponse.body().as(LoginCourierResponse.class);
            removeCourier(loginCourierResponseBody.getId().toString());
        }

    }

    @Test
    @DisplayName("create courier request gives right response")
    public void createCourierSendsRightResponse() {
        //Given
        CreateCourierRequest createRequest = new CreateCourierRequest("snow", "12345", "john");
        //When
        CreateCourierResponse createCourierResponseBody = createCourier(createRequest).body().as(CreateCourierResponse.class);
        //Then
        Assertions.assertEquals(true, createCourierResponseBody.getOk());

        //Clean
        LoginCourierResponse loginResponse = loginCourier(new LoginCourierRequest(createRequest.getLogin(), createRequest.getPassword())).body().as(LoginCourierResponse.class);
        removeCourier(loginResponse.getId().toString());
    }

}
