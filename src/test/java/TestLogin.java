import courier.BaseURI;
import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class TestLogin {
    Integer courierId;

    Courier courier = new Courier("anna2", "1234", "annett");

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseURI.BASE_URI;
    }

    @After
    public void deleteCourier() {
        if (courierId != null) {
            CourierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Login with all creds")

    public void login() {
        ValidatableResponse createCourier = CourierClient.create(courier);
        ValidatableResponse loginResponse = CourierClient.login(CourierCredentials.from(courier));
        int code = loginResponse.extract().statusCode();
        assertEquals(200, code);
        courierId = loginResponse.extract().path("id");
        assertNotNull(courierId);

    }

    @Test
    @DisplayName("Login without login")

    public void loginWithoutParam(){
        ValidatableResponse createCourier1 = CourierClient.create(courier);
        String courierPass = courier.getPassword();
        ValidatableResponse loginResponse = CourierClient.login(new CourierCredentials(null, courierPass));
        int code2 = loginResponse.extract().statusCode();
        assertEquals(400, code2);
        String bodyResponse = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", bodyResponse);
    }

    @Test
    @DisplayName("Login without password")

    public void loginWithoutPass(){
        ValidatableResponse createCourier1 = CourierClient.create(courier);
        String courierLogin = courier.getLogin();
        ValidatableResponse loginResponse = CourierClient.login(new CourierCredentials(courierLogin, null));
        int code2 = loginResponse.extract().statusCode();
        assertEquals(400, code2);
        String bodyResponse = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", bodyResponse);
    }

    @Test
    @DisplayName("Login without all creds")

    public void loginWithoutPassAndLog(){
        ValidatableResponse createCourier1 = CourierClient.create(courier);
        ValidatableResponse loginResponse = CourierClient.login(new CourierCredentials(null, null));
        int code2 = loginResponse.extract().statusCode();
        assertEquals(400, code2);
        String bodyResponse = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", bodyResponse);
    }

    @Test
    @DisplayName("Login with login not exist")

    public void loginWithLoginNotExist(){
        ValidatableResponse createCourier = CourierClient.create(courier);
        ValidatableResponse loginResponse = CourierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
        CourierClient.delete(courierId);
        ValidatableResponse loginResponse1 = CourierClient.login(CourierCredentials.from(courier));
        int code2 = loginResponse1.extract().statusCode();
        assertEquals(404, code2);
        String bodyResponse = loginResponse1.extract().path("message");
        assertEquals("Учетная запись не найдена", bodyResponse);
    }

}
