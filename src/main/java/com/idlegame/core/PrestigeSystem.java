package com.idlegame.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PrestigeSystem {
    private static final Logger logger = LoggerFactory.getLogger(PrestigeSystem.class);
    private BigDecimal prestigePoints;
    private BigDecimal prestigeMultiplier;
    private final ResourceSystem resourceSystem;
    private final BuildingSystem buildingSystem;
    
    public PrestigeSystem(ResourceSystem resourceSystem, BuildingSystem buildingSystem) {
        this.resourceSystem = resourceSystem;
        this.buildingSystem = buildingSystem;
        this.prestigePoints = BigDecimal.ZERO;
        this.prestigeMultiplier = BigDecimal.ONE;
        logger.info("Prestige system initialized");
    }
    
    public boolean canPrestige() {
        return resourceSystem.getResource(GameConstants.PRIMARY_CURRENCY)
            .compareTo(GameConstants.PRESTIGE_REQUIREMENT) >= 0;
    }
    
    public void prestige() {
        if (!canPrestige()) {
            logger.warn("Attempted to prestige without meeting requirements");
            return;
        }
        
        // Calculate prestige points to gain
        BigDecimal currentResources = resourceSystem.getResource(GameConstants.PRIMARY_CURRENCY);
        BigDecimal newPoints = calculatePrestigePoints(currentResources);
        
        // Add new prestige points
        prestigePoints = prestigePoints.add(newPoints);
        
        // Update multiplier
        updatePrestigeMultiplier();
        
        // Reset game state but keep building counts
        resourceSystem.reset();
        // buildingSystem.reset(); // Commenting this out to keep building counts
        
        logger.info("Prestige completed. Gained {} points, new total: {}", 
            newPoints, prestigePoints);
    }
    
    private BigDecimal calculatePrestigePoints(BigDecimal resources) {
        return resources.divide(GameConstants.PRESTIGE_REQUIREMENT, 2, RoundingMode.FLOOR)
            .multiply(GameConstants.PRESTIGE_MULTIPLIER);
    }
    
    private void updatePrestigeMultiplier() {
        // Each prestige point gives a 1% bonus
        prestigeMultiplier = BigDecimal.ONE.add(
            prestigePoints.multiply(new BigDecimal("0.01"))
        );
        
        // Apply the multiplier to resource generation
        resourceSystem.setPrestigeMultiplier(prestigeMultiplier);
        buildingSystem.setPrestigeMultiplier(prestigeMultiplier);
        
        logger.debug("Updated prestige multiplier to: {}", prestigeMultiplier);
    }
    
    private void resetGameState() {
        // Reset resources but keep prestige points
        resourceSystem.reset();
        buildingSystem.reset();
        
        // Reapply prestige multiplier
        updatePrestigeMultiplier();
    }
    
    public BigDecimal getPrestigePoints() {
        return prestigePoints;
    }
    
    public BigDecimal getPrestigeMultiplier() {
        return prestigeMultiplier;
    }
    
    public BigDecimal getNextPrestigePointsGain() {
        BigDecimal currentResources = resourceSystem.getResource(GameConstants.PRIMARY_CURRENCY);
        return calculatePrestigePoints(currentResources);
    }
    
    public void setPrestigePoints(BigDecimal points) {
        logger.debug("Setting prestige points to {}", points);
        this.prestigePoints = points;
    }

    public void setPrestigeMultiplier(BigDecimal multiplier) {
        logger.debug("Setting prestige multiplier to {}", multiplier);
        this.prestigeMultiplier = multiplier;
        buildingSystem.setPrestigeMultiplier(multiplier);
    }
}
