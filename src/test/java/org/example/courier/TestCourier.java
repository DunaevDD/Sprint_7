package org.example.courier;

import io.restassured.response.ValidatableResponse;
import org.example.courier.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCourier {

    private Courier randomCourier;
    private CourierClient courierClient;
    private CourierAssertions check;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        check = new CourierAssertions();
        randomCourier = CourierGenerator.random();
    }

    // Тесты "создание курьера"
    @Test
    public void checkCreateCourier() {
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        check.createdSuccessfully(createResponse);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
    }

    @Test
    public void checkImpossibleCreateSameCourier() {
        courierClient.create(randomCourier);
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        check.creationConflicted(createResponse);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
    }

    @Test
    public void checkImpossibleCreateCourierWithoutLogin() {
        randomCourier.setLogin(null);
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        check.creationFailed(createResponse);
    }

    @Test
    public void checkImpossibleCreateCourierWithoutPassword() {
        randomCourier.setPassword(null);
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        check.creationFailed(createResponse);
    }

    @Test
    public void checkImpossibleCreateCourierWithoutLastName() {
        randomCourier.setLastName(null);
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        check.creationFailed(createResponse);
    }

    @Test
    public void checkImpossibleCreateCourierWithBusyLogin() {
        randomCourier.setLogin("SameLogin");
        courierClient.create(randomCourier);
        Courier secondCourier = CourierGenerator.random();
        secondCourier.setLogin("SameLogin");
        ValidatableResponse createResponse = courierClient.create(secondCourier);
        check.creationConflicted(createResponse);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
    }


    //Тесты  "логин курьера"
    @Test
    public void checkCourierLogin() {
        courierClient.create(randomCourier);
        CourierLogin credentials = CourierLogin.from(randomCourier);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.authorizationSuccess(loginResponse);
        courierId = loginResponse.extract().path("id");
    }

    @Test
    public void checkAuthorizationWithoutLogin() {
        courierClient.create(randomCourier);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
        CourierLogin credentials = CourierLogin.from(randomCourier);
        credentials.setLogin(null);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.authorizationWithoutRequiredField(loginResponse);
    }

    @Test
    public void checkAuthorizationWithoutPassword() {
        courierClient.create(randomCourier);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
        CourierLogin credentials = CourierLogin.from(randomCourier);
        credentials.setPassword(null);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.authorizationWithoutRequiredField(loginResponse);
    }

    @Test
    public void checkAuthorizationWithInvalidLogin() {
        courierClient.create(randomCourier);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
        CourierLogin credentials = CourierLogin.from(randomCourier);
        credentials.setLogin("InvalidLogin");
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.authorizationWithInvalidField(loginResponse);
    }

    @Test
    public void checkAuthorizationWithInvalidPassword() {
        courierClient.create(randomCourier);
        courierId = courierClient.login(CourierLogin.from(randomCourier)).extract().path("id");
        CourierLogin credentials = CourierLogin.from(randomCourier);
        credentials.setPassword("InvalidPassword");
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.authorizationWithInvalidField(loginResponse);
    }

    @Test
    public void checkAuthorizationNonexistentCourier() {
        CourierLogin credentials = CourierLogin.from(randomCourier);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.authorizationWithInvalidField(loginResponse);
    }

    @After
    public void cleanUp() {
        if (courierId > 0) {
            courierClient.delete(courierId);
        }
    }
}