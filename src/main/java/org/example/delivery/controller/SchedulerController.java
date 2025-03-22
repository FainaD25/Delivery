package org.example.delivery.controller;

import org.example.delivery.component.WeatherDataFetcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    private final WeatherDataFetcher weatherDataFetcher;

    public SchedulerController(WeatherDataFetcher weatherDataFetcher) {
        this.weatherDataFetcher = weatherDataFetcher;
    }

    @GetMapping("/run")
    public String triggerTask() {
        weatherDataFetcher.fetchWeatherData();
        return "Scheduled task executed manually!";
    }
}
