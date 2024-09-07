import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import order.makingOrder;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestGetOrdersList {

    private order.makingOrder makingOrder = new makingOrder();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check to return list of orders")

    public void getOrdersList() {
        ValidatableResponse createResponse = makingOrder.getOrderList().assertThat().body("orders", notNullValue());
        int code = createResponse.extract().statusCode();
        assertEquals(code, 200);
    }

}
