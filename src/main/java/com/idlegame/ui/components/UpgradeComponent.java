package com.idlegame.ui.components;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import java.math.BigDecimal;
import java.util.function.Consumer;

public class UpgradeComponent extends StackPane {
    private final Button upgradeButton;
    private final String upgradeId;
    private final BigDecimal cost;
    
    public UpgradeComponent(String id, String name, String description, BigDecimal cost, 
                           String iconPath, Consumer<String> onPurchase) {
        this.upgradeId = id;
        this.cost = cost;
        
        upgradeButton = new Button();
        upgradeButton.setStyle("-fx-background-color: #673AB7; -fx-background-radius: 10; " +
                             "-fx-min-width: 64; -fx-min-height: 64; -fx-max-width: 64; -fx-max-height: 64;");
        
        // Set icon if provided
        if (iconPath != null && !iconPath.isEmpty()) {
            ImageView icon = new ImageView(iconPath);
            icon.setFitWidth(32);
            icon.setFitHeight(32);
            upgradeButton.setGraphic(icon);
        }
        
        // Create tooltip with name, description and cost
        Tooltip tooltip = new Tooltip(String.format("%s\n%s\nCost: %.0f", name, description, cost));
        tooltip.setStyle("-fx-font-size: 14px;");
        upgradeButton.setTooltip(tooltip);
        
        upgradeButton.setOnAction(e -> onPurchase.accept(upgradeId));
        
        this.getChildren().add(upgradeButton);
    }
    
    public void setEnabled(boolean enabled) {
        upgradeButton.setDisable(!enabled);
        String color = enabled ? "#673AB7" : "#cccccc";
        upgradeButton.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10; " +
                             "-fx-min-width: 64; -fx-min-height: 64; -fx-max-width: 64; -fx-max-height: 64;");
    }
    
    public String getUpgradeId() {
        return upgradeId;
    }
    
    public BigDecimal getCost() {
        return cost;
    }
}
