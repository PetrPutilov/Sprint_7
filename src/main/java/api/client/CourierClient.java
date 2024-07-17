package api.client;

import io.restassured.response.Response;
import model.CreateCourierRequest;
import model.LoginCourierRequest;

import static io.restassured.RestAssured.given;

public class CourierClient {
    public Response createCourier(CreateCourierRequest request) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(request)
                .when()
                .post("/api/v1/courier");
    }

    public Response loginCourier(LoginCourierRequest request) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(request)
                .when()
                .post("/api/v1/courier/login");
    }

    public Response removeCourier(String courierId) {
        return given()
                .header("Content-type", "application/json")
                .pathParam("id", courierId)
                .when()
                .delete("/api/v1/courier/{id}");
    }
}
