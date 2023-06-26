// импортируем RestAssured
// импортируем Before
// импортируем Test
// дополнительный статический импорт нужен, чтобы использовать given(), get() и then()


import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class TestCourier {


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
        @Test
        public void checkCreateNewCourierSuccess(){
            JCourier json = new JCourier("ДДД", "password1", "Дмитрий");
            //    Response response =
                    given()
                            .header("Content-type", "application/json")
                            //.auth().oauth2("подставь_сюда_свой_токен")
                            //.and()
                            .body(json)
                            .when()
                            .post("/api/v1/courier")
        //    response.then().assertThat().body("data._id", notNullValue())
          //          .and()
                            .then().statusCode(201);
        }

    @Test
    public void checkCreateNewCourierWithSameParameters(){
        JCourier json1 = new JCourier("ДДД1", "password3", "Дмитрий");
        JCourier json2 = new JCourier("ДДД1", "password3", "Дмитрий");
        //    Response response =
        given()
                .header("Content-type", "application/json")
                //.auth().oauth2("подставь_сюда_свой_токен")
                //.and()
                .body(json1)
                .when()
                .post("/api/v1/courier")
                //    response.then().assertThat().body("data._id", notNullValue())
                //          .and()
                .then().statusCode(201);

        given()
                .header("Content-type", "application/json")
                //.auth().oauth2("подставь_сюда_свой_токен")
                //.and()
                .body(json2)
                .when()
                .post("/api/v1/courier")
                //    response.then().assertThat().body("data._id", notNullValue())
                //          .and()
                .then().statusCode(409);
               //.and()
               // .when()
               // .post("/api/v1/courier");

    }
}

