package com.example.lombokzadaniekoncowe;

import com.example.lombokzadaniekoncowe.exception.CityGeoDataNotAvailableException;
import com.example.lombokzadaniekoncowe.model.GeoCoordinates;
import com.example.lombokzadaniekoncowe.model.WeatherIndicators;
import com.example.lombokzadaniekoncowe.model.WeatherResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

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
            WeatherResponseDto response = restTemplate.getForObject(weatherUrl, WeatherResponseDto.class);
            Map<String, Double> main = response.getMain();
            return WeatherIndicators.builder()
                    .temp(main.get("temp"))
                    .feelsLike(main.get("feels_like"))
                    .tempMin(main.get("temp_min"))
                    .tempMax(main.get("temp_max"))
                    .pressure(main.get("pressure"))
                    .humidity(main.get("humidity")).build();
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
