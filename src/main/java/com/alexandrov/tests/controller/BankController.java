package com.alexandrov.tests.controller;

import com.alexandrov.tests.domain.LoginInfo;
import com.alexandrov.tests.domain.UserInfo;
import com.alexandrov.tests.exception.InvalidUsernameException;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class BankController {

    private Map<String, UserInfo> users = Map.of(
            "Artem", UserInfo.builder().username("Artem").build(),
            "Alexander", UserInfo.builder().username("Alexander").build(),
            "Mihail", UserInfo.builder().username("Mihail").build()
    );

    @PostMapping("/user/login")
    @ApiOperation("Authorization")
    public UserInfo doLogin(@RequestBody LoginInfo loginInfo) {
        if (loginInfo.getUserName().equals("Artem")) {
            return UserInfo.builder()
                    .loginDate(new Date())
                    .username(loginInfo.getUserName())
                    .build();
        } else {
            throw new InvalidUsernameException();
        }
    }

    @GetMapping("user/getAll")
    @ApiOperation("Get all users")
    public List<UserInfo> getAllUsersInfo() {
        return users.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}