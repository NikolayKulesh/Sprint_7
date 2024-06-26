import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.OrderClient;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check the response code returns a list of orders")
    @Description("Test for API method GET /api/v1/orders")
    public void getOrdersList() {
        OrderClient orderClient = new OrderClient();

        Response response = orderClient.getOrders();

        response.then().assertThat().body("orders.id", notNullValue())
                .and().
                statusCode(200);
    }
}
