package com.idlegame.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.*;

public class AchievementSystem {
    private static final Logger logger = LoggerFactory.getLogger(AchievementSystem.class);
    private final Set<String> unlockedAchievements;
    private final ResourceSystem resourceSystem;
    private final BuildingSystem buildingSystem;
    private long totalClicks;
    private BigDecimal totalResourcesGained;
    
    public AchievementSystem(ResourceSystem resourceSystem, BuildingSystem buildingSystem) {
        this.unlockedAchievements = new HashSet<>();
        this.resourceSystem = resourceSystem;
        this.buildingSystem = buildingSystem;
        this.totalClicks = 0;
        this.totalResourcesGained = BigDecimal.ZERO;
        logger.info("Achievement system initialized");
    }
    
    public void registerClick() {
        totalClicks++;
        checkClickAchievements();
    }
    
    public void checkAllAchievements() {
        checkClickAchievements();
        checkResourceAchievements();
        checkBuildingAchievements();
    }
    
    private void checkClickAchievements() {
        for (int threshold : GameConstants.CLICK_ACHIEVEMENT_THRESHOLDS) {
            String achievementId = "clicks_" + threshold;
            if (!unlockedAchievements.contains(achievementId) && totalClicks >= threshold) {
                unlockAchievement(achievementId);
            }
        }
    }
    
    private void checkResourceAchievements() {
        for (BigDecimal threshold : GameConstants.RESOURCE_ACHIEVEMENT_THRESHOLDS) {
            String achievementId = "resources_" + threshold;
            if (!unlockedAchievements.contains(achievementId) && totalResourcesGained.compareTo(threshold) >= 0) {
                unlockAchievement(achievementId);
            }
        }
    }
    
    private void checkBuildingAchievements() {
        int totalBuildings = buildingSystem.getTotalBuildingCount();
        for (int threshold : GameConstants.BUILDING_COUNT_ACHIEVEMENTS) {
            String achievementId = "buildings_" + threshold;
            if (!unlockedAchievements.contains(achievementId) && totalBuildings >= threshold) {
                unlockAchievement(achievementId);
            }
        }
    }
    
    private void unlockAchievement(String achievementId) {
        if (unlockedAchievements.add(achievementId)) {
            logger.info("Achievement unlocked: {}", achievementId);
            // Apply achievement rewards here
            applyAchievementReward(achievementId);
        }
    }
    
    private void applyAchievementReward(String achievementId) {
        // Different rewards based on achievement type
        if (achievementId.startsWith("clicks_")) {
            resourceSystem.upgradeClickMultiplier(BigDecimal.valueOf(1.1));
        } else if (achievementId.startsWith("resources_")) {
            resourceSystem.upgradeGenerationRate(GameConstants.PRIMARY_CURRENCY, BigDecimal.valueOf(1.05));
        } else if (achievementId.startsWith("buildings_")) {
            buildingSystem.applyEfficiencyBonus(BigDecimal.valueOf(1.1));
        }
    }
    
    public void checkAchievements() {
        checkClickAchievements();
        checkResourceAchievements();
        checkBuildingAchievements();
    }
    
    public Set<String> getUnlockedAchievements() {
        return Collections.unmodifiableSet(unlockedAchievements);
    }
    
    public long getTotalClicks() {
        return totalClicks;
    }
    
    public void onResourceGain(BigDecimal amount) {
        totalResourcesGained = totalResourcesGained.add(amount);
        checkResourceAchievements();
    }
    
    public boolean hasAchievement(String achievementId) {
        return unlockedAchievements.contains(achievementId);
    }
    
    public void reset() {
        unlockedAchievements.clear();
        totalClicks = 0;
        totalResourcesGained = BigDecimal.ZERO;
    }
    
    private void checkBuildingAchievementsNew() {
        for (String buildingId : GameConstants.BUILDING_IDS) {
            BuildingSystem.Building building = buildingSystem.getBuilding(buildingId);
            if (building != null) {
                int count = building.getCount();
                for (int threshold : GameConstants.ACHIEVEMENT_THRESHOLDS) {
                    if (count >= threshold) {
                        String achievementId = buildingId + "_" + threshold;
                        unlockAchievement(achievementId);
                    }
                }
            }
        }
    }
    
    private void checkClickAchievementsNew() {
        for (int threshold : GameConstants.CLICK_ACHIEVEMENT_THRESHOLDS) {
            if (totalClicks >= threshold) {
                String achievementId = "clicks_" + threshold;
                unlockAchievement(achievementId);
            }
        }
    }
    
    private void checkResourceAchievementsNew() {
        for (BigDecimal threshold : GameConstants.RESOURCE_ACHIEVEMENT_THRESHOLDS) {
            if (totalResourcesGained.compareTo(threshold) >= 0) {
                String achievementId = "resources_" + threshold;
                unlockAchievement(achievementId);
            }
        }
    }
    
    private void applyAchievementBonus(String achievementId) {
        // Apply achievement-specific bonuses here
        if (achievementId.startsWith("clicks_")) {
            buildingSystem.setClickMultiplier(buildingSystem.getClickMultiplier().multiply(GameConstants.CLICK_ACHIEVEMENT_BONUS));
        }
    }

    public void addResourceGain(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            totalResourcesGained = totalResourcesGained.add(amount);
            checkResourceAchievements();
        }
    }
}
