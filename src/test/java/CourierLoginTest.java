import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.CourierClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {

    private CourierClient courierClient;
    private String id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        courierClient = new CourierClient();

        Response response = courierClient.create("berzerk@gmail.com", "12345", "Berserk");
        Response loginResponse = courierClient.login("berzerk@gmail.com", "12345");
        id = loginResponse.then().extract().path("id").toString();
    }

    @Test
    @DisplayName("Check that the courier can log in and returns a successful request id")
    @Description("The response must contain an id and the response code must contain 200")
    public void courierCanLoginTest() {
        Response loginResponse = courierClient.login("berzerk@gmail.com", "12345");
        loginResponse.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Check that all required fields must be specified for authorization")
    @Description("We check the authorization without a password, the response code should be 400")
    public void requiredFieldsLoginTest() {
        Response loginResponse = courierClient.login("berzerk@gmail.com", "");
        loginResponse.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check that it is impossible to log in under a non-existent user")
    @Description("We pass the wrong username and password to the request body. Check the body and the response code")
    public void wrongLoginTest() {
        Response loginResponse = courierClient.login("berzzzzerk@gmail.com", "12346");
        loginResponse.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }


    @After
    public void tearDown() {
        courierClient.delete(id);
    }
}
