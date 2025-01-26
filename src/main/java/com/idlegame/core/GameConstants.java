package com.idlegame.core;

import java.math.BigDecimal;

public final class GameConstants {
    // Game timing
    public static final long GAME_TICK_DURATION = 1000; // 1 second
    public static final long AUTO_SAVE_INTERVAL = 300; // 5 minutes
    
    // Resource names
    public static final String PRIMARY_CURRENCY = "gold";
    public static final String SECONDARY_CURRENCY = "gems";
    
    // Initial values
    public static final BigDecimal INITIAL_PRIMARY_CURRENCY = BigDecimal.ZERO;
    public static final BigDecimal INITIAL_SECONDARY_CURRENCY = BigDecimal.ZERO;
    
    // Upgrade constants
    public static final BigDecimal BASE_UPGRADE_COST = BigDecimal.valueOf(10);
    public static final double UPGRADE_COST_MULTIPLIER = 1.15;
    
    // Generation rates
    public static final BigDecimal BASE_GENERATION_RATE = BigDecimal.valueOf(0.1);
    
    private GameConstants() {
        // Prevent instantiation
    }
}
