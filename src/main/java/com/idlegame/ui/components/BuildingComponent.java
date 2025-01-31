package com.idlegame.ui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.math.BigDecimal;
import java.util.function.Consumer;

public class BuildingComponent extends HBox {
    private final String buildingId;
    private final Label nameLabel;
    private final Label countLabel;
    private final Label productionLabel;
    private final Label costLabel;
    private final Button buyButton;
    private final Button buyMaxButton;
    
    public BuildingComponent(String buildingId, String name, BigDecimal baseCost, BigDecimal baseProduction, Consumer<Integer> onBuy) {
        super(10); // spacing between elements
        this.buildingId = buildingId;
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 5; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 1);");
        
        // Left side - Building info
        VBox infoBox = new VBox(5);
        nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        countLabel = new Label("Owned: 0");
        countLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
        
        productionLabel = new Label(String.format("+%.1f/sec", baseProduction));
        productionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #4CAF50;");
        
        infoBox.getChildren().addAll(nameLabel, countLabel, productionLabel);
        
        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // Right side - Cost and buttons
        VBox controlBox = new VBox(5);
        controlBox.setAlignment(Pos.CENTER_RIGHT);
        
        costLabel = new Label(String.format("Cost: %.0f", baseCost));
        costLabel.setStyle("-fx-font-size: 14px;");
        
        HBox buttonBox = new HBox(5);
        buyButton = new Button("Buy");
        buyButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5 15;");
        buyButton.setOnAction(e -> onBuy.accept(1));
        
        buyMaxButton = new Button("Buy Max");
        buyMaxButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 5 15;");
        buyMaxButton.setOnAction(e -> onBuy.accept(-1)); // -1 indicates buy max
        
        buttonBox.getChildren().addAll(buyButton, buyMaxButton);
        controlBox.getChildren().addAll(costLabel, buttonBox);
        
        this.getChildren().addAll(infoBox, spacer, controlBox);
    }
    
    public String getBuildingId() {
        return buildingId;
    }
    
    public void update(int count, BigDecimal currentCost, BigDecimal currentProduction, boolean canAfford) {
        countLabel.setText(String.format("Owned: %d", count));
        costLabel.setText(String.format("Cost: %.0f", currentCost));
        productionLabel.setText(String.format("+%.1f/sec", currentProduction));
        
        String buttonStyle = canAfford ? 
            "-fx-background-color: #4CAF50; -fx-text-fill: white;" :
            "-fx-background-color: #cccccc; -fx-text-fill: white;";
        
        buyButton.setStyle(buttonStyle + " -fx-padding: 5 15;");
        buyButton.setDisable(!canAfford);
        
        buyMaxButton.setStyle(buttonStyle.replace("#4CAF50", "#2196F3") + " -fx-padding: 5 15;");
        buyMaxButton.setDisable(!canAfford);
    }
}
