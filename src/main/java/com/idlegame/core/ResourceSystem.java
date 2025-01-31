package com.idlegame.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages all game resources including storage, generation rates, and transactions.
 * Uses BigDecimal for precise calculations to prevent floating-point errors.
 */
public class ResourceSystem {
    private static final Logger logger = LoggerFactory.getLogger(ResourceSystem.class);
    
    private final Map<String, BigDecimal> resources;
    private final Map<String, BigDecimal> generationRates;
    private BigDecimal clickMultiplier;
    private final Map<String, BigDecimal> clickMultipliers = new HashMap<>();
    private final Map<String, BigDecimal> resourceCaps;
    private final Map<String, ResourceCategory> resourceCategories;
    private final Map<String, Map<String, BigDecimal>> resourceMultipliers;
    private final Map<String, BigDecimal> offlineProgressRates;

    public enum ResourceCategory {
        PRIMARY,
        SECONDARY,
        TERTIARY
    }

    public ResourceSystem() {
        this.resources = new HashMap<>();
        this.generationRates = new HashMap<>();
        this.clickMultiplier = BigDecimal.ONE;
        this.resourceCaps = new HashMap<>();
        this.resourceCategories = new HashMap<>();
        this.resourceMultipliers = new HashMap<>();
        this.offlineProgressRates = new HashMap<>();
        // Initialize default click multiplier
        clickMultipliers.put("base", BigDecimal.ONE);
        initializeResources();
    }

    private void initializeResources() {
        // Initialize with default values from GameConstants
        resources.put(GameConstants.PRIMARY_CURRENCY, GameConstants.INITIAL_PRIMARY_CURRENCY);
        resources.put(GameConstants.SECONDARY_CURRENCY, GameConstants.INITIAL_SECONDARY_CURRENCY);
        resources.put(GameConstants.TERTIARY_CURRENCY, GameConstants.INITIAL_TERTIARY_CURRENCY);

        // Initialize generation rates
        generationRates.put(GameConstants.PRIMARY_CURRENCY, GameConstants.BASE_GENERATION_RATE);
        generationRates.put(GameConstants.SECONDARY_CURRENCY, GameConstants.BASE_GENERATION_RATE);
        generationRates.put(GameConstants.TERTIARY_CURRENCY, BigDecimal.ZERO); // Special resource, no base generation

        // Set resource categories
        resourceCategories.put(GameConstants.PRIMARY_CURRENCY, ResourceCategory.PRIMARY);
        resourceCategories.put(GameConstants.SECONDARY_CURRENCY, ResourceCategory.SECONDARY);
        resourceCategories.put(GameConstants.TERTIARY_CURRENCY, ResourceCategory.TERTIARY);
    }

    /**
     * Updates all resources based on their generation rates.
     * Called periodically by the game loop to increment resources.
     */
    public void updateResources(BuildingSystem buildingSystem, long deltaTimeMs) {
        logger.debug("Updating resources with delta time: {} ms", deltaTimeMs);
        
        // Convert milliseconds to seconds for rate calculation
        BigDecimal deltaTime = BigDecimal.valueOf(deltaTimeMs).divide(BigDecimal.valueOf(1000), 10, RoundingMode.HALF_UP);
        
        for (Map.Entry<String, BigDecimal> entry : generationRates.entrySet()) {
            String resourceId = entry.getKey();
            BigDecimal baseRate = entry.getValue();
            
            // Apply building multiplier if applicable
            BigDecimal multiplier = buildingSystem.getBuildingMultiplier(resourceId);
            BigDecimal finalRate = baseRate.multiply(multiplier);
            
            // Calculate and add resources
            BigDecimal amount = finalRate.multiply(deltaTime);
            add(resourceId, amount);
        }
    }

    /**
     * Adds resources to the player's balance.
     */
    public void add(String resourceId, BigDecimal amount) {
        BigDecimal current = resources.getOrDefault(resourceId, BigDecimal.ZERO);
        BigDecimal cap = resourceCaps.getOrDefault(resourceId, BigDecimal.valueOf(Double.MAX_VALUE));
        BigDecimal newAmount = current.add(amount);

        if (newAmount.compareTo(cap) > 0) {
            logger.debug("Resource {} hit cap of {}", resourceId, cap);
            newAmount = cap;
        }

        resources.put(resourceId, newAmount);
        logger.debug("Added {} {} (new total: {})", amount, resourceId, newAmount);
    }

    /**
     * Deducts resources from the player's balance.
     */
    public void deductResource(String resourceId, BigDecimal amount) {
        BigDecimal current = resources.getOrDefault(resourceId, BigDecimal.ZERO);
        BigDecimal newAmount = current.subtract(amount);
        
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            logger.debug("Deduction would result in negative balance, setting to 0");
            newAmount = BigDecimal.ZERO;
        }

