package com.alexandrov.tests;

import com.alexandrov.tests.domain.UserInfo;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.with;

public class BankControllerTest {

    private static final String BASEURI = "http://localhost:8080";

    private RequestSpecification spec = with()
            .baseUri(BASEURI)
            .basePath("/");

    @Test
    void bankControllerTest() {
        UserInfo[] userInfos = spec.get("user/getAll")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(UserInfo[].class);
        System.out.println(Arrays.toString(userInfos));


        Stream.of(userInfos)
                .filter(userInfo -> userInfo.getUsername().equals("Pasha"))
                .peek(userInfo -> System.out.println(userInfo.getUsername()))
                .map(userInfo -> userInfo.toString())
                .collect(Collectors.toList());
    }
}