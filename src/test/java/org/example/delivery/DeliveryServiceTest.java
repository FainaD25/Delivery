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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Mock
    private WeatherRepository weatherRepository;
    @Mock
    private BaseFeeRepository baseFeeRepository;

    @InjectMocks
    private DeliveryService deliveryService;

    @BeforeEach
    void setUp() {
        deliveryService = new DeliveryService(weatherRepository, baseFeeRepository);
    }

    @Test
    void testCalculateFee_Tallinn_NormalConditions() {
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tallinn-Harku");
        entity.setAirTemperature(5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tallinnCarBaseFee = new BaseFeeEntity();
        tallinnCarBaseFee.setCity("Tallinn");
        tallinnCarBaseFee.setVehicleType("Car");
        tallinnCarBaseFee.setBaseFee(4.0);

        BaseFeeEntity tallinnScooterBaseFee = new BaseFeeEntity();
        tallinnScooterBaseFee.setCity("Tallinn");
        tallinnScooterBaseFee.setVehicleType("Scooter");
        tallinnScooterBaseFee.setBaseFee(3.5);

        BaseFeeEntity tallinnBikeBaseFee = new BaseFeeEntity();
        tallinnBikeBaseFee.setCity("Tallinn");
        tallinnBikeBaseFee.setVehicleType("Bike");
        tallinnBikeBaseFee.setBaseFee(3.0);

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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tartu-Tõravere");
        entity.setAirTemperature(5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tartuCarBaseFee = new BaseFeeEntity();
        tartuCarBaseFee.setCity("Tartu");
        tartuCarBaseFee.setVehicleType("Car");
        tartuCarBaseFee.setBaseFee(3.5);

        BaseFeeEntity tartuScooterBaseFee = new BaseFeeEntity();
        tartuScooterBaseFee.setCity("Tartu");
        tartuScooterBaseFee.setVehicleType("Scooter");
        tartuScooterBaseFee.setBaseFee(3.0);

        BaseFeeEntity tartuBikeBaseFee = new BaseFeeEntity();
        tartuBikeBaseFee.setCity("Tartu");
        tartuBikeBaseFee.setVehicleType("Bike");
        tartuBikeBaseFee.setBaseFee(2.5);


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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Pärnu");
        entity.setAirTemperature(5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity parnuCarBaseFee = new BaseFeeEntity();
        parnuCarBaseFee.setCity("Pärnu");
        parnuCarBaseFee.setVehicleType("Car");
        parnuCarBaseFee.setBaseFee(3.0);

        BaseFeeEntity parnuScooterBaseFee = new BaseFeeEntity();
        parnuScooterBaseFee.setCity("Pärnu");
        parnuScooterBaseFee.setVehicleType("Scooter");
        parnuScooterBaseFee.setBaseFee(2.5);

        BaseFeeEntity parnuBikeBaseFee = new BaseFeeEntity();
        parnuBikeBaseFee.setCity("Pärnu");
        parnuBikeBaseFee.setVehicleType("Bike");
        parnuBikeBaseFee.setBaseFee(2.0);


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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tallinn-Harku");
        entity.setAirTemperature(-5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tallinnCarBaseFee = new BaseFeeEntity();
        tallinnCarBaseFee.setCity("Tallinn");
        tallinnCarBaseFee.setVehicleType("Car");
        tallinnCarBaseFee.setBaseFee(4.0);

        BaseFeeEntity tallinnScooterBaseFee = new BaseFeeEntity();
        tallinnScooterBaseFee.setCity("Tallinn");
        tallinnScooterBaseFee.setVehicleType("Scooter");
        tallinnScooterBaseFee.setBaseFee(3.5);

        BaseFeeEntity tallinnBikeBaseFee = new BaseFeeEntity();
        tallinnBikeBaseFee.setCity("Tallinn");
        tallinnBikeBaseFee.setVehicleType("Bike");
        tallinnBikeBaseFee.setBaseFee(3.0);

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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tartu-Tõravere");
        entity.setAirTemperature(-5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tartuCarBaseFee = new BaseFeeEntity();
        tartuCarBaseFee.setCity("Tartu");
        tartuCarBaseFee.setVehicleType("Car");
        tartuCarBaseFee.setBaseFee(3.5);

        BaseFeeEntity tartuScooterBaseFee = new BaseFeeEntity();
        tartuScooterBaseFee.setCity("Tartu");
        tartuScooterBaseFee.setVehicleType("Scooter");
        tartuScooterBaseFee.setBaseFee(3.0);

        BaseFeeEntity tartuBikeBaseFee = new BaseFeeEntity();
        tartuBikeBaseFee.setCity("Tartu");
        tartuBikeBaseFee.setVehicleType("Bike");
        tartuBikeBaseFee.setBaseFee(2.5);


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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Pärnu");
        entity.setAirTemperature(-5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity parnuCarBaseFee = new BaseFeeEntity();
        parnuCarBaseFee.setCity("Pärnu");
        parnuCarBaseFee.setVehicleType("Car");
        parnuCarBaseFee.setBaseFee(3.0);

        BaseFeeEntity parnuScooterBaseFee = new BaseFeeEntity();
        parnuScooterBaseFee.setCity("Pärnu");
        parnuScooterBaseFee.setVehicleType("Scooter");
        parnuScooterBaseFee.setBaseFee(2.5);

        BaseFeeEntity parnuBikeBaseFee = new BaseFeeEntity();
        parnuBikeBaseFee.setCity("Pärnu");
        parnuBikeBaseFee.setVehicleType("Bike");
        parnuBikeBaseFee.setBaseFee(2.0);

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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tallinn-Harku");
        entity.setAirTemperature(-15);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tallinnCarBaseFee = new BaseFeeEntity();
        tallinnCarBaseFee.setCity("Tallinn");
        tallinnCarBaseFee.setVehicleType("Car");
        tallinnCarBaseFee.setBaseFee(4.0);

        BaseFeeEntity tallinnScooterBaseFee = new BaseFeeEntity();
        tallinnScooterBaseFee.setCity("Tallinn");
        tallinnScooterBaseFee.setVehicleType("Scooter");
        tallinnScooterBaseFee.setBaseFee(3.5);

        BaseFeeEntity tallinnBikeBaseFee = new BaseFeeEntity();
        tallinnBikeBaseFee.setCity("Tallinn");
        tallinnBikeBaseFee.setVehicleType("Bike");
        tallinnBikeBaseFee.setBaseFee(3.0);

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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tartu-Tõravere");
        entity.setAirTemperature(-15);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tartuCarBaseFee = new BaseFeeEntity();
        tartuCarBaseFee.setCity("Tartu");
        tartuCarBaseFee.setVehicleType("Car");
        tartuCarBaseFee.setBaseFee(3.5);

        BaseFeeEntity tartuScooterBaseFee = new BaseFeeEntity();
        tartuScooterBaseFee.setCity("Tartu");
        tartuScooterBaseFee.setVehicleType("Scooter");
        tartuScooterBaseFee.setBaseFee(3.0);

        BaseFeeEntity tartuBikeBaseFee = new BaseFeeEntity();
        tartuBikeBaseFee.setCity("Tartu");
        tartuBikeBaseFee.setVehicleType("Bike");
        tartuBikeBaseFee.setBaseFee(2.5);


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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Pärnu");
        entity.setAirTemperature(-15);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity parnuCarBaseFee = new BaseFeeEntity();
        parnuCarBaseFee.setCity("Pärnu");
        parnuCarBaseFee.setVehicleType("Car");
        parnuCarBaseFee.setBaseFee(3.0);

        BaseFeeEntity parnuScooterBaseFee = new BaseFeeEntity();
        parnuScooterBaseFee.setCity("Pärnu");
        parnuScooterBaseFee.setVehicleType("Scooter");
        parnuScooterBaseFee.setBaseFee(2.5);

        BaseFeeEntity parnuBikeBaseFee = new BaseFeeEntity();
        parnuBikeBaseFee.setCity("Pärnu");
        parnuBikeBaseFee.setVehicleType("Bike");
        parnuBikeBaseFee.setBaseFee(2.0);

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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tallinn-Harku");
        entity.setAirTemperature(5);
        entity.setWindSpeed(15);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tallinnCarBaseFee = new BaseFeeEntity();
        tallinnCarBaseFee.setCity("Tallinn");
        tallinnCarBaseFee.setVehicleType("Car");
        tallinnCarBaseFee.setBaseFee(4.0);

        BaseFeeEntity tallinnScooterBaseFee = new BaseFeeEntity();
        tallinnScooterBaseFee.setCity("Tallinn");
        tallinnScooterBaseFee.setVehicleType("Scooter");
        tallinnScooterBaseFee.setBaseFee(3.5);

        BaseFeeEntity tallinnBikeBaseFee = new BaseFeeEntity();
        tallinnBikeBaseFee.setCity("Tallinn");
        tallinnBikeBaseFee.setVehicleType("Bike");
        tallinnBikeBaseFee.setBaseFee(3.0);

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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tartu-Tõravere");
        entity.setAirTemperature(5);
        entity.setWindSpeed(15);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tartuCarBaseFee = new BaseFeeEntity();
        tartuCarBaseFee.setCity("Tartu");
        tartuCarBaseFee.setVehicleType("Car");
        tartuCarBaseFee.setBaseFee(3.5);

        BaseFeeEntity tartuScooterBaseFee = new BaseFeeEntity();
        tartuScooterBaseFee.setCity("Tartu");
        tartuScooterBaseFee.setVehicleType("Scooter");
        tartuScooterBaseFee.setBaseFee(3.0);

        BaseFeeEntity tartuBikeBaseFee = new BaseFeeEntity();
        tartuBikeBaseFee.setCity("Tartu");
        tartuBikeBaseFee.setVehicleType("Bike");
        tartuBikeBaseFee.setBaseFee(2.5);


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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Pärnu");
        entity.setAirTemperature(5);
        entity.setWindSpeed(15);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity parnuCarBaseFee = new BaseFeeEntity();
        parnuCarBaseFee.setCity("Pärnu");
        parnuCarBaseFee.setVehicleType("Car");
        parnuCarBaseFee.setBaseFee(3.0);

        BaseFeeEntity parnuScooterBaseFee = new BaseFeeEntity();
        parnuScooterBaseFee.setCity("Pärnu");
        parnuScooterBaseFee.setVehicleType("Scooter");
        parnuScooterBaseFee.setBaseFee(2.5);

        BaseFeeEntity parnuBikeBaseFee = new BaseFeeEntity();
        parnuBikeBaseFee.setCity("Pärnu");
        parnuBikeBaseFee.setVehicleType("Bike");
        parnuBikeBaseFee.setBaseFee(2.0);

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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tallinn-Harku");
        entity.setAirTemperature(5);
        entity.setWindSpeed(25);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tallinnCarBaseFee = new BaseFeeEntity();
        tallinnCarBaseFee.setCity("Tallinn");
        tallinnCarBaseFee.setVehicleType("Car");
        tallinnCarBaseFee.setBaseFee(4.0);

        BaseFeeEntity tallinnScooterBaseFee = new BaseFeeEntity();
        tallinnScooterBaseFee.setCity("Tallinn");
        tallinnScooterBaseFee.setVehicleType("Scooter");
        tallinnScooterBaseFee.setBaseFee(3.5);

        BaseFeeEntity tallinnBikeBaseFee = new BaseFeeEntity();
        tallinnBikeBaseFee.setCity("Tallinn");
        tallinnBikeBaseFee.setVehicleType("Bike");
        tallinnBikeBaseFee.setBaseFee(3.0);

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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tartu-Tõravere");
        entity.setAirTemperature(5);
        entity.setWindSpeed(25);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tartuCarBaseFee = new BaseFeeEntity();
        tartuCarBaseFee.setCity("Tartu");
        tartuCarBaseFee.setVehicleType("Car");
        tartuCarBaseFee.setBaseFee(3.5);

        BaseFeeEntity tartuScooterBaseFee = new BaseFeeEntity();
        tartuScooterBaseFee.setCity("Tartu");
        tartuScooterBaseFee.setVehicleType("Scooter");
        tartuScooterBaseFee.setBaseFee(3.0);

        BaseFeeEntity tartuBikeBaseFee = new BaseFeeEntity();
        tartuBikeBaseFee.setCity("Tartu");
        tartuBikeBaseFee.setVehicleType("Bike");
        tartuBikeBaseFee.setBaseFee(2.5);


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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Pärnu");
        entity.setAirTemperature(5);
        entity.setWindSpeed(25);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity parnuCarBaseFee = new BaseFeeEntity();
        parnuCarBaseFee.setCity("Pärnu");
        parnuCarBaseFee.setVehicleType("Car");
        parnuCarBaseFee.setBaseFee(3.0);

        BaseFeeEntity parnuScooterBaseFee = new BaseFeeEntity();
        parnuScooterBaseFee.setCity("Pärnu");
        parnuScooterBaseFee.setVehicleType("Scooter");
        parnuScooterBaseFee.setBaseFee(2.5);

        BaseFeeEntity parnuBikeBaseFee = new BaseFeeEntity();
        parnuBikeBaseFee.setCity("Pärnu");
        parnuBikeBaseFee.setVehicleType("Bike");
        parnuBikeBaseFee.setBaseFee(2.0);

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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tallinn-Harku");
        entity.setAirTemperature(5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Moderate snowfall");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tallinnCarBaseFee = new BaseFeeEntity();
        tallinnCarBaseFee.setCity("Tallinn");
        tallinnCarBaseFee.setVehicleType("Car");
        tallinnCarBaseFee.setBaseFee(4.0);

        BaseFeeEntity tallinnScooterBaseFee = new BaseFeeEntity();
        tallinnScooterBaseFee.setCity("Tallinn");
        tallinnScooterBaseFee.setVehicleType("Scooter");
        tallinnScooterBaseFee.setBaseFee(3.5);

        BaseFeeEntity tallinnBikeBaseFee = new BaseFeeEntity();
        tallinnBikeBaseFee.setCity("Tallinn");
        tallinnBikeBaseFee.setVehicleType("Bike");
        tallinnBikeBaseFee.setBaseFee(3.0);

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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tartu-Tõravere");
        entity.setAirTemperature(5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Light sleet");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tartuCarBaseFee = new BaseFeeEntity();
        tartuCarBaseFee.setCity("Tartu");
        tartuCarBaseFee.setVehicleType("Car");
        tartuCarBaseFee.setBaseFee(3.5);

        BaseFeeEntity tartuScooterBaseFee = new BaseFeeEntity();
        tartuScooterBaseFee.setCity("Tartu");
        tartuScooterBaseFee.setVehicleType("Scooter");
        tartuScooterBaseFee.setBaseFee(3.0);

        BaseFeeEntity tartuBikeBaseFee = new BaseFeeEntity();
        tartuBikeBaseFee.setCity("Tartu");
        tartuBikeBaseFee.setVehicleType("Bike");
        tartuBikeBaseFee.setBaseFee(2.5);


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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Pärnu");
        entity.setAirTemperature(5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Heavy snowfall");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity parnuCarBaseFee = new BaseFeeEntity();
        parnuCarBaseFee.setCity("Pärnu");
        parnuCarBaseFee.setVehicleType("Car");
        parnuCarBaseFee.setBaseFee(3.0);

        BaseFeeEntity parnuScooterBaseFee = new BaseFeeEntity();
        parnuScooterBaseFee.setCity("Pärnu");
        parnuScooterBaseFee.setVehicleType("Scooter");
        parnuScooterBaseFee.setBaseFee(2.5);

        BaseFeeEntity parnuBikeBaseFee = new BaseFeeEntity();
        parnuBikeBaseFee.setCity("Pärnu");
        parnuBikeBaseFee.setVehicleType("Bike");
        parnuBikeBaseFee.setBaseFee(2.0);

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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tallinn-Harku");
        entity.setAirTemperature(5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Light rain");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tallinnCarBaseFee = new BaseFeeEntity();
        tallinnCarBaseFee.setCity("Tallinn");
        tallinnCarBaseFee.setVehicleType("Car");
        tallinnCarBaseFee.setBaseFee(4.0);

        BaseFeeEntity tallinnScooterBaseFee = new BaseFeeEntity();
        tallinnScooterBaseFee.setCity("Tallinn");
        tallinnScooterBaseFee.setVehicleType("Scooter");
        tallinnScooterBaseFee.setBaseFee(3.5);

        BaseFeeEntity tallinnBikeBaseFee = new BaseFeeEntity();
        tallinnBikeBaseFee.setCity("Tallinn");
        tallinnBikeBaseFee.setVehicleType("Bike");
        tallinnBikeBaseFee.setBaseFee(3.0);

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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tartu-Tõravere");
        entity.setAirTemperature(5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Moderate rain");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tartuCarBaseFee = new BaseFeeEntity();
        tartuCarBaseFee.setCity("Tartu");
        tartuCarBaseFee.setVehicleType("Car");
        tartuCarBaseFee.setBaseFee(3.5);

        BaseFeeEntity tartuScooterBaseFee = new BaseFeeEntity();
        tartuScooterBaseFee.setCity("Tartu");
        tartuScooterBaseFee.setVehicleType("Scooter");
        tartuScooterBaseFee.setBaseFee(3.0);

        BaseFeeEntity tartuBikeBaseFee = new BaseFeeEntity();
        tartuBikeBaseFee.setCity("Tartu");
        tartuBikeBaseFee.setVehicleType("Bike");
        tartuBikeBaseFee.setBaseFee(2.5);


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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Pärnu");
        entity.setAirTemperature(5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Heavy rain");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity parnuCarBaseFee = new BaseFeeEntity();
        parnuCarBaseFee.setCity("Pärnu");
        parnuCarBaseFee.setVehicleType("Car");
        parnuCarBaseFee.setBaseFee(3.0);

        BaseFeeEntity parnuScooterBaseFee = new BaseFeeEntity();
        parnuScooterBaseFee.setCity("Pärnu");
        parnuScooterBaseFee.setVehicleType("Scooter");
        parnuScooterBaseFee.setBaseFee(2.5);

        BaseFeeEntity parnuBikeBaseFee = new BaseFeeEntity();
        parnuBikeBaseFee.setCity("Pärnu");
        parnuBikeBaseFee.setVehicleType("Bike");
        parnuBikeBaseFee.setBaseFee(2.0);

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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tallinn-Harku");
        entity.setAirTemperature(5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Glaze");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tallinnCarBaseFee = new BaseFeeEntity();
        tallinnCarBaseFee.setCity("Tallinn");
        tallinnCarBaseFee.setVehicleType("Car");
        tallinnCarBaseFee.setBaseFee(4.0);

        BaseFeeEntity tallinnScooterBaseFee = new BaseFeeEntity();
        tallinnScooterBaseFee.setCity("Tallinn");
        tallinnScooterBaseFee.setVehicleType("Scooter");
        tallinnScooterBaseFee.setBaseFee(3.5);

        BaseFeeEntity tallinnBikeBaseFee = new BaseFeeEntity();
        tallinnBikeBaseFee.setCity("Tallinn");
        tallinnBikeBaseFee.setVehicleType("Bike");
        tallinnBikeBaseFee.setBaseFee(3.0);

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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Tartu-Tõravere");
        entity.setAirTemperature(5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Hail");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity tartuCarBaseFee = new BaseFeeEntity();
        tartuCarBaseFee.setCity("Tartu");
        tartuCarBaseFee.setVehicleType("Car");
        tartuCarBaseFee.setBaseFee(3.5);

        BaseFeeEntity tartuScooterBaseFee = new BaseFeeEntity();
        tartuScooterBaseFee.setCity("Tartu");
        tartuScooterBaseFee.setVehicleType("Scooter");
        tartuScooterBaseFee.setBaseFee(3.0);

        BaseFeeEntity tartuBikeBaseFee = new BaseFeeEntity();
        tartuBikeBaseFee.setCity("Tartu");
        tartuBikeBaseFee.setVehicleType("Bike");
        tartuBikeBaseFee.setBaseFee(2.5);


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
        WeatherEntity entity = new WeatherEntity();
        entity.setStationName("Pärnu");
        entity.setAirTemperature(5);
        entity.setWindSpeed(5);
        entity.setWeatherPhenomenon("Thunder");
        entity.setTimestamp(LocalDateTime.now());

        BaseFeeEntity parnuCarBaseFee = new BaseFeeEntity();
        parnuCarBaseFee.setCity("Pärnu");
        parnuCarBaseFee.setVehicleType("Car");
        parnuCarBaseFee.setBaseFee(3.0);

        BaseFeeEntity parnuScooterBaseFee = new BaseFeeEntity();
        parnuScooterBaseFee.setCity("Pärnu");
        parnuScooterBaseFee.setVehicleType("Scooter");
        parnuScooterBaseFee.setBaseFee(2.5);

        BaseFeeEntity parnuBikeBaseFee = new BaseFeeEntity();
        parnuBikeBaseFee.setCity("Pärnu");
        parnuBikeBaseFee.setVehicleType("Bike");
        parnuBikeBaseFee.setBaseFee(2.0);

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
        assertThrows(IllegalArgumentException.class, () -> deliveryService.calculateTotalFee("Car", "Narva"));
    }

    @Test
    void testCalculateFee_NoWeatherDataAvailable() {
        BaseFeeEntity parnuCarBaseFee = new BaseFeeEntity();
        parnuCarBaseFee.setCity("Pärnu");
        parnuCarBaseFee.setVehicleType("Car");
        parnuCarBaseFee.setBaseFee(3.0);

        assertThrows(IllegalStateException.class, () -> deliveryService.calculateTotalFee("Car", "Pärnu"));
    }
}
