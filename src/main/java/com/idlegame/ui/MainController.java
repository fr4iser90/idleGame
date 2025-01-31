package com.idlegame.ui;

import com.idlegame.core.GameManager;
import com.idlegame.core.GameConstants;
import com.idlegame.core.BuildingSystem;
import com.idlegame.ui.components.BuildingComponent;
import com.idlegame.ui.components.UpgradeComponent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.NumberFormat;

public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private GameManager gameManager;
    private List<BuildingComponent> buildingComponents;
    private List<UpgradeComponent> upgradeComponents;
    private NumberFormat numberFormat = NumberFormat.getInstance();

    @FXML private Label resourceLabel;
    @FXML private Label perSecondLabel;
    @FXML private Label clickPowerLabel;
    @FXML private Label prestigeLabel;
    @FXML private Label prestigePointsLabel;
    @FXML private Button clickButton;
    @FXML private VBox buildingsContainer;
    @FXML private GridPane upgradesGrid;
    @FXML private VBox achievementContainer;

    @FXML
    private void initialize() {
        logger.info("Initializing MainController");
        gameManager = new GameManager();
        gameManager.initialize();

        buildingComponents = new ArrayList<>();
        upgradeComponents = new ArrayList<>();

        // Initialize buildings dynamically
        initializeBuildings();
        setupUI();
        initializeUpgrades();

        // Load saved game state and start game loop
        gameManager.loadGame();
        gameManager.startGame();
        startResourceUpdate();

        updateDisplay();
        logger.info("MainController initialization complete");
    }

    private void initializeBuildings() {
        // Clear existing components
        buildingsContainer.getChildren().clear();
        buildingComponents.clear();

        // Create building components dynamically
        for (String buildingId : GameConstants.BUILDING_IDS) {
            BuildingSystem.Building building = gameManager.getBuildingSystem().getBuilding(buildingId);
            if (building != null) {
                BuildingComponent component = new BuildingComponent(
                    buildingId,
                    building.getId(),
                    building.getNextCost(),
                    building.getCurrentProduction(),
                    amount -> purchaseBuilding(buildingId, amount)
                );
                buildingComponents.add(component);
                buildingsContainer.getChildren().add(component);
            }
        }
    }

    private void purchaseBuilding(String buildingId, int amount) {
        BuildingSystem.Building building = gameManager.getBuildingSystem().getBuilding(buildingId);
        if (building != null) {
            if (amount == -1) {
                // Buy max implementation
                while (gameManager.getResourceSystem().canAfford(GameConstants.PRIMARY_CURRENCY, building.getNextCost())) {
                    gameManager.getBuildingSystem().purchaseBuilding(buildingId);
                }
            } else {
                // Buy specific amount
                for (int i = 0; i < amount && gameManager.getResourceSystem().canAfford(GameConstants.PRIMARY_CURRENCY, building.getNextCost()); i++) {
                    gameManager.getBuildingSystem().purchaseBuilding(buildingId);
                }
            }
            updateDisplay();
        }
    }

    private void setupUI() {
        // Click button action
        clickButton.setOnAction(event -> {
            logger.debug("Click button pressed");
            gameManager.clickMainAction();
            updateResourceDisplay();
            logger.trace("Resources after click: {}", gameManager.getPrimaryResource());
        });
    }

    private void initializeUpgrades() {
        // Add some example upgrades
        String[][] upgrades = {
            {"click_power_1", "Better Click", "2x click power", "100"},
            {"auto_click_1", "Auto Clicker", "1 click per second", "500"},
            {"production_1", "Better Production", "2x all production", "1000"},
            {"offline_1", "Offline Progress", "Earn while away", "2000"}
        };

        int row = 0, col = 0;
        for (String[] upgrade : upgrades) {
            UpgradeComponent component = new UpgradeComponent(
                upgrade[0], upgrade[1], upgrade[2],
                new BigDecimal(upgrade[3]), "",
                this::purchaseUpgrade
            );
            upgradeComponents.add(component);
            upgradesGrid.add(component, col, row);
            
            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }
    }

    private void purchaseUpgrade(String upgradeId) {
        logger.debug("Attempting to purchase upgrade {}", upgradeId);
        // TODO: Implement actual upgrade purchase logic
        upgradeComponents.stream()
            .filter(u -> u.getUpgradeId().equals(upgradeId))
            .findFirst()
            .ifPresent(u -> u.setEnabled(false));
    }

    private void startResourceUpdate() {
        logger.debug("Starting resource update thread");
        Thread updateThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50); // Update every 50ms for smoother display
                    updateDisplay();
                } catch (InterruptedException e) {
                    logger.warn("Resource update thread interrupted", e);
                    break;
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();
    }

    private void updateDisplay() {
        Platform.runLater(() -> {
            BigDecimal resources = gameManager.getResourceSystem().getAmount(GameConstants.PRIMARY_CURRENCY);
            resourceLabel.setText(String.format("Resources: %s", formatNumber(resources)));

            // Update building components
            for (BuildingComponent component : buildingComponents) {
                BuildingSystem.Building building = gameManager.getBuildingSystem().getBuilding(component.getBuildingId());
                if (building != null) {
                    component.update(
                        building.getCount(),
                        building.getNextCost(),
                        building.getCurrentProduction(),
                        gameManager.getResourceSystem().canAfford(GameConstants.PRIMARY_CURRENCY, building.getNextCost())
                    );
                }
            }
        });
    }

    private void updateResourceDisplay() {
        updateDisplay();
    }

    private String formatNumber(BigDecimal number) {
        if (number.compareTo(new BigDecimal("1000000")) >= 0) {
            return String.format("%.2fM", number.divide(new BigDecimal("1000000"), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        } else if (number.compareTo(new BigDecimal("1000")) >= 0) {
            return String.format("%.2fK", number.divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        } else {
            return numberFormat.format(number.doubleValue());
        }
    }
}
