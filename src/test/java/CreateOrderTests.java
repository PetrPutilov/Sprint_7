import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CancelOrderRequest;
import model.CreateOrderRequest;
import model.CreateOrderResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class CreateOrderTests extends BaseTest{

    private static Stream<Arguments> provideColorLists() {
        return Stream.of(
                Arguments.of(List.of("BLACK")),
                Arguments.of(List.of("GREY")),
                Arguments.of(List.of("BLACK", "GREY")),
                Arguments.of(List.of())
        );
    }

    @ParameterizedTest
    @MethodSource("provideColorLists")
    @DisplayName("can create order")
    public void createOrder(List<String> colors){
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
                colors
                );
        //When
        Response createOrderResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(createRequest)
                .when()
                .post("/api/v1/orders");;
        //Then
        createOrderResponse.then().statusCode(201);

        //Clean
        CreateOrderResponse createOrderResponseBody = createOrderResponse.body().as(CreateOrderResponse.class);
        cancelOrder(new CancelOrderRequest(createOrderResponseBody.getTrack()));
    }
}
