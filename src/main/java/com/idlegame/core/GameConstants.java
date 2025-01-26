package com.idlegame.core;

import java.math.BigDecimal;

public final class GameConstants {
    // Game timing
    public static final long GAME_TICK_DURATION = 1000; // 1 second
    public static final long AUTO_SAVE_INTERVAL = 300; // 5 minutes
    
    // Resource names
    public static final String PRIMARY_CURRENCY = "marijuana";
    public static final String SECONDARY_CURRENCY = "seeds";
    public static final String TERTIARY_CURRENCY = "strains";
    
    // Initial values
    public static final BigDecimal INITIAL_PRIMARY_CURRENCY = BigDecimal.ZERO;
    public static final BigDecimal INITIAL_SECONDARY_CURRENCY = BigDecimal.valueOf(10);
    public static final BigDecimal INITIAL_TERTIARY_CURRENCY = BigDecimal.valueOf(5);
    
    // Upgrade constants
    public static final BigDecimal BASE_UPGRADE_COST = BigDecimal.valueOf(10);
    public static final double UPGRADE_COST_MULTIPLIER = 1.15;
    
    // Generation rates
    public static final BigDecimal BASE_GENERATION_RATE = BigDecimal.valueOf(0.1);
    
    // Click mechanics
    public static final BigDecimal BASE_CLICK_POWER = BigDecimal.valueOf(1);
    public static final BigDecimal BASE_AUTOCLICKER_POWER = BigDecimal.valueOf(0.1);
    public static final long AUTOCLICKER_INTERVAL = 1000; // 1 second
    
    // Prestige system
    public static final BigDecimal PRESTIGE_CURRENCY_THRESHOLD = BigDecimal.valueOf(1_000_000);
    public static final double PRESTIGE_BONUS_MULTIPLIER = 1.5;
    
    // Achievement thresholds
    public static final BigDecimal[] ACHIEVEMENT_THRESHOLDS = {
        BigDecimal.valueOf(10),
        BigDecimal.valueOf(100),
        BigDecimal.valueOf(1_000),
        BigDecimal.valueOf(10_000),
        BigDecimal.valueOf(100_000)
    };
    
    // UI refresh rate
    public static final long UI_REFRESH_INTERVAL = 250; // 250ms
    
    // Balancing factors
    public static final double PRICE_EXPONENT = 1.07;
    public static final double PROGRESSION_EXPONENT = 1.15;
    
    // Offline progression
    public static final long MAX_OFFLINE_TIME = 86400_000; // 24 hours in milliseconds
    public static final double OFFLINE_EFFICIENCY = 0.5; // 50% of online rate
    
    // Special events
    public static final long EVENT_INTERVAL = 3600_000; // 1 hour in milliseconds
    public static final double EVENT_BONUS_MULTIPLIER = 2.0;
    
    // Multipliers and bonuses
    public static final double CRITICAL_CLICK_CHANCE = 0.05; // 5% chance for critical click
    public static final double CRITICAL_CLICK_MULTIPLIER = 2.5; // Critical click multiplier
    public static final double COMBO_BONUS_MULTIPLIER = 1.02; // Bonus per consecutive click
    
    // Clicker upgrades
    public static final int MAX_CLICKER_LEVEL = 100; // Maximum clicker upgrade level
    public static final double CLICKER_UPGRADE_MULTIPLIER = 1.2; // Power increase per level
    public static final BigDecimal CLICKER_UPGRADE_BASE_COST = BigDecimal.valueOf(50);
    
    // Resource caps
    public static final BigDecimal PRIMARY_CURRENCY_CAP = BigDecimal.valueOf(1_000_000_000);
    public static final BigDecimal SECONDARY_CURRENCY_CAP = BigDecimal.valueOf(10_000);
    public static final BigDecimal TERTIARY_CURRENCY_CAP = BigDecimal.valueOf(1_000);
    
    // Time-based bonuses
    public static final long DAILY_BONUS_INTERVAL = 86400_000; // 24 hours in milliseconds
    public static final BigDecimal DAILY_BONUS_AMOUNT = BigDecimal.valueOf(100);
    public static final double STREAK_BONUS_MULTIPLIER = 1.1; // Bonus multiplier for consecutive days
    
    // Prestige levels
    public static final int MAX_PRESTIGE_LEVEL = 100; // Maximum prestige level
    public static final double PRESTIGE_LEVEL_MULTIPLIER = 1.25; // Bonus per prestige level
    public static final BigDecimal PRESTIGE_LEVEL_COST = BigDecimal.valueOf(1_000_000_000);
    
