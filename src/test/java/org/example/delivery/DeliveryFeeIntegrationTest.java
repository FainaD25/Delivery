package org.example.delivery;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class DeliveryFeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCalculateDeliveryFee() throws Exception {
        mockMvc.perform(get("/scheduler/run"));
        mockMvc.perform(get("/api/public/fee")
                .param("city", "Tallinn")
                .param("vehicleType", "Car"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testCalculateDeliveryFeeNoCityData() throws Exception {
        mockMvc.perform(get("/api/public/fee")
                        .param("city", "Narva")
                        .param("vehicleType", "Car"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCalculateDeliveryFeeNoVehicleData() throws Exception {
        mockMvc.perform(get("/api/public/fee")
                        .param("city", "Tallinn")
                        .param("vehicleType", "Truck"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCalculateDeliveryFeeNoWeatherData() throws Exception {
        mockMvc.perform(get("/api/public/fee")
                        .param("city", "Tallinn")
                        .param("vehicleType", "Car"))
                .andExpect(status().isNotFound());
    }
}
