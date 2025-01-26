package com.idlegame.ui;

import com.idlegame.core.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainController {
    private GameManager gameManager;

    @FXML
    private Label resourceLabel;
    @FXML
    private Button clickButton;
    @FXML
    private Button upgradeButton;
    @FXML
    private VBox mainContainer;

    public void initialize() {
        gameManager = new GameManager();
        setupUI();
        startResourceUpdate();
    }

    private void setupUI() {
        // Click button action
        clickButton.setOnAction(event -> {
            gameManager.clickAction();
            updateResourceDisplay();
        });

        // Upgrade button action
        upgradeButton.setOnAction(event -> {
            if (gameManager.getPrimaryResource() >= 10) {
                gameManager.upgradeGenerationRate(0.1);
                gameManager.clickAction(); // Deduct cost
                updateResourceDisplay();
            }
        });
    }

    private void startResourceUpdate() {
        // Update resource display every second
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    updateResourceDisplay();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();
    }

    private void updateResourceDisplay() {
        javafx.application.Platform.runLater(() -> {
            resourceLabel.setText(String.format("Resources: %.1f", 
                gameManager.getPrimaryResource()));
        });
    }
}