    // Achievement rewards
    public static final BigDecimal[] ACHIEVEMENT_REWARDS = {
        BigDecimal.valueOf(10),
        BigDecimal.valueOf(100),
        BigDecimal.valueOf(1_000),
        BigDecimal.valueOf(10_000),
        BigDecimal.valueOf(100_000)
    };
    
    // Special effects
    public static final long GOLDEN_CLICK_DURATION = 30_000; // 30 seconds in milliseconds
    public static final double GOLDEN_CLICK_MULTIPLIER = 5.0; // Golden click multiplier
    public static final long GOLDEN_CLICK_COOLDOWN = 300_000; // 5 minutes in milliseconds
    
    // Skill tree system
    public static final int MAX_SKILL_POINTS = 100; // Maximum skill points available
    public static final double SKILL_TREE_BRANCH_MULTIPLIER = 1.5; // Multiplier for each branch
    public static final int SKILL_POINTS_PER_PRESTIGE = 5; // Skill points gained per prestige
    
    // Mini-games
    public static final long MINIGAME_COOLDOWN = 3600_000; // 1 hour in milliseconds
    public static final double MINIGAME_REWARD_MULTIPLIER = 2.0; // Reward multiplier for mini-games
    public static final int MINIGAME_MAX_ATTEMPTS = 3; // Maximum attempts per cooldown period
    
    // Leaderboards
    public static final long LEADERBOARD_UPDATE_INTERVAL = 3600_000; // 1 hour in milliseconds
    public static final int LEADERBOARD_TOP_PLAYERS = 100; // Number of players in top leaderboard
    public static final BigDecimal LEADERBOARD_REWARD = BigDecimal.valueOf(1000); // Daily reward
    
    // Seasonal events
    public static final long SEASON_DURATION = 2_592_000_000L; // 30 days in milliseconds
    public static final double SEASONAL_BONUS_MULTIPLIER = 1.5; // Seasonal bonus multiplier
    public static final int MAX_SEASON_LEVEL = 100; // Maximum seasonal progression level
    
    // Social features
    public static final int MAX_FRIENDS = 50; // Maximum number of friends
    public static final double FRIEND_BONUS_MULTIPLIER = 1.01; // Bonus per active friend
    public static final long GIFT_COOLDOWN = 86400_000; // 24 hours in milliseconds
    
    // Cloud saving
    public static final long CLOUD_SAVE_INTERVAL = 3600_000; // 1 hour in milliseconds
    public static final int MAX_CLOUD_BACKUPS = 5; // Maximum number of cloud backups
    public static final long CLOUD_SYNC_TIMEOUT = 30_000; // 30 seconds in milliseconds
    
    // Analytics tracking
    public static final long ANALYTICS_FLUSH_INTERVAL = 300_000; // 5 minutes in milliseconds
    public static final int MAX_EVENTS_PER_BATCH = 100; // Maximum events per analytics batch
    public static final long ANALYTICS_RETENTION = 30_240_000_000L; // 350 days in milliseconds
    
    // Advanced click mechanics
    public static final double CLICK_COMBO_DECAY_RATE = 0.95; // Combo decay rate per second
    public static final int MAX_CLICK_COMBO = 100; // Maximum click combo
    public static final double COMBO_BONUS_EXPONENT = 1.05; // Combo bonus scaling
    public static final long CLICK_ANIMATION_DURATION = 200; // Click animation duration in ms
    
    // Prestige system enhancements
    public static final int PRESTIGE_ACHIEVEMENTS_REQUIRED = 10; // Achievements needed for prestige
    public static final double PRESTIGE_POINT_MULTIPLIER = 1.2; // Prestige point multiplier
    public static final BigDecimal PRESTIGE_POINT_BASE = BigDecimal.valueOf(100);
    
    // Quality of life features
    public static final long NOTIFICATION_DURATION = 5000; // Notification display time
    public static final int MAX_UNDO_STEPS = 5; // Maximum undo steps
    public static final long AUTO_CLICKER_WARNING_THRESHOLD = 1000; // Clicks per second warning
    
    // Anti-cheat measures
    public static final long CLICK_VALIDATION_INTERVAL = 1000; // Click validation interval
    public static final int MAX_CLICKS_PER_SECOND = 20; // Maximum allowed clicks per second
    public static final long TIME_SYNC_INTERVAL = 3600_000; // Time sync interval (1 hour)
    
    // Localization support
    public static final String DEFAULT_LOCALE = "en-US";
    public static final int MAX_LOCALIZED_STRINGS = 1000; // Maximum localized strings
    public static final long LOCALE_RELOAD_INTERVAL = 3600_000; // Locale reload interval
    
