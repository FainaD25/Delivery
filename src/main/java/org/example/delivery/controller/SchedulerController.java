package org.example.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.delivery.component.WeatherDataFetcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
@Tag(name = "Scheduler", description = "Operations for running scheduled tasks manually")
public class SchedulerController {

    private final WeatherDataFetcher weatherDataFetcher;

    public SchedulerController(WeatherDataFetcher weatherDataFetcher) {
        this.weatherDataFetcher = weatherDataFetcher;
    }

    @GetMapping("/run")
    @Operation(summary = "Trigger task", description = "Triggers a task for fetching weather info")
    @ApiResponse(responseCode = "200", description = "Successful execution")
    public String triggerTask() {
        weatherDataFetcher.fetchWeatherData();
        return "Scheduled task executed manually!";
    }
}
