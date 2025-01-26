package com.idlegame.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BuildingSystem {
    private final Map<String, Building> buildings;
    private final ResourceSystem resourceSystem;
    
    public BuildingSystem(ResourceSystem resourceSystem) {
        this.resourceSystem = resourceSystem;
        this.buildings = new HashMap<>();
        initializeBuildings();
    }
    
    private void initializeBuildings() {
        // Initialize marijuana strain buildings
        buildings.put(GameConstants.BUILDING_LOW_QUALITY, new Building(
            "Low Quality",
            GameConstants.LOW_QUALITY_BASE_COST,
            GameConstants.LOW_QUALITY_GENERATION,
            GameConstants.LOW_QUALITY_COST_MULTIPLIER
        ));
        
        buildings.put(GameConstants.BUILDING_MEDIUM_QUALITY, new Building(
            "Medium Quality", 
            GameConstants.MEDIUM_QUALITY_BASE_COST,
            GameConstants.MEDIUM_QUALITY_GENERATION,
            GameConstants.MEDIUM_QUALITY_COST_MULTIPLIER
        ));
        
        buildings.put(GameConstants.BUILDING_HIGH_QUALITY, new Building(
            "High Quality",
            GameConstants.HIGH_QUALITY_BASE_COST,
            GameConstants.HIGH_QUALITY_GENERATION,
            GameConstants.HIGH_QUALITY_COST_MULTIPLIER
        ));
    }
    
    public void update() {
        buildings.values().forEach(building -> {
            if (building.getCount() > 0) {
                resourceSystem.addResource(
                    GameConstants.PRIMARY_CURRENCY,
                    building.getGenerationRate()
                );
            }
        });
    }
    
    public boolean canAffordBuilding(String buildingId) {
        Building building = buildings.get(buildingId);
        return resourceSystem.canAfford(
            GameConstants.PRIMARY_CURRENCY,
            building.getNextCost()
        );
    }
    
    public void purchaseBuilding(String buildingId) {
        if (canAffordBuilding(buildingId)) {
            Building building = buildings.get(buildingId);
            resourceSystem.spendResource(
                GameConstants.PRIMARY_CURRENCY,
                building.getNextCost()
            );
            building.purchase();
        }
    }
    
    public Map<String, Building> getBuildings() {
        return buildings;
    }
    
    private static class Building {
        private final String name;
        private final BigDecimal baseCost;
        private final BigDecimal baseGeneration;
        private final BigDecimal costMultiplier;
        private int count;
        
        public Building(String name, BigDecimal baseCost, 
                       BigDecimal baseGeneration, BigDecimal costMultiplier) {
            this.name = name;
            this.baseCost = baseCost;
            this.baseGeneration = baseGeneration;
            this.costMultiplier = costMultiplier;
            this.count = 0;
        }
        
        public BigDecimal getNextCost() {
            return baseCost.multiply(
                costMultiplier.pow(count)
            );
        }
        
        public BigDecimal getGenerationRate() {
            return baseGeneration.multiply(
                new BigDecimal(count)
            );
        }
        
        public void purchase() {
            count++;
        }
        
        public int getCount() {
            return count;
        }
        
        public String getName() {
            return name;
        }
    }
}
