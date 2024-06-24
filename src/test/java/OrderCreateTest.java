import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.OrderCreate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] colour;

    public OrderCreateTest(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] colour) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.colour = colour;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Иван", "Иванов", "Суздальский 17", 2, "89998887766", 3, "2024-06-23", "ааааа", new String[]{"BLACK"}},
                {"Петр", "Петров", "Ленина 20", 3, "89998887765", 5, "2024-06-25", "ббб!", new String[]{"GREY"}},
                {"Федор", "Федоров", "Тайваньская 50", 4, "89998887764", 2, "2024-06-26", "ввв?", new String[]{"GREY", "BLACK"}},
                {"Василий", "Васильев", "Будапештская 48", 6, "89998887763", 1, "2024-06-27", "жжж7.", new String[]{}},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check the color and track field in the response body")
    @Description("Check that the response contains the track and the response code 201")
    public void addOrderTest() {
        OrderCreate orderCreate = new OrderCreate(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colour);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(orderCreate)
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);

    }
}
