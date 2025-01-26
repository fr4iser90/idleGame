package com.idlegame.ui;

import com.idlegame.core.GameManager;
import java.math.BigDecimal;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private GameManager gameManager;

    @FXML
    private Label resourceLabel;
    @FXML
    private Button clickButton;
    @FXML
    private Button upgradeButton;
    @FXML
    private Button buildButton;
    @FXML
    private VBox mainContainer;

    public void initialize() {
        logger.info("Initializing MainController");
        gameManager = new GameManager();
        setupUI();
        startResourceUpdate();
        logger.debug("MainController initialized with gameManager: {}", gameManager);
    }

    private void setupUI() {
        // Click button action
        clickButton.setOnAction(event -> {
            logger.debug("Click button pressed");
            gameManager.clickAction();
            updateResourceDisplay();
            logger.trace("Resources after click: {}", gameManager.getPrimaryResource());
        });

        // Upgrade button action
        upgradeButton.setOnAction(event -> {
            logger.debug("Upgrade button pressed");
            if (gameManager.getPrimaryResource().compareTo(BigDecimal.TEN) >= 0) {
                logger.info("Purchasing generation rate upgrade");
                gameManager.upgradeGenerationRate(0.1);
                gameManager.deductResources(BigDecimal.TEN); // Deduct cost without adding resources
                updateResourceDisplay();
                logger.trace("Resources after upgrade: {}", gameManager.getPrimaryResource());
            } else {
                logger.debug("Not enough resources for upgrade");
            }
        });

        // Build button action
        buildButton.setOnAction(event -> {
            logger.debug("Build button pressed");
            if (gameManager.getPrimaryResource().compareTo(BigDecimal.valueOf(50)) >= 0) {
                logger.info("Purchasing building");
                // TODO: Implement building purchase logic
                gameManager.clickAction(); // Deduct cost
                updateResourceDisplay();
                logger.trace("Resources after building purchase: {}", gameManager.getPrimaryResource());
            } else {
                logger.debug("Not enough resources for building");
            }
        });
    }

    private void startResourceUpdate() {
        logger.debug("Starting resource update thread");
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    updateResourceDisplay();
                    logger.trace("Periodic resource display update");
                } catch (InterruptedException e) {
                    logger.warn("Resource update thread interrupted", e);
                    break;
                }
            }
        }).start();
    }

    private void updateResourceDisplay() {
        logger.trace("Updating resource display");
        javafx.application.Platform.runLater(() -> {
            BigDecimal resources = gameManager.getPrimaryResource();
            resourceLabel.setText(String.format("Resources: %.1f", resources));
            logger.trace("Resource display updated to: {}", resources);
        });
    }
}
