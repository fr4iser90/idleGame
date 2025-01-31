package com.idlegame.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.time.Instant;
import com.idlegame.core.BuildingSystem.Building;

/**
 * GameManager is the central class that manages the game state and updates.
 */
public class GameManager {
    private static final Logger logger = LoggerFactory.getLogger(GameManager.class);
    private final ResourceSystem resourceSystem;
    private final UpgradeSystem upgradeSystem;
    private final BuildingSystem buildingSystem;
    private final AchievementSystem achievementSystem;
    private final PrestigeSystem prestigeSystem;
    private GameState gameState;
    private boolean isRunning;
    private Instant lastUpdateTime;
    private long lastSaveTime;
    private boolean initialized;

    public GameManager() {
        logger.info("Initializing GameManager");
        this.resourceSystem = new ResourceSystem();
        this.upgradeSystem = new UpgradeSystem(resourceSystem);
        this.buildingSystem = new BuildingSystem(resourceSystem);
        this.achievementSystem = new AchievementSystem(resourceSystem, buildingSystem);
        this.prestigeSystem = new PrestigeSystem(resourceSystem, buildingSystem);
        this.gameState = new GameState();
        this.isRunning = false;
        this.lastUpdateTime = Instant.now();
        this.lastSaveTime = System.currentTimeMillis();
        this.initialized = false;
        logger.debug("GameManager initialized with all systems");
    }

    public void initialize() {
        if (!initialized) {
            logger.info("Initializing game manager");
            resourceSystem.reset();
            buildingSystem.reset();
            achievementSystem.reset();
            initialized = true;
        }
    }

    public void clickMainAction() {
        logger.debug("Processing click action");
        BigDecimal baseClick = GameConstants.BASE_CLICK_POWER;
        BigDecimal clickMultiplier = resourceSystem.getClickMultiplier();
        BigDecimal buildingMultiplier = buildingSystem.getClickMultiplier();
        BigDecimal prestigeMultiplier = prestigeSystem.getPrestigeMultiplier();
        BigDecimal clickValue = baseClick.multiply(clickMultiplier).multiply(buildingMultiplier).multiply(prestigeMultiplier);
        
        resourceSystem.add(GameConstants.PRIMARY_CURRENCY, clickValue);
        achievementSystem.registerClick();
        logger.debug("Click added {} (with prestige multiplier: {})", clickValue, prestigeMultiplier);
    }

    public void deductResources(BigDecimal amount) {
        resourceSystem.spend(GameConstants.PRIMARY_CURRENCY, amount);
    }

    public void upgradeGenerationRate(BigDecimal multiplier) {
        resourceSystem.upgradeGenerationRate(GameConstants.PRIMARY_CURRENCY, multiplier);
    }

    public BigDecimal getPrimaryResource() {
        return resourceSystem.getResource(GameConstants.PRIMARY_CURRENCY);
    }

    public void startGame() {
        logger.info("Starting game");
        isRunning = true;
        Thread gameThread = new Thread(this::runGameLoop);
        gameThread.setDaemon(true);
        gameThread.start();
        logger.info("Game loop started in background thread");
    }

    private void runGameLoop() {
        logger.debug("Entering game loop");
        while (isRunning) {
            update();
            try {
                Thread.sleep(GameConstants.GAME_TICK_DURATION);
            } catch (InterruptedException e) {
                logger.error("Game loop interrupted", e);
                Thread.currentThread().interrupt();
                stopGame();
            }
        }
        logger.debug("Exiting game loop");
    }

