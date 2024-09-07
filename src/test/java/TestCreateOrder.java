import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import order.makingOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;



@RunWith(Parameterized.class)
public class TestCreateOrder {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final Integer metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;
    private order.makingOrder makingOrder = new makingOrder();


    public TestCreateOrder(String firstName, String lastName, String address, Integer metroStation, String phone, Integer rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    @Parameterized.Parameters(name = "toThePost")
    public static Object[][] testData() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "shine",  new String[]{"GREY"}},
                {"Moon", "Shine", "pepe", 3, "+7 800 355 35 35", 4, "2024-09-03", "bright", new String[]{"BLACK"}},
                {"Sun", "Dark", "madrid", 2, "+7 800 355 35 35", 3, "2025-06-06", "like", new String[]{"BLACK", "GRAY"}},
                {"Mercuri", "Red", "sun sistenm", 1, "+7 800 355 35 35", 2, "2025-06-06", "a diamond", new String[]{"null"}},
        };
    }
    @Test
    @DisplayName("Create order")
    public void createOrder() {
        ValidatableResponse createResponse = makingOrder.creatingOrder(firstName, lastName, address, metroStation,
                phone, rentTime, deliveryDate, comment, color);
        int code = createResponse.extract().statusCode();
        assertEquals(code, 201);
        int param = createResponse.extract().path("track");
        assertNotNull(param);

    }
}