    // Performance optimization
    public static final int MAX_ACTIVE_ANIMATIONS = 100; // Maximum concurrent animations
    public static final long FRAME_TIME_WARNING = 33; // Frame time warning threshold (30fps)
    public static final long MEMORY_WARNING_THRESHOLD = 500_000_000; // 500MB memory warning
    
    // Accessibility features
    public static final int MAX_FONT_SIZE = 48; // Maximum font size for accessibility
    public static final int MIN_FONT_SIZE = 12; // Minimum font size
    public static final double COLOR_CONTRAST_RATIO = 4.5; // WCAG AA compliance
    public static final int MAX_ANIMATION_DURATION = 1000; // Max animation duration for reduced motion
    
    // Advanced autoclicker mechanics
    public static final int MAX_AUTOCLICKERS = 10; // Maximum number of autoclickers
    public static final double AUTOCLICKER_EFFICIENCY_DECAY = 0.9; // Efficiency decay per autoclicker
    public static final long AUTOCLICKER_UPGRADE_INTERVAL = 60_000; // 1 minute in milliseconds
    
    // Multiplayer/social features
    public static final long GUILD_CREATION_COST = 1000; // Cost to create a guild
    public static final int MAX_GUILD_MEMBERS = 50; // Maximum guild members
    public static final double GUILD_BONUS_MULTIPLIER = 1.1; // Guild bonus multiplier
    public static final long GUILD_LEADERBOARD_UPDATE = 3600_000; // 1 hour in milliseconds
    
    // Advanced economy balancing
    public static final double INFLATION_RATE = 1.01; // Daily inflation rate
    public static final long ECONOMY_REBALANCE_INTERVAL = 86400_000; // 24 hours in milliseconds
    public static final double MARKET_FLUCTUATION_RATE = 0.05; // Daily market fluctuation
    
    // Visual effects
    public static final int MAX_PARTICLE_EFFECTS = 1000; // Maximum concurrent particles
    public static final long PARTICLE_LIFETIME = 2000; // Particle lifetime in milliseconds
    public static final double PARTICLE_DENSITY = 0.5; // Particle density multiplier
    
    // Sound system
    public static final int MAX_CONCURRENT_SOUNDS = 16; // Maximum concurrent sounds
    public static final double SOUND_VOLUME_RANGE = 0.5; // Volume range for sound effects
    public static final long SOUND_FADE_DURATION = 1000; // Sound fade duration in milliseconds
    
    // Tutorial system
    public static final int MAX_TUTORIAL_STEPS = 20; // Maximum tutorial steps
    public static final long TUTORIAL_STEP_DURATION = 10_000; // Tutorial step duration
    public static final double TUTORIAL_BONUS_MULTIPLIER = 1.5; // Bonus for completing tutorial
    
    // Debug/testing features
    public static final boolean DEBUG_MODE = false; // Debug mode flag
    public static final long DEBUG_TICK_INTERVAL = 100; // Debug tick interval
    public static final double DEBUG_SPEED_MULTIPLIER = 10.0; // Debug speed multiplier
    
    // Building system constants
    public static final String BUILDING_LOW_QUALITY = "LOW_QUALITY";
    public static final String BUILDING_MEDIUM_QUALITY = "MEDIUM_QUALITY";
    public static final String BUILDING_HIGH_QUALITY = "HIGH_QUALITY";
    
    // Base costs
    public static final BigDecimal LOW_QUALITY_BASE_COST = new BigDecimal("10");
    public static final BigDecimal MEDIUM_QUALITY_BASE_COST = new BigDecimal("100");
    public static final BigDecimal HIGH_QUALITY_BASE_COST = new BigDecimal("1000");
    
    // Base generation rates
    public static final BigDecimal LOW_QUALITY_GENERATION = new BigDecimal("0.1");
    public static final BigDecimal MEDIUM_QUALITY_GENERATION = new BigDecimal("1.0");
    public static final BigDecimal HIGH_QUALITY_GENERATION = new BigDecimal("10.0");
    
    // Cost multipliers
    public static final BigDecimal LOW_QUALITY_COST_MULTIPLIER = new BigDecimal("1.15");
    public static final BigDecimal MEDIUM_QUALITY_COST_MULTIPLIER = new BigDecimal("1.20");
    public static final BigDecimal HIGH_QUALITY_COST_MULTIPLIER = new BigDecimal("1.25");
    
    // Unlock requirements
    public static final BigDecimal MEDIUM_QUALITY_UNLOCK = new BigDecimal("1000");
    public static final BigDecimal HIGH_QUALITY_UNLOCK = new BigDecimal("10000");
    
    private GameConstants() {
        // Prevent instantiation
    }
}
