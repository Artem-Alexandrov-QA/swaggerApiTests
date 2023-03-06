package com.alexandrov.tests;

import com.alexandrov.tests.entity.City;
import com.alexandrov.tests.util.Data;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.with;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;


@SpringBootTest
public class TicketsControllerTest {

    private static final String BASEURI = "http://localhost:8080";

    private RequestSpecification spec = with()
            .baseUri(BASEURI)
            .basePath("/")
            .contentType(ContentType.JSON)
            .log().all();

    @Tag("ApiTest")
    @Test
    void ticketsControllerGetAllTest() {

        spec.get("tickets/getAll")
                .then()
                .statusCode(200)
                .body("RouteOne.size", is(1),
                        "RouteTwo.size", is(1),
                        "RouteThree.size", is(1),
                        "RouteOne.price[0]", is("3000"),
                        "RouteTwo.price[0]", is("1500"),
                        "RouteThree.price[0]", is("999"));
        String response = spec.get("tickets/getAll")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
    }

    @Tag("ApiTest")
    @Test
    void ticketsControllerDeparturesTest() {
        City[][] cityInfosDep = spec.get("tickets/getDepartures")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(City[][].class);

        Assertions.assertEquals(3, cityInfosDep.length, "Size of array is wrong");

        List<String> list1 = Stream.of(cityInfosDep)
                .flatMap(Arrays::stream)
                .filter(cityInfo -> cityInfo.getRailwayName()
                        .equals("railwayKazan") && cityInfo.getCityName().equals("Kazan"))
                .peek(cityInfo -> System.out.println(cityInfo.getRailwayName()))
                .peek(cityInfo -> System.out.println(cityInfo.getCityName()))
                .map(cityInfo -> toString())
                .collect(Collectors.toList());

        List<String> list2 = Stream.of(cityInfosDep)
                .flatMap(Arrays::stream)
                .filter(cityInfo -> cityInfo.getRailwayName()
                        .equals("railwayVolgograd") && cityInfo.getCityName().equals("Volgograd"))
                .peek(cityInfo -> System.out.println(cityInfo.getRailwayName()))
                .peek(cityInfo -> System.out.println(cityInfo.getCityName()))
                .map(cityInfo -> toString())
                .collect(Collectors.toList());

        assertThat(list1).hasSize(3);
        assertThat(list2).hasSize(3);
    }

    @Tag("ApiTest")
    @Test
    void ticketsControllerArrivalsTest() {
        City[][] cityInfosArr = spec.get("tickets/getRailway")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(City[][].class);

        spec.get("tickets/getRailway")
                .then()
                .statusCode(200)
                .body(
                        "[0][0].city_name", is("Samara"),
                        "[0][0].railway_station", is("railwaySamara"),
                        "[0][0].date", is(1674590400000L),
                        "[0][1].city_name", is("Moscow"),
                        "[0][1].railway_station", is("kurskyRailwayStation"),
                        "[0][1].date", is(1674590400000L)
                );

        System.out.println(Arrays.stream(cityInfosArr).findFirst());

        Assertions.assertEquals(3, cityInfosArr.length, "Size of array is wrong");

        System.out.println(Arrays.deepToString(cityInfosArr));
    }

    @Tag("ApiTest")
    @Test
    void addNewTicket() {
        Data data = new Data();
        JSONObject body = data.getJsonObject();
        String response = spec
                .body(body.toString())
                .post("tickets/addTicket")
                .then().statusCode(200)
                .extract()
                .asString();
        System.out.println(response);
        spec
                .body(body.toString())
                .post("tickets/addTicket")
                .then().statusCode(200)
                .body("RouteFourth.size", is(1),
                        "RouteFourth.price[0]", is("1000"),
                        "RouteFourth.departure.size", is(1),
                        "RouteFourth.arrival.size", is(1),
                        "RouteFourth.counts[0]", is(2),
                        "RouteFourth.departure[0].city_name[0]", is("Test1"),
                        "RouteFourth.arrival[0].city_name[0]", is("Test2"));
    }

    @Tag("ApiTest")
    @Test
    void addEmptyBody() {
        spec
                .post("tickets/addTicket")
                .then()
                .statusCode(400)
                .body("error", is("Bad Request"));
    }

    @Tag("ApiTest")
    @Test
    void getTicketsByFilteredPriceTest() {
        int minPrice = 400;
        int maxPrice = 3000;

        String response = spec
                .queryParam("minPrice", minPrice)
                .queryParam("maxPrice", maxPrice)
                .get("tickets/filterPrice")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        System.out.println(response);

        spec
                .queryParam("minPrice", minPrice)
                .queryParam("maxPrice", maxPrice)
                .get("tickets/filterPrice").then()
                .statusCode(200)
                .body("size", is(3),
                        "[0].price", is("1500"),
                        "[1].price", is("3000"));
    }
}