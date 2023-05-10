package com.example.lombokzadaniekoncowe;

import com.example.lombokzadaniekoncowe.exception.CityGeoDataNotAvailableException;
import com.example.lombokzadaniekoncowe.model.GeoCoordinates;
import com.example.lombokzadaniekoncowe.model.WeatherIndicators;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Service
public class WeatherService {

    private final String apiKey;
    private static final RestTemplate restTemplate = new RestTemplate();

    public WeatherService(@Value("${app.openweather.apikey}") String apiKey) {
        this.apiKey = apiKey;
    }

    public WeatherIndicators getWeatherIndicators(String city) {
        GeoCoordinates geoData = getGeoData(city);
        String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + geoData.getLat() + "&lon=" + geoData.getLon() + "&units=Metric&appid=" + apiKey;
        try {
            String response = restTemplate.getForObject(weatherUrl, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response).get("main");
            return WeatherIndicators.builder()
                    .temp(jsonNode.findValue("temp").asDouble())
                    .feelsLike(jsonNode.findValue("feels_like").asDouble())
                    .tempMin(jsonNode.findValue("temp_min").asDouble())
                    .tempMax(jsonNode.findValue("temp_max").asDouble())
                    .pressure(jsonNode.findValue("pressure").asDouble())
                    .humidity(jsonNode.findValue("humidity").asDouble()).build();
        } catch (Exception e) {
            throw new CityGeoDataNotAvailableException();
        }
    }

    private GeoCoordinates getGeoData(String city) {
        try {
            String geoUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=5&appid=" + apiKey;
            String response = restTemplate.getForObject(geoUrl, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response).get(1);
            return new GeoCoordinates(jsonNode.findValue("lat").asDouble(), jsonNode.findValue("lon").asDouble());
        } catch (Exception e) {
            throw new CityGeoDataNotAvailableException();
        }
    }
}
