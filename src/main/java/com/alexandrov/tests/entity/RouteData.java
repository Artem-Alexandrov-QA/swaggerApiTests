package com.alexandrov.tests.entity;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteData {

    private String route;

    @Override
    public String toString() {
        return route;
    }
}