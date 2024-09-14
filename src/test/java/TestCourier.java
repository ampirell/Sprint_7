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
import static junit.framework.TestCase.assertTrue;

public class TestCourier {
    private Integer courierId;
    Courier courier = new Courier("anna18", "pass1", "annett");

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseURI.BASE_URI;
    }
    @After
    public void deleteCourier() {
        ValidatableResponse loginResponse = CourierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
        if (courierId != null){
           CourierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Create courier")

    public void createCourier() {
        //Создаем курьера
        ValidatableResponse createResponse = CourierClient.create(courier);
        int code = createResponse.extract().statusCode();
        assertEquals(201, code);
        boolean param = createResponse.extract().path("ok");
        assertTrue(param);
        //входим под логином
        ValidatableResponse loginResponse = CourierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
    }


    @Test
    @DisplayName("Create courier with the same parameters")

    public void createCourierWithSameParams(){
        ValidatableResponse createResponse = CourierClient.create(courier);
        int code = createResponse.extract().statusCode();
        assertEquals(201, code);
        ValidatableResponse createResponse2 = CourierClient.create(courier);
        int code2 = createResponse2.extract().statusCode();
        assertEquals(409, code2);
        String bodyResponse = createResponse2.extract().path("message");
        assertEquals("Этот логин уже используется", bodyResponse);
        ValidatableResponse loginResponse = CourierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
    }


    @Test
    @DisplayName("Create courier with same login")

    public void createCourierWithSameLogin() {
        ValidatableResponse createResponse = CourierClient.create(courier);
        int code = createResponse.extract().statusCode();
        assertEquals(201, code);

        Courier courier2 = new Courier("anna18", "pass2", "mary");
        ValidatableResponse createResponse2 = CourierClient.create(courier);
        int code2 = createResponse2.extract().statusCode();
        assertEquals(409, code2);

        String bodyResponse = createResponse2.extract().path("message");
        assertEquals("Этот логин уже используется", bodyResponse);
        ValidatableResponse loginResponse = CourierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
    }


    @Test
    @DisplayName("Create courier without password")

    public void createCourierWithoutPass(){

        Courier courier = new Courier("anna18", null, "annett");
        ValidatableResponse createResponse3 = CourierClient.create(courier);
        int code2 = createResponse3.extract().statusCode();
        assertEquals(400, code2);

        String bodyResponse = createResponse3.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", bodyResponse);
    }


    @Test
    @DisplayName("Create courier without login")

    public void createCourierWithoutLogin(){
        Courier courier = new Courier(null, "pass1", "annett");
        ValidatableResponse createResponse3 = CourierClient.create(courier);
        int code2 = createResponse3.extract().statusCode();
        assertEquals(400, code2);

        String bodyResponse = createResponse3.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", bodyResponse);
    }


    @Test
    @DisplayName("Create courier without name")

    public void createCourierWithoutName(){
        Courier courier = new Courier("anna18", "pass1", null);
        ValidatableResponse createResponse3 = CourierClient.create(courier);
        int code2 = createResponse3.extract().statusCode();
        assertEquals(400, code2);

        String bodyResponse = createResponse3.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", bodyResponse);
    }

}


