import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class TestLoginCourier {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }


    @Test
    public void checkLoginCourierSuccess() {
        JLoginCourier json = new JLoginCourier("ДДД", "password1");
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(200);
    }

    @Test
    public void checkLoginCourierWithoutLogin() {
        JLoginCourier json = new JLoginCourier(null, "password1");
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(400);
    }
        @Test
        public void checkLoginCourierWithoutPassword() {
            JLoginCourier json = new JLoginCourier(null, "password1");
            given()
                    .header("Content-type", "application/json")
                    .body(json)
                    .when()
                    .post("/api/v1/courier/login")
                    .then().statusCode(400);

    }
    @Test
    public void checkLoginCourierWithIncorrectLogin() {
        JLoginCourier json = new JLoginCourier("ЕЕЕ", "password1");
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(404);

    }
    @Test
    public void checkLoginCourierWithIncorrectPassword() {
        JLoginCourier json = new JLoginCourier("ЕЕЕ", "password99");
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(404);

    }

}
