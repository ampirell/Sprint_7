package order;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class makingOrder {
    private static final String ORDER_PATH = "/api/v1/orders";

    @Step
    public ValidatableResponse creatingOrder(String firstName, String lastName, String address, Integer metroStation,
                                             String phone, Integer rentTime, String deliveryDate, String comment, String[] color) {
        return given().log().ifValidationFails()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"firstName\": \"" + firstName + "\",\n" +
                        "    \"lastName\": \"" + lastName + "\",\n" +
                        "    \"address\": \"" + address + "\",\n" +
                        "    \"metroStation\": \"" + metroStation + "\",\n" +
                        "    \"phone\": \"" + phone + "\",\n" +
                        "    \"rentTime\": \"" + rentTime + "\",\n" +
                        "    \"deliveryDate\": \"" + deliveryDate + "\",\n" +
                        "    \"comment\": \"" + comment + "\",\n" +
                        "    \"color\": [\"" + String.join(",", color) + "\"]\n" +
                        "}")
                .when()
                .post(ORDER_PATH)
                .then();

    }

    @Step
    public ValidatableResponse getOrderList(){
        return given()
                .get(ORDER_PATH)
                .then();
    }

}
