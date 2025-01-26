package com.idlegame.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ResourceSystem {
    private final Map<String, BigDecimal> resources;
    private final Map<String, BigDecimal> generationRates;
    private BigDecimal clickMultiplier;

    public ResourceSystem() {
        resources = new HashMap<>();
        generationRates = new HashMap<>();
        clickMultiplier = BigDecimal.ONE;
        initializeResources();
    }

    private void initializeResources() {
        resources.put(GameConstants.PRIMARY_CURRENCY, BigDecimal.ZERO);
        generationRates.put(GameConstants.PRIMARY_CURRENCY, BigDecimal.ZERO);
    }

    public void updateResources() {
        resources.forEach((key, value) -> {
            BigDecimal generation = generationRates.getOrDefault(key, BigDecimal.ZERO);
            resources.put(key, value.add(generation));
        });
    }

    public BigDecimal getResource(String resourceName) {
        return resources.getOrDefault(resourceName, BigDecimal.ZERO);
    }

    public void addResource(String resourceName, BigDecimal amount) {
        BigDecimal effectiveAmount = amount.multiply(clickMultiplier);
        resources.put(resourceName, resources.getOrDefault(resourceName, BigDecimal.ZERO).add(effectiveAmount));
    }

    public BigDecimal getClickMultiplier() {
        return clickMultiplier;
    }

    public void setClickMultiplier(BigDecimal multiplier) {
        this.clickMultiplier = multiplier;
    }

    public void setGenerationRate(String resourceName, BigDecimal rate) {
        generationRates.put(resourceName, rate);
    }

    public void upgradeGenerationRate(String resourceName, BigDecimal multiplier) {
        BigDecimal currentRate = generationRates.getOrDefault(resourceName, BigDecimal.ZERO);
        generationRates.put(resourceName, currentRate.multiply(multiplier));
    }

    public BigDecimal getGenerationRate(String resourceName) {
        return generationRates.getOrDefault(resourceName, BigDecimal.ZERO);
    }

    public boolean canAfford(String resourceName, BigDecimal amount) {
        return resources.getOrDefault(resourceName, BigDecimal.ZERO).compareTo(amount) >= 0;
    }

    public void spendResource(String resourceName, BigDecimal amount) {
        if (canAfford(resourceName, amount)) {
            resources.put(resourceName, resources.get(resourceName).subtract(amount));
        }
    }
}
