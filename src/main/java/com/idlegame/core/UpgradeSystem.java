package com.idlegame.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class UpgradeSystem {
    private final Map<String, Upgrade> upgrades;
    private final ResourceSystem resourceSystem;

    public UpgradeSystem(ResourceSystem resourceSystem) {
        this.upgrades = new HashMap<>();
        this.resourceSystem = resourceSystem;
        initializeUpgrades();
    }

    private void initializeUpgrades() {
        // Initialize base upgrades
        addUpgrade(new Upgrade(
            "basic_click",
            "Basic Click",
            BigDecimal.valueOf(10),
            UpgradeType.CLICK,
            new UpgradeEffect(1.0, 0),
            resourceSystem
        ));
        
        // Add passive income upgrade
        addUpgrade(new Upgrade(
            "basic_passive",
            "Basic Passive Income",
            BigDecimal.valueOf(50),
            UpgradeType.PASSIVE,
            new UpgradeEffect(0.1, 0.05),
            resourceSystem
        ));
    }

    public void applyUpgradeEffects() {
        // Reset click multiplier before applying upgrades
        resourceSystem.setClickMultiplier(BigDecimal.ONE);
        
        // Apply all active upgrades
        upgrades.values().forEach(upgrade -> {
            if (upgrade.getLevel() > 0) {
                upgrade.applyEffect(resourceSystem);
            }
        });
    }

    public boolean purchaseUpgrade(String upgradeId) {
        Upgrade upgrade = upgrades.get(upgradeId);
        if (upgrade != null && resourceSystem.canAfford(GameConstants.PRIMARY_CURRENCY, upgrade.getCost())) {
            // Deduct the cost first
            resourceSystem.spendResource(GameConstants.PRIMARY_CURRENCY, upgrade.getCost());
            
            // Apply the upgrade
            upgrade.purchase();
            
            // If it's a passive upgrade, update generation rate
            if (upgrade.getType() == UpgradeType.PASSIVE) {
                BigDecimal currentRate = resourceSystem.getGenerationRate(GameConstants.PRIMARY_CURRENCY);
                BigDecimal newRate = currentRate.add(BigDecimal.valueOf(upgrade.effect.calculateEffect(upgrade.getLevel())));
                resourceSystem.setGenerationRate(GameConstants.PRIMARY_CURRENCY, newRate);
            }
            return true;
        }
        return false;
    }

    public void addUpgrade(Upgrade upgrade) {
        upgrades.put(upgrade.getId(), upgrade);
    }

    public Upgrade getUpgrade(String upgradeId) {
        return upgrades.get(upgradeId);
    }

    public enum UpgradeType {
        CLICK,
        PASSIVE,
        UNLOCK
    }

    public class Upgrade {
        private final String id;
        private final String name;
        private BigDecimal cost;
        private final UpgradeType type;
        private final UpgradeEffect effect;
        private int level = 0;
        private final ResourceSystem resourceSystem;

        public Upgrade(String id, String name, BigDecimal cost, UpgradeType type, UpgradeEffect effect, ResourceSystem resourceSystem) {
            this.id = id;
            this.name = name;
            this.cost = cost;
            this.type = type;
            this.effect = effect;
            this.resourceSystem = resourceSystem;
        }

        public void purchase() {
            level++;
            cost = cost.multiply(BigDecimal.valueOf(1.15)); // 15% cost increase per level
        }

        public void applyEffect(ResourceSystem resourceSystem) {
            switch (type) {
                case CLICK:
                    BigDecimal currentClick = resourceSystem.getClickMultiplier();
                    BigDecimal newClick = currentClick.add(BigDecimal.valueOf(effect.calculateEffect(level)));
                    resourceSystem.setClickMultiplier(newClick);
                    break;
                case PASSIVE:
                    BigDecimal currentRate = resourceSystem.getGenerationRate(GameConstants.PRIMARY_CURRENCY);
                    BigDecimal newRate = currentRate.add(BigDecimal.valueOf(effect.calculateEffect(level)));
                    resourceSystem.setGenerationRate(GameConstants.PRIMARY_CURRENCY, newRate);
                    break;
                case UNLOCK:
                    // Handle unlocks
                    break;
            }
        }

        // Getters
        public String getId() { return id; }
        public String getName() { return name; }
        public BigDecimal getCost() { return cost; }
        public UpgradeType getType() { return type; }
        public int getLevel() { return level; }
    }

    public static class UpgradeEffect {
        private final double baseValue;
        private final double scalingFactor;

        public UpgradeEffect(double baseValue, double scalingFactor) {
            this.baseValue = baseValue;
            this.scalingFactor = scalingFactor;
        }

        public double calculateEffect(int level) {
            return baseValue + (scalingFactor * level);
        }
    }
}
