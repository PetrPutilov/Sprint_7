import api.client.OrdersClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.CancelOrderRequest;
import model.CreateOrderRequest;
import model.CreateOrderResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.OffsetDateTime;
import java.util.List;

public class BaseOrderTest extends BaseTest{

    private final OrdersClient ordersClient = new OrdersClient();

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
        return ordersClient.createOrder(request);
    }

    @Step("cancel order")
    public void cancelOrder(CancelOrderRequest request) {
        ordersClient.cancelOrder(request);
    }
}
