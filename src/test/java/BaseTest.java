import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.CancelOrderRequest;
import model.CreateCourierRequest;
import model.CreateOrderRequest;
import model.LoginCourierRequest;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class BaseTest {
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
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

    @Step("create order")
    public Response createOrder(CreateOrderRequest request) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(request)
                .when()
                .post("/api/v1/orders");
    }

    @Step("cancel order")
    public void cancelOrder(CancelOrderRequest request) {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(request)
                .when()
                .put("/api/v1/orders/cancel");
    }
}
