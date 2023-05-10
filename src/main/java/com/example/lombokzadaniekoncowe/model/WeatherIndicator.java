package com.example.lombokzadaniekoncowe.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherIndicator {
    private String name;
    private double value;

}
