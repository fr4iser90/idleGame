package com.idlegame.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;


public class GameManager {
    private static final Logger logger = LoggerFactory.getLogger(GameManager.class);
    private final ResourceSystem resourceSystem;
    private final UpgradeSystem upgradeSystem;
    private final BuildingSystem buildingSystem;
    private GameState gameState;
    private boolean isRunning;

    public GameManager() {
        logger.info("Initializing GameManager");
        this.resourceSystem = new ResourceSystem();
        this.upgradeSystem = new UpgradeSystem(resourceSystem);
        this.buildingSystem = new BuildingSystem(resourceSystem);
        this.gameState = new GameState();
        this.isRunning = false;
        logger.debug("GameManager initialized with resourceSystem: {}, upgradeSystem: {}, buildingSystem: {}", 
            resourceSystem, upgradeSystem, buildingSystem);
    }

    public void clickAction() {
        logger.debug("Processing click action");
        BigDecimal clickValue = GameConstants.BASE_CLICK_POWER
            .multiply(resourceSystem.getClickMultiplier())
            .multiply(buildingSystem.getClickMultiplier());
        resourceSystem.addResource(GameConstants.PRIMARY_CURRENCY, clickValue);
        logger.debug("Click added {} (x{} from upgrades, x{} from buildings)", 
            clickValue, 
            resourceSystem.getClickMultiplier(),
            buildingSystem.getClickMultiplier());
    }

    public void deductResources(BigDecimal amount) {
        logger.debug("Deducting {} resources", amount);
        resourceSystem.deductResource(GameConstants.PRIMARY_CURRENCY, amount);
        logger.trace("Resources after deduction: {}", getPrimaryResource());
    }

    public BigDecimal getPrimaryResource() {
        return resourceSystem.getResource(GameConstants.PRIMARY_CURRENCY);
    }

    public void upgradeGenerationRate(double multiplier) {
        resourceSystem.upgradeGenerationRate(
            GameConstants.PRIMARY_CURRENCY, 
            BigDecimal.valueOf(multiplier)
        );
    }

    public void startGame() {
        logger.info("Starting game");
        isRunning = true;
        runGameLoop();
        logger.info("Game loop started");
    }

    private void runGameLoop() {
        logger.debug("Entering game loop");
        while (isRunning) {
            updateGame();
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

    private void updateGame() {
        logger.trace("Updating game state");
        resourceSystem.updateResources(buildingSystem);
        logger.trace("Resources updated");
        upgradeSystem.applyUpgradeEffects();
        logger.trace("Upgrades applied");
        checkForUnlocks();
        if (gameState.shouldAutoSave()) {
            logger.debug("Auto-saving game state");
            saveGameState();
        }
    }

    public void updateResources(BuildingSystem buildingSystem) {
        resourceSystem.updateResources(buildingSystem);
    }

    private void checkForUnlocks() {
        // Logic for checking unlock conditions
    }

    private void saveGameState() {
        // Persistence logic
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
}
