import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import order.MakingOrder;
import courier.BaseURI;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestGetOrdersList {

    private MakingOrder makingOrder = new MakingOrder();

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseURI.BASE_URI;
    }

    @Test
    @DisplayName("Check to return list of orders")

    public void getOrdersList() {
        ValidatableResponse createResponse = makingOrder.getOrderList()
                .assertThat().statusCode(200).
                body(JsonSchemaValidator.matchesJsonSchemaInClasspath("JSONSchema.json"));


//"orders.id[0]", notNullValue()
//        int code = createResponse.extract().statusCode();
//        assertEquals(code, 200);
    }

}