    private void update() {
        logger.trace("Updating game state");
        Instant currentTime = Instant.now();
        long deltaTime = currentTime.toEpochMilli() - lastUpdateTime.toEpochMilli();
        
        // Update resource generation from buildings
        BigDecimal production = buildingSystem.getProduction();
        BigDecimal deltaSeconds = BigDecimal.valueOf(deltaTime).divide(BigDecimal.valueOf(1000), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal generated = production.multiply(deltaSeconds);
        
        if (generated.compareTo(BigDecimal.ZERO) > 0) {
            resourceSystem.add(GameConstants.PRIMARY_CURRENCY, generated);
            achievementSystem.addResourceGain(generated);
        }
        
        lastUpdateTime = currentTime;

        // Update all systems
        resourceSystem.updateResources(buildingSystem, deltaTime);
        achievementSystem.checkAchievements();

        // Auto-save check
        if (currentTime.toEpochMilli() - lastSaveTime >= GameConstants.AUTOSAVE_INTERVAL) {
            saveGameState();
            lastSaveTime = currentTime.toEpochMilli();
        }

        logger.trace("Game state updated");
    }

    public void updateResources(BuildingSystem buildingSystem) {
        Instant currentTime = Instant.now();
        long deltaTime = currentTime.toEpochMilli() - lastUpdateTime.toEpochMilli();
        lastUpdateTime = currentTime;
        resourceSystem.updateResources(buildingSystem, deltaTime);
    }

    private void checkForUnlocks() {
        // Logic for checking unlock conditions
    }

    private void saveGameState() {
        logger.info("Saving game state");
        // Save building system
        gameState.putState("buildingSystem", buildingSystem);

        // Save resources
        Map<String, BigDecimal> resourceAmounts = new HashMap<>();
        resourceAmounts.put(GameConstants.PRIMARY_CURRENCY, getPrimaryResource());
        gameState.putState("resources", resourceAmounts);

        // Save prestige data
        gameState.putState("prestigePoints", prestigeSystem.getPrestigePoints());
        gameState.putState("prestigeMultiplier", prestigeSystem.getPrestigeMultiplier());

        gameState.save();
        logger.info("Game state saved successfully");
    }

    public void loadGame() {
        logger.info("Loading game state");
        GameState loadedState = gameState.load();
        if (loadedState != null) {
            // Restore building system state
            BuildingSystem loadedBuildingSystem = (BuildingSystem) loadedState.getState("buildingSystem");
            if (loadedBuildingSystem != null) {
                buildingSystem.restoreState(loadedBuildingSystem);
            }

            // Restore resources
            @SuppressWarnings("unchecked")
            Map<String, BigDecimal> resources = (Map<String, BigDecimal>) loadedState.getState("resources");
            if (resources != null) {
                resources.forEach((resource, amount) -> 
                    resourceSystem.setResource(resource, amount)
                );
            }

            // Restore prestige data
            BigDecimal prestigePoints = (BigDecimal) loadedState.getState("prestigePoints");
            BigDecimal prestigeMultiplier = (BigDecimal) loadedState.getState("prestigeMultiplier");
            if (prestigePoints != null) {
                prestigeSystem.setPrestigePoints(prestigePoints);
            }
            if (prestigeMultiplier != null) {
                prestigeSystem.setPrestigeMultiplier(prestigeMultiplier);
            }

            long offlineTime = System.currentTimeMillis() - loadedState.getLastSaveTime().toEpochMilli();
            if (offlineTime > 0) {
                calculateOfflineProgress(offlineTime);
            }
            gameState = loadedState;
            logger.info("Game state loaded successfully");
        }
    }

    private void calculateOfflineProgress(long offlineTime) {
        logger.info("Calculating offline progress for {} ms", offlineTime);
        // Cap offline time to maximum allowed
        offlineTime = Math.min(offlineTime, GameConstants.MAX_OFFLINE_TIME);

        // Calculate offline earnings with reduced rate
        BigDecimal offlineRate = BigDecimal.valueOf(GameConstants.OFFLINE_PROGRESS_RATE);
        BigDecimal offlineEarnings = resourceSystem.calculateOfflineEarnings(
            buildingSystem, 
            offlineTime,
            offlineRate
        );

        resourceSystem.add(GameConstants.PRIMARY_CURRENCY, offlineEarnings);
        logger.info("Added offline earnings: {}", offlineEarnings);
    }

    public void stopGame() {
        logger.info("Stopping game");
        isRunning = false;
        logger.info("Game stopped");
    }

    public ResourceSystem getResourceSystem() {
        return resourceSystem;
    }

    public UpgradeSystem getUpgradeSystem() {
        return upgradeSystem;
    }

    public BuildingSystem getBuildingSystem() {
        return buildingSystem;
    }

    public boolean canPrestige() {
        return prestigeSystem.canPrestige();
    }

    public void prestige() {
        if (canPrestige()) {
            prestigeSystem.prestige();
            logger.info("Prestige completed successfully");
        }
    }

    public Set<String> getUnlockedAchievements() {
        return achievementSystem.getUnlockedAchievements();
    }

    public BigDecimal getPrestigePoints() {
        return prestigeSystem.getPrestigePoints();
    }

    public BigDecimal getNextPrestigePointsGain() {
        return prestigeSystem.getNextPrestigePointsGain();
    }
}
