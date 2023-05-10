package com.example.lombokzadaniekoncowe.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(CityGeoDataNotAvailableException.class)
    public String handleGeoDataException() {
        return "nodata";
    }

    @ExceptionHandler(CityWeatherDataNotAvailableException.class)
    public String handleWeatherDataException() {
        return "nodata";
    }
}
