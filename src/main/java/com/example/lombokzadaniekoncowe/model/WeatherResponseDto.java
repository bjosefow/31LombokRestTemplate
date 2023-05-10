package com.example.lombokzadaniekoncowe.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Data
public class WeatherResponseDto {
    private Map<String, Double> main;
}
