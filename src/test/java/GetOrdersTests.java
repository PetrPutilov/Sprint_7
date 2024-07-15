import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CancelOrderRequest;
import model.CreateOrderRequest;
import model.CreateOrderResponse;
import model.GetOrdersResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GetOrdersTests extends BaseTest {


    @Test
    @DisplayName("can get orders")
    public void canGetOrders() {
        //Given
        CreateOrderRequest createRequest = new CreateOrderRequest(
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

        Response createOrderResponse = createOrder(createRequest);

        //When
        GetOrdersResponse getOrdersResponseBody = given().get("/api/v1/orders").getBody().as(GetOrdersResponse.class);
        //Then
        Assertions.assertNotNull(getOrdersResponseBody);
        Assertions.assertFalse(getOrdersResponseBody.getOrders().isEmpty());

        //Clean
        CreateOrderResponse createOrderResponseBody = createOrderResponse.body().as(CreateOrderResponse.class);
        cancelOrder(new CancelOrderRequest(createOrderResponseBody.getTrack()));
    }
}
