package com.tscore.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryResourceIT {

    @Test
    @Order(1)
    void getAll_returnsSeededCategories() {
        given()
                .when().get("/categories")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", greaterThanOrEqualTo(2))
                .body("name", hasItems("Electronics", "Books"));
    }

    @Test
    @Order(2)
    void getById_existingId_returnsCategory() {
        given()
                .when().get("/categories/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Electronics"));
    }

    @Test
    @Order(3)
    void getById_nonExistingId_returns404() {
        given()
                .when().get("/categories/9999")
                .then()
                .statusCode(404)
                .body("message", containsString("9999"));
    }

    @Test
    @Order(4)
    void create_validCategory_returns201WithBody() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {"name": "Sports"}
                        """)
                .when().post("/categories")
                .then()
                .statusCode(201)
                .body("name", equalTo("Sports"))
                .body("id", notNullValue());
    }

    @Test
    @Order(5)
    void create_blankName_returns400() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {"name": ""}
                        """)
                .when().post("/categories")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(6)
    void update_existingId_returnsUpdatedCategory() {
        var id = given()
                .contentType(ContentType.JSON)
                .body("""
                        {"name": "To Be Updated"}
                        """)
                .when().post("/categories")
                .then()
                .statusCode(201)
                .extract().path("id");

        given()
                .contentType(ContentType.JSON)
                .body("""
                        {"name": "Updated Name"}
                        """)
                .when().put("/categories/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Name"));
    }

    @Test
    @Order(7)
    void update_nonExistingId_returns404() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {"name": "Ghost"}
                        """)
                .when().put("/categories/9999")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(8)
    void delete_existingId_returns204() {
        var id = given()
                .contentType(ContentType.JSON)
                .body("""
                        {"name": "Temporary"}
                        """)
                .when().post("/categories")
                .then()
                .statusCode(201)
                .extract().path("id");

        given()
                .when().delete("/categories/" + id)
                .then()
                .statusCode(204);

        given()
                .when().get("/categories/" + id)
                .then()
                .statusCode(404);
    }

    @Test
    @Order(9)
    void delete_nonExistingId_returns404() {
        given()
                .when().delete("/categories/9999")
                .then()
                .statusCode(404);
    }
}
