package com.example.lombokzadaniekoncowe;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("cityName", "");
        return "home";
    }

    @GetMapping("/weather-for-city")
    public String showWeatherForCity(@RequestParam String cityName, Model model) {
        model.addAttribute("indicators", weatherService.getWeatherIndicators(cityName));
        model.addAttribute("cityName", cityName);
        return "currentWeather";
    }
}
