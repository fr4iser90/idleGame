package com.idlegame.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuildingSystem {
    private static final Logger logger = LoggerFactory.getLogger(BuildingSystem.class);
    private final Map<String, Building> buildings;
    private final ResourceSystem resourceSystem;
    private BigDecimal prestigeMultiplier = BigDecimal.ONE;
    private BigDecimal clickMultiplier = BigDecimal.ONE;

    public BuildingSystem(ResourceSystem resourceSystem) {
        this.resourceSystem = resourceSystem;
        this.buildings = new HashMap<>();
        initializeBuildings();
    }

    private void initializeBuildings() {
        for (int i = 0; i < GameConstants.BUILDING_IDS.length; i++) {
            String buildingId = GameConstants.BUILDING_IDS[i];
            buildings.put(buildingId, new Building(
                buildingId,
                GameConstants.BUILDING_BASE_COSTS[i],
                GameConstants.BUILDING_COST_MULTIPLIERS[i],
                GameConstants.BUILDING_BASE_PRODUCTION[i]
            ));
        }
    }

    public void purchaseBuilding(String buildingId) {
        Building building = buildings.get(buildingId);
        if (building == null) {
            logger.error("Attempted to purchase non-existent building: {}", buildingId);
            return;
        }

        BigDecimal cost = building.getNextCost();
        if (resourceSystem.canAfford(GameConstants.PRIMARY_CURRENCY, cost)) {
            resourceSystem.spend(GameConstants.PRIMARY_CURRENCY, cost);
            building.incrementCount();
            logger.info("Purchased building: {} for {}", buildingId, cost);
        } else {
            logger.debug("Cannot afford building: {} (cost: {})", buildingId, cost);
        }
    }

    public BigDecimal getProduction() {
        BigDecimal totalProduction = BigDecimal.ZERO;
        for (Building building : buildings.values()) {
            totalProduction = totalProduction.add(building.getCurrentProduction());
        }
        return totalProduction.multiply(prestigeMultiplier);
    }

    public BigDecimal getBuildingMultiplier(String resourceId) {
        BigDecimal multiplier = BigDecimal.ONE;
        for (Building building : buildings.values()) {
            multiplier = multiplier.multiply(building.getMultiplierEffect());
        }
        return multiplier;
    }

    public void setPrestigeMultiplier(BigDecimal multiplier) {
        this.prestigeMultiplier = multiplier;
    }

    public BigDecimal getClickMultiplier() {
        return clickMultiplier;
    }

    public void setClickMultiplier(BigDecimal multiplier) {
        this.clickMultiplier = multiplier;
    }

    public Building getBuilding(String buildingId) {
        return buildings.get(buildingId);
    }

    public int getTotalBuildingCount() {
        int total = 0;
        for (Building building : buildings.values()) {
            total += building.getCount();
        }
        return total;
    }

    public void reset() {
        buildings.clear();
        initializeBuildings();
        prestigeMultiplier = BigDecimal.ONE;
        clickMultiplier = BigDecimal.ONE;
    }

    public void restoreState(BuildingSystem other) {
        this.prestigeMultiplier = other.prestigeMultiplier;
        this.clickMultiplier = other.clickMultiplier;
        this.buildings.clear();
        this.buildings.putAll(other.buildings);
    }

    public void applyEfficiencyBonus(BigDecimal bonus) {
        for (Building building : buildings.values()) {
            building.applyEfficiencyBonus(bonus);
        }
    }

    public static class Building {
        private final String id;
        private final BigDecimal baseCost;
        private final BigDecimal costMultiplier;
        private final BigDecimal baseProduction;
        private int count;
        private BigDecimal efficiencyBonus;

        public Building(String id, BigDecimal baseCost, BigDecimal costMultiplier, BigDecimal baseProduction) {
            this.id = id;
            this.baseCost = baseCost;
            this.costMultiplier = costMultiplier;
            this.baseProduction = baseProduction;
            this.count = 0;
            this.efficiencyBonus = BigDecimal.ONE;
        }

        public String getId() {
            return id;
        }

        public int getCount() {
            return count;
        }

        public void incrementCount() {
            count++;
        }

        public BigDecimal getNextCost() {
            return baseCost.multiply(costMultiplier.pow(count));
        }

        public BigDecimal getCurrentProduction() {
            return baseProduction
                .multiply(BigDecimal.valueOf(count))
                .multiply(efficiencyBonus);
        }

        public BigDecimal getMultiplierEffect() {
            return efficiencyBonus;
        }

        public void applyEfficiencyBonus(BigDecimal bonus) {
            this.efficiencyBonus = this.efficiencyBonus.multiply(bonus);
        }
    }
}
