package com.example.lombokzadaniekoncowe.model;

import lombok.Data;
import java.util.Map;

@Data
public class WeatherResponseDto {
    private Map<String, Double> main;
}