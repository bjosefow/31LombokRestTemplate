package com.example.lombokzadaniekoncowe.exception;

public class CityGeoDataNotAvailableException extends RuntimeException {
    public CityGeoDataNotAvailableException() {
        super("Nie udalo sie pobrac danych lokalizacji dla tego miasta");
    }
}
