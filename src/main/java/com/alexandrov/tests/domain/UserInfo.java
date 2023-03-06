package com.alexandrov.tests.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInfo {

    private Date loginDate;
    private String username;

    @Override
    public String toString() {
        return "UserInfo{" +
                "loginDate=" + loginDate +
                ", username='" + username + '\'' +
                '}';
    }
}