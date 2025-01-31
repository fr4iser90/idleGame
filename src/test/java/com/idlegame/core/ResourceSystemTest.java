package com.idlegame.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

public class ResourceSystemTest {
    private ResourceSystem resourceSystem;
    private BuildingSystem buildingSystem;

    @BeforeEach
    void setUp() {
        resourceSystem = new ResourceSystem();
        buildingSystem = new BuildingSystem(resourceSystem);
    }

    @Test
    void testBasicResourceGeneration() {
        // Set up a test resource with known generation rate
        String testResource = "testResource";
        BigDecimal generationRate = BigDecimal.valueOf(1.0); // 1 per second
        resourceSystem.setGenerationRate(testResource, generationRate);

        // Update for 1 second worth of ticks
        resourceSystem.updateResources(buildingSystem, 1000);

        // Check if exactly 1 resource was generated (1 per second * 1 second)
        BigDecimal expectedAmount = BigDecimal.ONE;
        BigDecimal actualAmount = resourceSystem.getResource(testResource);
        assertEquals(0, expectedAmount.compareTo(actualAmount), 
            "Resource generation for 1 second should equal exactly 1 unit");
    }

    @Test
    void testResourceGenerationWithMultiplier() {
        String testResource = "testResource";
        BigDecimal generationRate = BigDecimal.valueOf(1.0);
        BigDecimal multiplier = BigDecimal.valueOf(2.0);
        
        resourceSystem.setGenerationRate(testResource, generationRate);
        resourceSystem.addMultiplier(testResource, "test", multiplier);

        // Update for 1 second
        resourceSystem.updateResources(buildingSystem, 1000);

        // Should generate 2 resources (1 base * 2 multiplier * 1 second)
        BigDecimal expectedAmount = BigDecimal.valueOf(2);
        BigDecimal actualAmount = resourceSystem.getResource(testResource);
        assertEquals(0, expectedAmount.compareTo(actualAmount),
            "Resource generation with multiplier should be correctly applied");
    }

    @Test
    void testResourceCap() {
        String testResource = "testResource";
        BigDecimal generationRate = BigDecimal.valueOf(10.0); // 10 per second
        BigDecimal cap = BigDecimal.valueOf(5.0); // Cap at 5

        resourceSystem.setGenerationRate(testResource, generationRate);
        resourceSystem.setResourceCap(testResource, cap);

        // Update for 1 second - should hit the cap
        resourceSystem.updateResources(buildingSystem, 1000);

        BigDecimal actualAmount = resourceSystem.getResource(testResource);
        assertEquals(0, cap.compareTo(actualAmount),
            "Resource amount should not exceed the cap");
    }

    @Test
    void testPartialSecondGeneration() {
        String testResource = "testResource";
        BigDecimal generationRate = BigDecimal.valueOf(1.0); // 1 per second
        resourceSystem.setGenerationRate(testResource, generationRate);

        // Update for half a second
        resourceSystem.updateResources(buildingSystem, 500);

        BigDecimal expectedAmount = BigDecimal.valueOf(0.5); // Half a second = 0.5 resources
        BigDecimal actualAmount = resourceSystem.getResource(testResource);
        assertEquals(0, expectedAmount.compareTo(actualAmount),
            "Resource generation for partial second should be proportional");
    }
}
