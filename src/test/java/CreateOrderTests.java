import io.qameta.allure.junit4.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTests extends BaseOrderTest{

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
        createRequest.setColor(colors);
        //When
        createOrderResponse = createOrder(createRequest);
        //Then
        createOrderResponse.then().statusCode(201)
                .and().body("track", notNullValue());
    }
}
