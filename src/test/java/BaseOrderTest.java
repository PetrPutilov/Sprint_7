import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.CancelOrderRequest;
import model.CreateOrderRequest;
import model.CreateOrderResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BaseOrderTest extends BaseTest{

    protected CreateOrderRequest createRequest;

    protected Response createOrderResponse;

    @AfterEach
    public void cleanOrder(){
        CreateOrderResponse createOrderResponseBody = createOrderResponse.body().as(CreateOrderResponse.class);
        cancelOrder(new CancelOrderRequest(createOrderResponseBody.getTrack()));
    }

    @BeforeEach
    public void createOrderRequest(){
        createRequest = new CreateOrderRequest(
                "Tireon",
                "Lannister",
                "Winterfell, 12",
                "dragonfleet",
                "+489567678",
                30,
                OffsetDateTime.now().plusDays(3),
                "A Lannister Always Pays His Debts",
                List.of()
        );
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
