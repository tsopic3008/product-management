package com.tscore.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ProductResourceIT {

    @Test
    void getAll_returnsSeededProducts() {
        given()
                .when().get("/products")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(3))
                .body("name", hasItems("Laptop Pro", "Wireless Mouse", "Clean Code"));
    }

    @Test
    void getAll_withCategoryFilter_returnsFilteredProducts() {
        given()
                .queryParam("categoryId", 1)
                .when().get("/products")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(2))
                .body("name", hasItems("Laptop Pro", "Wireless Mouse"))
                .body("categoryName", everyItem(equalTo("Electronics")));
    }

    @Test
    void getAll_withUnknownCategoryId_returnsEmptyList() {
        given()
                .queryParam("categoryId", 9999)
                .when().get("/products")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(0));
    }

    @Test
    void getById_existingId_returnsProduct() {
        given()
                .when().get("/products/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Laptop Pro"))
                .body("price", equalTo(1499.99f))
                .body("categoryName", equalTo("Electronics"))
                .body("imageUrl", notNullValue());
    }

    @Test
    void getById_nonExistingId_returns404() {
        given()
                .when().get("/products/9999")
                .then()
                .statusCode(404)
                .body("message", containsString("9999"));
    }

    @Test
    void getAll_responseContainsMappedFields() {
        given()
                .when().get("/products")
                .then()
                .statusCode(200)
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].price", notNullValue());
    }
}
