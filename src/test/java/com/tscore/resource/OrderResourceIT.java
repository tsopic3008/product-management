package com.tscore.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class OrderResourceIT {

    @Test
    void placeOrder_validRequest_returns201WithOrderResponse() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "userId": "user-test-1",
                          "items": [
                            {"productId": 1, "quantity": 2},
                            {"productId": 3, "quantity": 1}
                          ]
                        }
                        """)
                .when().post("/orders")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("userId", equalTo("user-test-1"))
                .body("createdAt", notNullValue())
                .body("items.size()", equalTo(2))
                .body("items[0].productId", equalTo(1))
                .body("items[0].quantity", equalTo(2))
                .body("items[1].productId", equalTo(3))
                .body("items[1].quantity", equalTo(1));
    }

    @Test
    void placeOrder_missingUserId_returns400() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "items": [{"productId": 1, "quantity": 1}]
                        }
                        """)
                .when().post("/orders")
                .then()
                .statusCode(400);
    }

    @Test
    void placeOrder_emptyItems_returns400() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "userId": "user-test",
                          "items": []
                        }
                        """)
                .when().post("/orders")
                .then()
                .statusCode(400);
    }

    @Test
    void placeOrder_nonExistingProduct_returns404() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "userId": "user-test",
                          "items": [{"productId": 9999, "quantity": 1}]
                        }
                        """)
                .when().post("/orders")
                .then()
                .statusCode(404)
                .body("message", containsString("9999"));
    }

    @Test
    void placeOrder_quantityZero_returns400() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "userId": "user-test",
                          "items": [{"productId": 1, "quantity": 0}]
                        }
                        """)
                .when().post("/orders")
                .then()
                .statusCode(400);
    }

    @Test
    void getById_existingOrder_returnsOrderResponse() {
        var orderId = given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "userId": "user-findbyid",
                          "items": [{"productId": 2, "quantity": 3}]
                        }
                        """)
                .when().post("/orders")
                .then()
                .statusCode(201)
                .extract().path("id");

        given()
                .when().get("/orders/" + orderId)
                .then()
                .statusCode(200)
                .body("id", equalTo(orderId))
                .body("userId", equalTo("user-findbyid"))
                .body("items[0].productName", equalTo("Wireless Mouse"))
                .body("items[0].quantity", equalTo(3));
    }

    @Test
    void getById_nonExistingId_returns404() {
        given()
                .when().get("/orders/9999")
                .then()
                .statusCode(404)
                .body("message", containsString("9999"));
    }

    @Test
    void getByUserId_returnsOrdersForUser() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "userId": "user-unique-42",
                          "items": [{"productId": 1, "quantity": 1}]
                        }
                        """)
                .when().post("/orders")
                .then()
                .statusCode(201);

        given()
                .queryParam("userId", "user-unique-42")
                .when().get("/orders")
                .then()
                .statusCode(200)
                .body("$.size()", greaterThanOrEqualTo(1))
                .body("userId", everyItem(equalTo("user-unique-42")));
    }

    @Test
    void getByUserId_missingUserId_returns400() {
        given()
                .when().get("/orders")
                .then()
                .statusCode(400);
    }

    @Test
    void getAll_returnsAllOrders() {
        given()
                .when().get("/orders/all")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}
