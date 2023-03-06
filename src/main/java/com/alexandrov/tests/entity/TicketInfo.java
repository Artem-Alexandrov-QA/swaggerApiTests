package com.alexandrov.tests.entity;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketInfo {

    private String price;
    private List<City> departure;
    private List<City> arrival;
    private Integer counts;

    @Override
    public String toString() {
        return "TicketInfo{" +
                "price='" + price + '\'' +
                ", departure=" + departure +
                ", arrival=" + arrival +
                ", counts=" + counts +
                '}';
    }
}