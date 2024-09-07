import courier.Courier;
import courier.courierClient;
import courier.courierCredentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class TestLogin {
    int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @After
    public void deleteCourier() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Login with all creds")

    public void login() {
        Courier courier = new Courier("anna25", "123", "annett");
        ValidatableResponse createCourier = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(courierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
        assertNotNull(courierId);
        int code = loginResponse.extract().statusCode();
        assertEquals(code, 200);
    }

    @Test
    @DisplayName("Login without login")

    public void loginWithoutParam(){
        Courier courier = new Courier("anna24", "123", "annett");
        ValidatableResponse createCourier1 = courierClient.create(courier);
        String courierPass = courier.getPassword();
        ValidatableResponse loginResponse = courierClient.login(new courierCredentials(null, courierPass));
        int code2 = loginResponse.extract().statusCode();
        assertEquals(400, code2);
        String bodyResponse = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", bodyResponse);
    }

    @Test
    @DisplayName("Login without password")

    public void loginWithoutPass(){
        Courier courier = new Courier("anna24", "123", "annett");
        ValidatableResponse createCourier1 = courierClient.create(courier);
        String courierLogin = courier.getLogin();
        ValidatableResponse loginResponse = courierClient.login(new courierCredentials(courierLogin, null));
        int code2 = loginResponse.extract().statusCode();
        assertEquals(400, code2);
        String bodyResponse = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", bodyResponse);
    }

    @Test
    @DisplayName("Login without all creds")

    public void loginWithoutPassAndLog(){
        Courier courier = new Courier("anna24", "123", "annett");
        ValidatableResponse createCourier1 = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(new courierCredentials(null, null));
        int code2 = loginResponse.extract().statusCode();
        assertEquals(400, code2);
        String bodyResponse = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", bodyResponse);
    }

    @Test
    @DisplayName("Login with login not exist")

    public void loginWithLoginNotExist(){
        Courier courier = new Courier("anna25", "123", "annett");
        ValidatableResponse createCourier = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(courierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
        courierClient.delete(courierId);
        ValidatableResponse loginResponse1 = courierClient.login(courierCredentials.from(courier));
        int code2 = loginResponse1.extract().statusCode();
        assertEquals(404, code2);
        String bodyResponse = loginResponse1.extract().path("message");
        assertEquals("Учетная запись не найдена", bodyResponse);
    }

}
