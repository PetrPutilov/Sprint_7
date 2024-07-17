package api.client;

import io.restassured.response.Response;
import model.CancelOrderRequest;
import model.CreateOrderRequest;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    public Response createOrder(CreateOrderRequest request) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(request)
                .when()
                .post("/api/v1/orders");
    }

    public void cancelOrder(CancelOrderRequest request) {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(request)
                .when()
                .put("/api/v1/orders/cancel");
    }
}
