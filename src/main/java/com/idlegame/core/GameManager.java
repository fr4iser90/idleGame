package com.idlegame.core;

import java.util.Timer;
import java.util.TimerTask;

public class GameManager {
    private double primaryResource = 0;
    private double resourceGenerationRate = 1.0;
    private Timer gameTimer;

    public GameManager() {
        initializeGame();
    }

    private void initializeGame() {
        // Setup game timer for passive resource generation
        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                generateResources();
            }
        }, 1000, 1000); // Update every second
    }

    private void generateResources() {
        primaryResource += resourceGenerationRate;
    }

    public void clickAction() {
        primaryResource += 1;
    }

    public double getPrimaryResource() {
        return primaryResource;
    }

    public double getResourceGenerationRate() {
        return resourceGenerationRate;
    }

    public void upgradeGenerationRate(double amount) {
        resourceGenerationRate += amount;
    }

    public void stopGame() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }
    }
}
