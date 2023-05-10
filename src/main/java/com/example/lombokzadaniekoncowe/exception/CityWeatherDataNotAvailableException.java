package com.example.lombokzadaniekoncowe.exception;

public class CityWeatherDataNotAvailableException extends RuntimeException {
    public CityWeatherDataNotAvailableException() {
        super("Nie udalo sie pobrac danych pogodowych dla tego miasta");
    }
}
