import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.CourierClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class CourierCreateTest {

    private CourierClient courierClient;
    private String id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check that the courier can be created and the request returns the correct response code")
    @Description("Test for API method POST /api/v1/courier and POST /api/v1/courier/login to check if a courier has been created")
    public void addCourierTest() {
        courierClient = new CourierClient();

        Response response = courierClient.create("todo_a@gmail.com", "12345", "Todo");

        Response loginResponse = courierClient.login("todo_a@gmail.com", "12345");
        id = loginResponse.then().extract().path("id").toString();

        assertEquals("Неверный статус код", 201, response.statusCode());
        assertEquals("Неверный статус код", 200, loginResponse.statusCode());
    }

    @Test
    @DisplayName("checks that a successful request returns ok: true")
    @Description("Test for API method POST /api/v1/courier")
    public void returnOkTrueTest() {
        courierClient = new CourierClient();

        Response response = courierClient.create("griffith@gmail.com", "12345", "Griffith");

        Response loginResponse = courierClient.login("griffith@gmail.com", "12345");
        id = loginResponse.then().extract().path("id").toString();

        response.then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Checks that it is impossible to create two identical couriers or users with the same login")
    @Description("Check that the body and the response code correspond to the swagger")
    public void identicalCouriersTest() {
        courierClient = new CourierClient();

        Response response = courierClient.create("guts_berserk@gmail.com", "12345", "Guts");

        Response loginResponse = courierClient.login("guts_berserk@gmail.com", "12345");
        id = loginResponse.then().extract().path("id").toString();

        Response doubleResponse = courierClient.create("guts_berserk@gmail.com", "12345", "Guts");
        doubleResponse.then().assertThat().body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(409);
    }

    @Test
    @DisplayName("Checks that all required fields must be passed to the handle, if not, then an error")
    @Description("Check that the body and the response code correspond to the swagger")
    public void requiredFieldsTest() {
        courierClient = new CourierClient();

        Response response = courierClient.create("", "12345", "Beherith");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

   @After
    public void tearDown() {
        if(id != null) {
          courierClient.delete(id);
        }
    }

}
