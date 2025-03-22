package org.example.delivery.service;

import lombok.RequiredArgsConstructor;
import org.example.delivery.entity.BaseFeeEntity;
import org.example.delivery.entity.WeatherEntity;
import org.example.delivery.repository.BaseFeeRepository;
import org.example.delivery.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final WeatherRepository weatherRepository;
    private final BaseFeeRepository baseFeeRepository;

    private final Map<String, String> cityStationNames = Map.of(
            "Tallinn", "Tallinn-Harku",
            "Tartu", "Tartu-Tõravere",
            "Pärnu", "Pärnu"
    );

    public double calculateAirTemperatureFee(WeatherEntity entity) {
        if (entity.getAirTemperature() < -10) {
            return 1.0;
        } else if (entity.getAirTemperature() < 0) {
            return 0.5;
        }
        return 0.0;
    }

    public double calculateWindSpeedFee(WeatherEntity entity) {
        if (entity.getWindSpeed() > 20) {
            throw new IllegalArgumentException("Usage of bike is forbidden due to high wind speed.");
        } else if (entity.getWindSpeed() >= 10) {
            return  0.5;
        }
        return 0.0;
    }

    public double calculateWeatherPhenomenonFee(WeatherEntity entity) {
        String phenomenon = entity.getWeatherPhenomenon().toLowerCase();
        if (phenomenon.contains("snow") || phenomenon.contains("sleet")) {
            return 1.0;
        } else if (phenomenon.contains("rain")) {
            return  0.5;
        } else if (phenomenon.contains("glaze") || phenomenon.contains("hail") || phenomenon.contains("thunder")) {
            throw new IllegalArgumentException("Usage of selected vehicle type is forbidden due to extreme weather.");
        }
        return 0.0;
    }

    public double calculateExtraFee(WeatherEntity entity, String vehicleType) {
        double fee = 0.0;

        if (!vehicleType.equalsIgnoreCase("CAR")) {
            fee += calculateAirTemperatureFee(entity) + calculateWeatherPhenomenonFee(entity);
            if (vehicleType.equalsIgnoreCase("BIKE")) {
                fee += calculateWindSpeedFee(entity);
            }
        }

        return fee;
    }

    public double calculateTotalFee(String vehicleType, String city) {
        String stationName = cityStationNames.get(city);
        if (stationName == null) {
            throw new IllegalArgumentException("Invalid city provided.");
        }

        WeatherEntity latestWeather = weatherRepository.findLatestByStationName(stationName);
        if (latestWeather == null) {
            throw new IllegalStateException("No weather data available for " + city);
        }

        BaseFeeEntity baseFeeEntity = baseFeeRepository.findByCityAndVehicleType(city, vehicleType)
                .orElseThrow(() -> new IllegalArgumentException("No base fee found for city and vehicle type."));
        double baseFee = baseFeeEntity.getBaseFee();
        double extraFee = calculateExtraFee(latestWeather, vehicleType);
        return baseFee + extraFee;
    }
}
