package org.example.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.delivery.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api")
@RestController
@Tag(name = "Delivery", description = "Operations related to deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Operation(
            summary = "Get delivery fee from database based on chosen vehicle type and city",
            description = "Retrieves a base fee for given combination of vehicle and city, calculates additional fees based on the latest weather data, and returns the total fee"
    )
    @ApiResponse(responseCode = "200", description = "Fee has been calculated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input or forbidden vehicle usage")
    @GetMapping("public/fee")
    public ResponseEntity<?> getDeliveryFee(
            @Parameter(name = "city", description = "City for which the fee needs to be calculated") @RequestParam(value = "city") String city,
            @Parameter(name = "vehicleType", description = "Type of vehicle to calculate fees for") @RequestParam(value = "vehicleType") String type
    ) {
        try {
            double deliveryFee = deliveryService.calculateTotalFee(type, city);
            return ResponseEntity.ok(deliveryFee);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
