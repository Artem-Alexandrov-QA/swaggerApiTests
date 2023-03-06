package com.alexandrov.tests.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class City {

    @JsonProperty("city_name")
    private String cityName;
    @JsonProperty("railway_station")
    private String railwayName;
    private Date date;

    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                ", railwayName='" + railwayName + '\'' +
                ", date=" + date +
                '}';
    }
}