package courier;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
public class courierClient {

    private static final String COURIER_PATH = "/api/v1/courier";

    @Step
    public static ValidatableResponse create(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step
    public static ValidatableResponse login(courierCredentials credentials) {
        return given()
                .header("Content-type", "application/json")
                .body(credentials)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    @Step
    public static ValidatableResponse delete(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/{courierId}", courierId)
                .then();
    }
}
