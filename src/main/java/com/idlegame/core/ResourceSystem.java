package com.idlegame.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages all game resources including storage, generation rates, and transactions.
 * Uses BigDecimal for precise calculations to prevent floating-point errors.
 */
public class ResourceSystem {
    private final Map<String, BigDecimal> resources;
    private final Map<String, BigDecimal> generationRates;
    private final Map<String, BigDecimal> clickMultipliers = new HashMap<>();

    /**
     * Initializes the resource system with default values.
     * Creates empty maps for resources and generation rates,
     * and sets initial click multiplier to 1.
     */
    public ResourceSystem() {
        resources = new HashMap<>();
        generationRates = new HashMap<>();
        // Initialize default click multiplier
        clickMultipliers.put("base", BigDecimal.ONE);
        initializeResources();
    }

    private void initializeResources() {
        resources.put(GameConstants.PRIMARY_CURRENCY, BigDecimal.ZERO);
        generationRates.put(GameConstants.PRIMARY_CURRENCY, BigDecimal.ZERO);
    }

    /**
     * Updates all resources based on their generation rates.
     * Called periodically by the game loop to increment resources.
     */
    public void updateResources(BuildingSystem buildingSystem) {
        BigDecimal baseRate = generationRates.getOrDefault(GameConstants.PRIMARY_CURRENCY, BigDecimal.ZERO);
        BigDecimal multiplier = buildingSystem.getBuildingMultiplier(GameConstants.PRIMARY_CURRENCY);
        resources.computeIfPresent(GameConstants.PRIMARY_CURRENCY, 
            (k, v) -> v.add(baseRate.multiply(multiplier)));
    }

    /**
     * Gets the current amount of a specific resource.
     * @param resourceName The name of the resource to query
     * @return Current amount of the resource, or zero if not found
     */
    public BigDecimal getResource(String resourceName) {
        return resources.getOrDefault(resourceName, BigDecimal.ZERO);
    }

    /**
     * Adds exactly 1 unit of a resource, ignoring click multipliers.
     * @param resourceName The name of the resource to add to
     */
    public void addResource(String resourceName, BigDecimal amount) {
        resources.put(resourceName, resources.getOrDefault(resourceName, BigDecimal.ZERO).add(amount));
    }

    public void deductResource(String resourceName, BigDecimal amount) {
        BigDecimal current = resources.getOrDefault(resourceName, BigDecimal.ZERO);
        if (current.compareTo(amount) >= 0) {
            resources.put(resourceName, current.subtract(amount));
        } else {
            resources.put(resourceName, BigDecimal.ZERO);
        }
    }

    /**
     * Predicts the cost of an upgrade after applying multipliers.
     * @param baseCost The base cost before multipliers
     * @param multiplier The upgrade multiplier
     * @param level The current upgrade level
     * @return Predicted cost as BigDecimal
     */
    public BigDecimal predictCost(BigDecimal baseCost, BigDecimal multiplier, int level) {
        return baseCost.multiply(multiplier.pow(level));
    }

    /**
     * Gets the current click multiplier.
     * @return Current click multiplier value
     */
    public BigDecimal getClickMultiplier() {
        return BigDecimal.ONE.add(clickMultipliers.values().stream()
            .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    /**
     * Sets the click multiplier value.
     * @param multiplier New multiplier value
     */
    public void addClickMultiplier(String id, BigDecimal multiplier) {
        clickMultipliers.put(id, multiplier);
    }

    public void setClickMultiplier(BigDecimal multiplier) {
        clickMultipliers.put("base", multiplier);
    }

    /**
     * Sets the generation rate for a specific resource.
     * @param resourceName Name of the resource
     * @param rate New generation rate per second
     */
    public void setGenerationRate(String resourceName, BigDecimal rate) {
        generationRates.put(resourceName, rate);
    }

    /**
     * Upgrades the generation rate of a resource by multiplying it.
     * @param resourceName Name of the resource to upgrade
     * @param multiplier Multiplier to apply to current rate
     */
    public void upgradeGenerationRate(String resourceName, BigDecimal multiplier) {
        BigDecimal currentRate = generationRates.getOrDefault(resourceName, BigDecimal.ZERO);
        generationRates.put(resourceName, currentRate.multiply(multiplier));
    }

    /**
     * Gets the current generation rate of a resource.
     * @param resourceName Name of the resource
     * @return Current generation rate, or zero if not found
     */
    public BigDecimal getGenerationRate(String resourceName) {
        return generationRates.getOrDefault(resourceName, BigDecimal.ZERO);
    }

    /**
     * Checks if the player can afford a purchase.
     * @param resourceName Name of the resource to check
     * @param amount Required amount
     * @return true if player has enough resources, false otherwise
     */
    public boolean canAfford(String resourceName, BigDecimal amount) {
        return resources.getOrDefault(resourceName, BigDecimal.ZERO).compareTo(amount) >= 0;
    }

    /**
     * Deducts resources from the player's balance.
     * @param resourceName Name of the resource to spend
     * @param amount Amount to deduct
     * @throws IllegalStateException if player cannot afford the purchase
     */
    public void spendResource(String resourceName, BigDecimal amount) {
        if (canAfford(resourceName, amount)) {
            resources.put(resourceName, resources.get(resourceName).subtract(amount));
        }
    }
}
