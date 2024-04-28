package com.erudio.controller;

import com.erudio.configuration.TestConfig;
import com.erudio.integration.AbstractIntegrationTest;
import com.erudio.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
class PersonControllerIntegrationTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper mapper;
    private static Person person;

    @BeforeAll
    public static void setup() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
                .setBasePath("/person")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .build();

        person = new Person(1L, "Filipe",
                "Bezerra",
                "bezerra@gmail.com",
                "Sao Paulo",
                "male");
    }

    @Test
    @Order(1)
    public void shouldCreate() throws JsonProcessingException {
        var content = given().spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person createdPerson = mapper.readValue(content, Person.class);
        person = createdPerson;

        assertEquals("Filipe", createdPerson.getFirstName());
        assertEquals("Bezerra", createdPerson.getLastName());
        assertEquals("bezerra@gmail.com", createdPerson.getEmail());
        assertEquals("Sao Paulo", createdPerson.getAddress());
        assertEquals("male", createdPerson.getSex());
    }

    @Test
    @Order(2)
    public void shouldUpdate() throws JsonProcessingException {
        person.setFirstName("Alda");
        person.setEmail("alda@gmail.com");
        person.setSex("female");

        var content = given().spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person updatedPerson = mapper.readValue(content, Person.class);
        person = updatedPerson;

        assertEquals("Alda", updatedPerson.getFirstName());
        assertEquals("alda@gmail.com", updatedPerson.getEmail());
        assertEquals("female", updatedPerson.getSex());
    }

    @Test
    @Order(3)
    public void shouldFindById() throws JsonProcessingException {
        var content = given().spec(specification)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person searchedPerson = mapper.readValue(content, Person.class);

        assertEquals("Alda", searchedPerson.getFirstName());
        assertEquals("alda@gmail.com", searchedPerson.getEmail());
        assertEquals("female", searchedPerson.getSex());
    }

    @Test
    @Order(4)
    public void shouldFindAll() throws JsonProcessingException {
        Person otherPerson = new Person(3L, "Nicolas",
                "Abreu",
                "nick@gmail.com",
                "Salvador",
                "male");

        given()
                .spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(otherPerson)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();


        var content = given()
                .spec(specification)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        List<Person> people = List.of(mapper.readValue(content, Person[].class));

        Person alda = people.get(0);
        Person nicolas = people.get(1);

        assertTrue(people.size() == 2);
        assertEquals("Alda", alda.getFirstName());
        assertEquals("alda@gmail.com", alda.getEmail());
        assertEquals("Nicolas", nicolas.getFirstName());
        assertEquals("nick@gmail.com", nicolas.getEmail());
    }

    @Test
    @Order(5)
    public void shouldDelete() {
        given().spec(specification)
                .pathParam("id", person.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }
}