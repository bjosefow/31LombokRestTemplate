package com.example.lombokzadaniekoncowe.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoCoordinates {
    private double lat;
    private double lon;
}
