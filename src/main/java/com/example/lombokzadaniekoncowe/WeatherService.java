package com.example.lombokzadaniekoncowe;

import com.example.lombokzadaniekoncowe.exception.CityGeoDataNotAvailableException;
import com.example.lombokzadaniekoncowe.model.GeoCoordinates;
import com.example.lombokzadaniekoncowe.model.WeatherIndicator;
import com.example.lombokzadaniekoncowe.model.WeatherResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    //https://openweathermap.org/current#geo
    public static final String API_KEY =  "15a7589e0c618768809de9771cef6e79";
    public static final RestTemplate restTemplate = new RestTemplate();

    public List<WeatherIndicator> getWeatherIndicators(String city) {
        GeoCoordinates geoData = getGeoData(city);
        String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + geoData.getLat() + "&lon=" + geoData.getLon() + "&units=Metric&appid=" + API_KEY;
        try {
            WeatherResponseDto response = restTemplate.getForObject(weatherUrl, WeatherResponseDto.class);
            Map<String, Double> main = response.getMain();
            return main.entrySet().stream().map(entry -> new WeatherIndicator(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        } catch (Exception e) {
            throw new CityGeoDataNotAvailableException();
        }
    }

    private GeoCoordinates getGeoData(String city) {
        try {
            String geoUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=5&appid=" + API_KEY;
            String response = restTemplate.getForObject(geoUrl, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response).get(1);
            return new GeoCoordinates(jsonNode.findValue("lat").asDouble(), jsonNode.findValue("lon").asDouble());
        } catch (Exception e) {
            throw new CityGeoDataNotAvailableException();
        }
    }
}
