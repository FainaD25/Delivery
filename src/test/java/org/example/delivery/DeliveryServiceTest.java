package org.example.delivery;

import static org.junit.jupiter.api.Assertions.*;


import org.example.delivery.entity.BaseFeeEntity;
import org.example.delivery.entity.WeatherEntity;
import org.example.delivery.repository.BaseFeeRepository;
import org.example.delivery.repository.WeatherRepository;
import org.example.delivery.service.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Mock
    private WeatherRepository weatherRepository;
    @Mock
    private BaseFeeRepository baseFeeRepository;

    @InjectMocks
    private DeliveryService deliveryService;

    private BaseFeeEntity tallinnCarBaseFee;
    private BaseFeeEntity tallinnScooterBaseFee;
    private BaseFeeEntity tallinnBikeBaseFee;
    private BaseFeeEntity tartuCarBaseFee;
    private BaseFeeEntity tartuScooterBaseFee;
    private BaseFeeEntity tartuBikeBaseFee;
    private BaseFeeEntity parnuCarBaseFee;
    private BaseFeeEntity parnuScooterBaseFee;
    private BaseFeeEntity parnuBikeBaseFee;

    @BeforeEach
    void setUp() {
        deliveryService = new DeliveryService(weatherRepository, baseFeeRepository);

        tallinnCarBaseFee = createBaseFeeEntity("Tallinn", "Car" , 4.0);
        tallinnScooterBaseFee = createBaseFeeEntity("Tallinn", "Scooter" , 3.5);
        tallinnBikeBaseFee = createBaseFeeEntity("Tallinn", "Bike" , 3.0);

        tartuCarBaseFee = createBaseFeeEntity("Tartu", "Car", 3.5);
        tartuScooterBaseFee = createBaseFeeEntity("Tartu", "Scooter", 3.0);
        tartuBikeBaseFee = createBaseFeeEntity("Tartu", "Bike", 2.5);

        parnuCarBaseFee = createBaseFeeEntity("Pärnu", "Car", 3.0);
        parnuScooterBaseFee = createBaseFeeEntity("Pärnu", "Scooter", 2.5);
        parnuBikeBaseFee = createBaseFeeEntity("Pärnu","Bike", 2.0);
    }

    private WeatherEntity createWeatherEntity(String stationName, double temperature, double windSpeed, String phenomenon) {
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName(stationName);
        entity.setAirTemperature(temperature);
        entity.setWindSpeed(windSpeed);
        entity.setWeatherPhenomenon(phenomenon);
        entity.setTimestamp(LocalDateTime.now());
        return entity;
    }

    private BaseFeeEntity createBaseFeeEntity(String city, String vehicleType, double baseFee) {
        BaseFeeEntity baseFeeEntity = new BaseFeeEntity();
        baseFeeEntity.setCity(city);
        baseFeeEntity.setVehicleType(vehicleType);
        baseFeeEntity.setBaseFee(baseFee);
        return baseFeeEntity;
    }

    @Test
    void testCalculateFee_Tallinn_NormalConditions() {
        WeatherEntity entity = createWeatherEntity("Tallinn-Harku", 5, 5, "Clear");

        when(weatherRepository.findLatestByStationName("Tallinn-Harku")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Car")).thenReturn(Optional.of(tallinnCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Scooter")).thenReturn(Optional.of(tallinnScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Bike")).thenReturn(Optional.of(tallinnBikeBaseFee));
        
        assertEquals(4.0, deliveryService.calculateTotalFee("Car", "Tallinn"));
        assertEquals(3.5, deliveryService.calculateTotalFee("Scooter", "Tallinn"));
        assertEquals(3.0, deliveryService.calculateTotalFee("Bike", "Tallinn"));

    }

    @Test
    void testCalculateFee_Tartu_NormalConditions() {
        WeatherEntity entity = createWeatherEntity("Tartu-Tõravere", 5, 5, "Clear");

        when(weatherRepository.findLatestByStationName("Tartu-Tõravere")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Car")).thenReturn(Optional.of(tartuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Scooter")).thenReturn(Optional.of(tartuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Bike")).thenReturn(Optional.of(tartuBikeBaseFee));

        assertEquals(3.5, deliveryService.calculateTotalFee("Car", "Tartu"));
        assertEquals(3.0, deliveryService.calculateTotalFee("Scooter", "Tartu"));
        assertEquals(2.5, deliveryService.calculateTotalFee("Bike", "Tartu"));
    }

    @Test
    void testCalculateFee_Parnu_NormalConditions() {
        WeatherEntity entity = createWeatherEntity("Pärnu", 5, 5, "Clear");

        when(weatherRepository.findLatestByStationName("Pärnu")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Car")).thenReturn(Optional.of(parnuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Scooter")).thenReturn(Optional.of(parnuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Bike")).thenReturn(Optional.of(parnuBikeBaseFee));

        assertEquals(3.0, deliveryService.calculateTotalFee("Car", "Pärnu"));
        assertEquals(2.5, deliveryService.calculateTotalFee("Scooter", "Pärnu"));
        assertEquals(2.0, deliveryService.calculateTotalFee("Bike", "Pärnu"));
    }

    @Test
    void testCalculateFee_Tallinn_LowTemperature() {
        WeatherEntity entity = createWeatherEntity("Tallinn-Harku", -10, 5, "Clear");

        when(weatherRepository.findLatestByStationName("Tallinn-Harku")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Car")).thenReturn(Optional.of(tallinnCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Scooter")).thenReturn(Optional.of(tallinnScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Bike")).thenReturn(Optional.of(tallinnBikeBaseFee));

        assertEquals(4.0, deliveryService.calculateTotalFee("Car", "Tallinn"));
        assertEquals(4.0, deliveryService.calculateTotalFee("Scooter", "Tallinn"));
        assertEquals(3.5, deliveryService.calculateTotalFee("Bike", "Tallinn"));

    }

    @Test
    void testCalculateFee_Tartu_LowTemperature() {
        WeatherEntity entity = createWeatherEntity("Tartu-Tõravere", 0, 5, "Clear");

        when(weatherRepository.findLatestByStationName("Tartu-Tõravere")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Car")).thenReturn(Optional.of(tartuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Scooter")).thenReturn(Optional.of(tartuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Bike")).thenReturn(Optional.of(tartuBikeBaseFee));

        assertEquals(3.5, deliveryService.calculateTotalFee("Car", "Tartu"));
        assertEquals(3.5, deliveryService.calculateTotalFee("Scooter", "Tartu"));
        assertEquals(3.0, deliveryService.calculateTotalFee("Bike", "Tartu"));
    }

    @Test
    void testCalculateFee_Parnu_LowTemperature() {
        WeatherEntity entity = createWeatherEntity("Pärnu", -5, 5, "Clear");

        when(weatherRepository.findLatestByStationName("Pärnu")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Car")).thenReturn(Optional.of(parnuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Scooter")).thenReturn(Optional.of(parnuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Bike")).thenReturn(Optional.of(parnuBikeBaseFee));

        assertEquals(3.0, deliveryService.calculateTotalFee("Car", "Pärnu"));
        assertEquals(3.0, deliveryService.calculateTotalFee("Scooter", "Pärnu"));
        assertEquals(2.5, deliveryService.calculateTotalFee("Bike", "Pärnu"));
    }

    @Test
    void testCalculateFee_Tallinn_VeryLowTemperature() {
        WeatherEntity entity = createWeatherEntity("Tallinn-Harku", -15, 5, "Clear");

        when(weatherRepository.findLatestByStationName("Tallinn-Harku")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Car")).thenReturn(Optional.of(tallinnCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Scooter")).thenReturn(Optional.of(tallinnScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Bike")).thenReturn(Optional.of(tallinnBikeBaseFee));

        assertEquals(4.0, deliveryService.calculateTotalFee("Car", "Tallinn"));
        assertEquals(4.5, deliveryService.calculateTotalFee("Scooter", "Tallinn"));
        assertEquals(4.0, deliveryService.calculateTotalFee("Bike", "Tallinn"));

    }

    @Test
    void testCalculateFee_Tartu_VeryLowTemperature() {
        WeatherEntity entity = createWeatherEntity("Tartu-Tõravere", -15, 5, "Clear");

        when(weatherRepository.findLatestByStationName("Tartu-Tõravere")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Car")).thenReturn(Optional.of(tartuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Scooter")).thenReturn(Optional.of(tartuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Bike")).thenReturn(Optional.of(tartuBikeBaseFee));

        assertEquals(3.5, deliveryService.calculateTotalFee("Car", "Tartu"));
        assertEquals(4.0, deliveryService.calculateTotalFee("Scooter", "Tartu"));
        assertEquals(3.5, deliveryService.calculateTotalFee("Bike", "Tartu"));
    }

    @Test
    void testCalculateFee_Parnu_VeryLowTemperature() {
        WeatherEntity entity = createWeatherEntity("Pärnu", -15, 5, "Clear");

        when(weatherRepository.findLatestByStationName("Pärnu")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Car")).thenReturn(Optional.of(parnuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Scooter")).thenReturn(Optional.of(parnuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Bike")).thenReturn(Optional.of(parnuBikeBaseFee));

        assertEquals(3.0, deliveryService.calculateTotalFee("Car", "Pärnu"));
        assertEquals(3.5, deliveryService.calculateTotalFee("Scooter", "Pärnu"));
        assertEquals(3.0, deliveryService.calculateTotalFee("Bike", "Pärnu"));
    }

    @Test
    void testCalculateFee_Tallinn_StrongWind() {
        WeatherEntity entity = createWeatherEntity("Tallinn-Harku", 5, 10, "Clear");

        when(weatherRepository.findLatestByStationName("Tallinn-Harku")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Car")).thenReturn(Optional.of(tallinnCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Scooter")).thenReturn(Optional.of(tallinnScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Bike")).thenReturn(Optional.of(tallinnBikeBaseFee));

        assertEquals(4.0, deliveryService.calculateTotalFee("Car", "Tallinn"));
        assertEquals(3.5, deliveryService.calculateTotalFee("Scooter", "Tallinn"));
        assertEquals(3.5, deliveryService.calculateTotalFee("Bike", "Tallinn"));

    }

    @Test
    void testCalculateFee_Tartu_StrongWind() {
        WeatherEntity entity = createWeatherEntity("Tartu-Tõravere", 5, 15, "Clear");

        when(weatherRepository.findLatestByStationName("Tartu-Tõravere")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Car")).thenReturn(Optional.of(tartuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Scooter")).thenReturn(Optional.of(tartuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Bike")).thenReturn(Optional.of(tartuBikeBaseFee));

        assertEquals(3.5, deliveryService.calculateTotalFee("Car", "Tartu"));
        assertEquals(3.0, deliveryService.calculateTotalFee("Scooter", "Tartu"));
        assertEquals(3.0, deliveryService.calculateTotalFee("Bike", "Tartu"));
    }

    @Test
    void testCalculateFee_Parnu_StrongWind() {
        WeatherEntity entity = createWeatherEntity("Pärnu", 5, 20, "Clear");

        when(weatherRepository.findLatestByStationName("Pärnu")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Car")).thenReturn(Optional.of(parnuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Scooter")).thenReturn(Optional.of(parnuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Bike")).thenReturn(Optional.of(parnuBikeBaseFee));

        assertEquals(3.0, deliveryService.calculateTotalFee("Car", "Pärnu"));
        assertEquals(2.5, deliveryService.calculateTotalFee("Scooter", "Pärnu"));
        assertEquals(2.5, deliveryService.calculateTotalFee("Bike", "Pärnu"));
    }

    @Test
    void testCalculateFee_Tallinn_VeryStrongWind() {
        WeatherEntity entity = createWeatherEntity("Tallinn-Harku", 5, 25, "Clear");

        when(weatherRepository.findLatestByStationName("Tallinn-Harku")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Car")).thenReturn(Optional.of(tallinnCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Scooter")).thenReturn(Optional.of(tallinnScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Bike")).thenReturn(Optional.of(tallinnBikeBaseFee));

        assertEquals(4.0, deliveryService.calculateTotalFee("Car", "Tallinn"));
        assertEquals(3.5, deliveryService.calculateTotalFee("Scooter", "Tallinn"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> deliveryService.calculateTotalFee("Bike", "Tallinn"));
        assertEquals("Usage of selected vehicle type is forbidden", exception.getMessage());
    }

    @Test
    void testCalculateFee_Tartu_VeryStrongWind() {
        WeatherEntity entity = createWeatherEntity("Tartu-Tõravere", 5, 25, "Clear");

        when(weatherRepository.findLatestByStationName("Tartu-Tõravere")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Car")).thenReturn(Optional.of(tartuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Scooter")).thenReturn(Optional.of(tartuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Bike")).thenReturn(Optional.of(tartuBikeBaseFee));

        assertEquals(3.5, deliveryService.calculateTotalFee("Car", "Tartu"));
        assertEquals(3.0, deliveryService.calculateTotalFee("Scooter", "Tartu"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> deliveryService.calculateTotalFee("Bike", "Tartu"));
        assertEquals("Usage of selected vehicle type is forbidden", exception.getMessage());
    }

    @Test
    void testCalculateFee_Parnu_VeryStrongWind() {
        WeatherEntity entity = createWeatherEntity("Pärnu", 5, 25, "Clear");

        when(weatherRepository.findLatestByStationName("Pärnu")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Car")).thenReturn(Optional.of(parnuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Scooter")).thenReturn(Optional.of(parnuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Bike")).thenReturn(Optional.of(parnuBikeBaseFee));

        assertEquals(3.0, deliveryService.calculateTotalFee("Car", "Pärnu"));
        assertEquals(2.5, deliveryService.calculateTotalFee("Scooter", "Pärnu"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> deliveryService.calculateTotalFee("Bike", "Pärnu"));
        assertEquals("Usage of selected vehicle type is forbidden", exception.getMessage());
    }

    @Test
    void testCalculateFee_Tallinn_SnowOrSleet() {
        WeatherEntity entity = createWeatherEntity("Tallinn-Harku", 5, 5, "Moderate snowfall");

        when(weatherRepository.findLatestByStationName("Tallinn-Harku")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Car")).thenReturn(Optional.of(tallinnCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Scooter")).thenReturn(Optional.of(tallinnScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Bike")).thenReturn(Optional.of(tallinnBikeBaseFee));

        assertEquals(4.0, deliveryService.calculateTotalFee("Car", "Tallinn"));
        assertEquals(4.5, deliveryService.calculateTotalFee("Scooter", "Tallinn"));
        assertEquals(4.0, deliveryService.calculateTotalFee("Bike", "Tallinn"));

    }

    @Test
    void testCalculateFee_Tartu_SnowOrSleet() {
        WeatherEntity entity = createWeatherEntity("Tartu-Tõravere", 5, 5, "Light sleet");

        when(weatherRepository.findLatestByStationName("Tartu-Tõravere")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Car")).thenReturn(Optional.of(tartuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Scooter")).thenReturn(Optional.of(tartuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Bike")).thenReturn(Optional.of(tartuBikeBaseFee));

        assertEquals(3.5, deliveryService.calculateTotalFee("Car", "Tartu"));
        assertEquals(4.0, deliveryService.calculateTotalFee("Scooter", "Tartu"));
        assertEquals(3.5, deliveryService.calculateTotalFee("Bike", "Tartu"));
    }

    @Test
    void testCalculateFee_Parnu_SnowOrSleet() {
        WeatherEntity entity = createWeatherEntity("Pärnu", 5, 5, "Heavy snowfall");

        when(weatherRepository.findLatestByStationName("Pärnu")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Car")).thenReturn(Optional.of(parnuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Scooter")).thenReturn(Optional.of(parnuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Bike")).thenReturn(Optional.of(parnuBikeBaseFee));

        assertEquals(3.0, deliveryService.calculateTotalFee("Car", "Pärnu"));
        assertEquals(3.5, deliveryService.calculateTotalFee("Scooter", "Pärnu"));
        assertEquals(3.0, deliveryService.calculateTotalFee("Bike", "Pärnu"));
    }

    @Test
    void testCalculateFee_Tallinn_Rain() {
        WeatherEntity entity = createWeatherEntity("Tallinn-Harku", 5, 5, "Light rain");

        when(weatherRepository.findLatestByStationName("Tallinn-Harku")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Car")).thenReturn(Optional.of(tallinnCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Scooter")).thenReturn(Optional.of(tallinnScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Bike")).thenReturn(Optional.of(tallinnBikeBaseFee));

        assertEquals(4.0, deliveryService.calculateTotalFee("Car", "Tallinn"));
        assertEquals(4.0, deliveryService.calculateTotalFee("Scooter", "Tallinn"));
        assertEquals(3.5, deliveryService.calculateTotalFee("Bike", "Tallinn"));

    }

    @Test
    void testCalculateFee_Tartu_Rain() {
        WeatherEntity entity = createWeatherEntity("Tartu-Tõravere", 5, 5, "Moderate rain");

        when(weatherRepository.findLatestByStationName("Tartu-Tõravere")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Car")).thenReturn(Optional.of(tartuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Scooter")).thenReturn(Optional.of(tartuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Bike")).thenReturn(Optional.of(tartuBikeBaseFee));

        assertEquals(3.5, deliveryService.calculateTotalFee("Car", "Tartu"));
        assertEquals(3.5, deliveryService.calculateTotalFee("Scooter", "Tartu"));
        assertEquals(3.0, deliveryService.calculateTotalFee("Bike", "Tartu"));
    }

    @Test
    void testCalculateFee_Parnu_Rain() {
        WeatherEntity entity = createWeatherEntity("Pärnu", 5, 5, "Heavy rain");

        when(weatherRepository.findLatestByStationName("Pärnu")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Car")).thenReturn(Optional.of(parnuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Scooter")).thenReturn(Optional.of(parnuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Bike")).thenReturn(Optional.of(parnuBikeBaseFee));

        assertEquals(3.0, deliveryService.calculateTotalFee("Car", "Pärnu"));
        assertEquals(3.0, deliveryService.calculateTotalFee("Scooter", "Pärnu"));
        assertEquals(2.5, deliveryService.calculateTotalFee("Bike", "Pärnu"));
    }

    @Test
    void testCalculateFee_Tallinn_Glaze() {
        WeatherEntity entity = createWeatherEntity("Tallinn-Harku", 5, 5, "Glaze");

        when(weatherRepository.findLatestByStationName("Tallinn-Harku")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Car")).thenReturn(Optional.of(tallinnCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Scooter")).thenReturn(Optional.of(tallinnScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tallinn", "Bike")).thenReturn(Optional.of(tallinnBikeBaseFee));

        assertEquals(4.0, deliveryService.calculateTotalFee("Car", "Tallinn"));
        IllegalArgumentException scooterException = assertThrows(IllegalArgumentException.class, () -> deliveryService.calculateTotalFee("Scooter", "Tallinn"));
        assertEquals("Usage of selected vehicle type is forbidden", scooterException.getMessage());
        IllegalArgumentException bikeException = assertThrows(IllegalArgumentException.class, () -> deliveryService.calculateTotalFee("Bike", "Tallinn"));
        assertEquals("Usage of selected vehicle type is forbidden", bikeException.getMessage());
    }

    @Test
    void testCalculateFee_Tartu_Hail() {
        WeatherEntity entity = createWeatherEntity("Tartu-Tõravere", 5, 5, "Hail");

        when(weatherRepository.findLatestByStationName("Tartu-Tõravere")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Car")).thenReturn(Optional.of(tartuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Scooter")).thenReturn(Optional.of(tartuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Tartu", "Bike")).thenReturn(Optional.of(tartuBikeBaseFee));

        assertEquals(3.5, deliveryService.calculateTotalFee("Car", "Tartu"));
        IllegalArgumentException scooterException = assertThrows(IllegalArgumentException.class, () -> deliveryService.calculateTotalFee("Scooter", "Tartu"));
        assertEquals("Usage of selected vehicle type is forbidden", scooterException.getMessage());
        IllegalArgumentException bikeException = assertThrows(IllegalArgumentException.class, () ->deliveryService.calculateTotalFee("Bike", "Tartu"));
        assertEquals("Usage of selected vehicle type is forbidden", bikeException.getMessage());
    }

    @Test
    void testCalculateFee_Parnu_Thunder() {
        WeatherEntity entity = createWeatherEntity("Pärnu", 5, 5, "Thunder");

        when(weatherRepository.findLatestByStationName("Pärnu")).thenReturn(entity);
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Car")).thenReturn(Optional.of(parnuCarBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Scooter")).thenReturn(Optional.of(parnuScooterBaseFee));
        when(baseFeeRepository.findByCityAndVehicleType("Pärnu", "Bike")).thenReturn(Optional.of(parnuBikeBaseFee));

        assertEquals(3.0, deliveryService.calculateTotalFee("Car", "Pärnu"));
        IllegalArgumentException scooterException = assertThrows(IllegalArgumentException.class, () -> deliveryService.calculateTotalFee("Scooter", "Pärnu"));
        assertEquals("Usage of selected vehicle type is forbidden", scooterException.getMessage());
        IllegalArgumentException bikeException = assertThrows(IllegalArgumentException.class, () -> deliveryService.calculateTotalFee("Bike", "Pärnu"));
        assertEquals("Usage of selected vehicle type is forbidden", bikeException.getMessage());
    }

    @Test
    void testCalculateFee_InvalidCity() {
        assertThrows(IllegalStateException.class, () -> deliveryService.calculateTotalFee("Car", "Narva"));
    }

    @Test
    void testCalculateFee_NoWeatherDataAvailable() {
        assertThrows(IllegalStateException.class, () -> deliveryService.calculateTotalFee("Car", "Pärnu"));
    }
}