        resources.put(resourceId, newAmount);
        logger.debug("Deducted {} {} (new total: {})", amount, resourceId, newAmount);
    }

    /**
     * Checks if the player can afford a cost.
     */
    public boolean canAfford(String resourceId, BigDecimal amount) {
        BigDecimal current = resources.getOrDefault(resourceId, BigDecimal.ZERO);
        boolean canAfford = current.compareTo(amount) >= 0;
        logger.debug("Checking if can afford {} {}: {} (current: {})", 
            amount, resourceId, canAfford, current);
        return canAfford;
    }

    /**
     * Spends resources if the player can afford it.
     */
    public void spendResource(String resourceId, BigDecimal amount) {
        if (canAfford(resourceId, amount)) {
            deductResource(resourceId, amount);
            logger.debug("Spent {} {}", amount, resourceId);
        } else {
            logger.debug("Cannot spend {} {} - insufficient funds", amount, resourceId);
        }
    }

    /**
     * Calculates offline earnings for all resources.
     */
    public BigDecimal calculateOfflineEarnings(BuildingSystem buildingSystem, long offlineTimeMs, BigDecimal offlineRate) {
        logger.debug("Calculating offline earnings for {} ms at {}% rate", offlineTimeMs, offlineRate.multiply(BigDecimal.valueOf(100)));
        
        BigDecimal offlineTime = BigDecimal.valueOf(offlineTimeMs).divide(BigDecimal.valueOf(1000), 10, RoundingMode.HALF_UP);
        BigDecimal baseProduction = buildingSystem.getProduction();
        return baseProduction.multiply(offlineTime).multiply(offlineRate);
    }

    /**
     * Gets the current amount of a resource.
     */
    public BigDecimal getAmount(String resourceId) {
        return resources.getOrDefault(resourceId, BigDecimal.ZERO);
    }

    public BigDecimal getResource(String resourceId) {
        return getAmount(resourceId);
    }

    /**
     * Gets the current generation rate of a resource.
     */
    public BigDecimal getGenerationRate(String resourceId) {
        return generationRates.getOrDefault(resourceId, BigDecimal.ZERO);
    }

    /**
     * Upgrades the generation rate of a resource by multiplying it.
     */
    public void upgradeGenerationRate(String resourceId, BigDecimal multiplier) {
        BigDecimal currentRate = generationRates.getOrDefault(resourceId, BigDecimal.ZERO);
        BigDecimal newRate = currentRate.multiply(multiplier);
        logger.debug("Upgrading generation rate for {} from {} to {}", resourceId, currentRate, newRate);
        generationRates.put(resourceId, newRate);
    }

    /**
     * Sets the generation rate for a resource.
     */
    public void setGenerationRate(String resourceId, BigDecimal rate) {
        logger.debug("Setting generation rate for {} to {}", resourceId, rate);
        generationRates.put(resourceId, rate);
    }

    /**
     * Sets a resource cap.
     */
    public void setResourceCap(String resourceId, BigDecimal cap) {
        logger.debug("Setting resource cap for {} to {}", resourceId, cap);
        resourceCaps.put(resourceId, cap);
    }

    /**
     * Gets the total multiplier for a resource by combining all individual multipliers.
     */
    private BigDecimal getTotalMultiplier(String resourceId, BuildingSystem buildingSystem) {
        Map<String, BigDecimal> multipliers = resourceMultipliers.getOrDefault(resourceId, new HashMap<>());
        BigDecimal total = BigDecimal.ONE;
        
        for (BigDecimal multiplier : multipliers.values()) {
            total = total.multiply(multiplier);
        }
        
        // Add building system multipliers if applicable
        BigDecimal buildingMultiplier = buildingSystem.getBuildingMultiplier(resourceId);
        if (buildingMultiplier != null) {
            total = total.multiply(buildingMultiplier);
        }
        
        return total;
    }

    /**
     * Gets the category of a resource.
     */
    public ResourceCategory getResourceCategory(String resourceId) {
        return resourceCategories.getOrDefault(resourceId, ResourceCategory.PRIMARY);
    }

    /**
     * Resets all resources to their initial values.
     */
    public void reset() {
        resources.clear();
        generationRates.clear();
        resourceCaps.clear();
        resourceMultipliers.clear();
        offlineProgressRates.clear();
        clickMultipliers.clear();
        clickMultipliers.put("base", BigDecimal.ONE);
        initializeResources();
        logger.info("Resource system reset to initial state");
    }

    /**
     * Gets the click multiplier value.
     */
    public BigDecimal getClickMultiplier() {
        return clickMultipliers.values().stream()
            .reduce(BigDecimal.ONE, BigDecimal::multiply);
    }

    /**
     * Sets the click multiplier value.
     */
    public void setClickMultiplier(BigDecimal multiplier) {
        logger.debug("Setting click multiplier to {}", multiplier);
        clickMultipliers.put("base", multiplier);
    }

    /**
     * Upgrades the click multiplier by multiplying it with a value.
     */
    public void upgradeClickMultiplier(BigDecimal multiplier) {
        BigDecimal current = clickMultipliers.get("base");
        BigDecimal newValue = current.multiply(multiplier);
        logger.debug("Upgrading click multiplier from {} to {}", current, newValue);
        clickMultipliers.put("base", newValue);
    }

    /**
     * Sets the prestige multiplier for all resources.
     */
    public void setPrestigeMultiplier(BigDecimal multiplier) {
        for (String resourceId : resources.keySet()) {
            Map<String, BigDecimal> multipliers = resourceMultipliers.computeIfAbsent(resourceId, k -> new HashMap<>());
            multipliers.put("prestige", multiplier);
        }
    }

    public void setResource(String resourceId, BigDecimal amount) {
        logger.debug("Setting {} to {}", resourceId, amount);
        resources.put(resourceId, amount);
    }

    public void spend(String resourceId, BigDecimal amount) {
        if (canAfford(resourceId, amount)) {
            BigDecimal current = resources.get(resourceId);
            resources.put(resourceId, current.subtract(amount));
            logger.debug("Spent {} {} (remaining: {})", amount, resourceId, resources.get(resourceId));
        } else {
            logger.warn("Attempted to spend {} {} but only had {}", amount, resourceId, resources.get(resourceId));
        }
    }
}
