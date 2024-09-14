package order;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static courier.BaseSpec.getBaseSpec;
import static io.restassured.RestAssured.given;

public class MakingOrder {
    private static final String ORDER_PATH = "/api/v1/orders";

    @Step
    public ValidatableResponse creatingOrder(Order order) {
        return given().log().ifValidationFails()
                .spec(getBaseSpec())
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();

    }

    @Step
    public ValidatableResponse getOrderList(){
        return given()
                .spec(getBaseSpec())
                .get(ORDER_PATH)
                .then();
    }

}
