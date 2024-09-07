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
import static junit.framework.TestCase.assertTrue;

public class TestCourier {
    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    @After
    public void deleteCourier() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Create courier")

    public void createCourier() {
        //Создаем курьера
        Courier courier = new Courier("anna18", "pass1", "annett");
        ValidatableResponse createResponse = courierClient.create(courier);
        int code = createResponse.extract().statusCode();
        assertEquals(code, 201);
        boolean param = createResponse.extract().path("ok");
        assertTrue(param);
        //входим под логином
        ValidatableResponse loginResponse = courierClient.login(courierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
    }


    @Test
    @DisplayName("Create courier with the same parameters")

    public void createCourierWithSameParams(){
        Courier courier = new Courier("anna22", "pass1", "annett");
        ValidatableResponse createResponse = courierClient.create(courier);
        int code = createResponse.extract().statusCode();
        assertEquals(code, 201);
        ValidatableResponse createResponse2 = courierClient.create(courier);
        int code2 = createResponse2.extract().statusCode();
        assertEquals(code2, 409);
        String bodyResponse = createResponse2.extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", bodyResponse);
        ValidatableResponse loginResponse = courierClient.login(courierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
    }


    @Test
    @DisplayName("Create courier with same login")

    public void createCourierWithSameLogin() {
        Courier courier = new Courier("anna23", "pass1", "annett");
        ValidatableResponse createResponse = courierClient.create(courier);
        int code = createResponse.extract().statusCode();
        assertEquals(code, 201);
        Courier courier2 = new Courier("anna23", "pass2", "mary");
        ValidatableResponse createResponse2 = courierClient.create(courier);
        int code2 = createResponse2.extract().statusCode();
        assertEquals(code2, 409);
        String bodyResponse = createResponse2.extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", bodyResponse);
        ValidatableResponse loginResponse = courierClient.login(courierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
    }


    @Test
    @DisplayName("Create courier without password")

    public void createcourierWithoutPass(){
        Courier courier = new Courier("anna24", null, "annett");
        ValidatableResponse createResponse3 = courierClient.create(courier);
        int code2 = createResponse3.extract().statusCode();
        assertEquals(400, code2);
        String bodyResponse = createResponse3.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", bodyResponse);
    }


    @Test
    @DisplayName("Create courier without login")

    public void createcourierWithoutLogin(){
        Courier courier = new Courier(null, "pass1", "annett");
        ValidatableResponse createResponse3 = courierClient.create(courier);
        int code2 = createResponse3.extract().statusCode();
        assertEquals(400, code2);
        String bodyResponse = createResponse3.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", bodyResponse);
    }


    @Test
    @DisplayName("Create courier with same firstname")

    public void createcourierWithoutName(){ //бага
        Courier courier = new Courier("anna24", "pass1", null);
        ValidatableResponse createResponse3 = courierClient.create(courier);
        int code2 = createResponse3.extract().statusCode();
        assertEquals(400, code2);
        String bodyResponse = createResponse3.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", bodyResponse);
    }

}


