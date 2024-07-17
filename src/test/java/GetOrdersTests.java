import io.qameta.allure.junit4.DisplayName;
import model.GetOrdersResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetOrdersTests extends BaseOrderTest {


    @Test
    @DisplayName("can get orders")
    public void canGetOrders() {
        //Given
        createOrderResponse = createOrder(createRequest);

        //When
        GetOrdersResponse getOrdersResponseBody = given().get("/api/v1/orders").getBody().as(GetOrdersResponse.class);
        //Then
        Assertions.assertNotNull(getOrdersResponseBody);
        Assertions.assertFalse(getOrdersResponseBody.getOrders().isEmpty());
    }
}
