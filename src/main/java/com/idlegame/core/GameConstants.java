package com.idlegame.core;

import java.math.BigDecimal;

public class GameConstants {
    // Game timing constants
    public static final long GAME_TICK_DURATION = 50;  // 50ms per tick
    public static final long AUTOSAVE_INTERVAL = 60000;  // Auto-save every minute
    public static final long AUTO_SAVE_INTERVAL = 300000; // 5 minutes
    public static final long MAX_OFFLINE_TIME = 72 * 3600 * 1000;  // 72 hours
    public static final double OFFLINE_PROGRESS_RATE = 0.5;  // 50% production when offline

    // Resource IDs and initial values
    public static final String PRIMARY_CURRENCY = "marijuana";
    public static final String SECONDARY_CURRENCY = "seeds";
    public static final String TERTIARY_CURRENCY = "strains";
    public static final String SPECIAL_CURRENCY = "strains";
    public static final String PRESTIGE_CURRENCY = "knowledge";

    public static final BigDecimal INITIAL_PRIMARY_CURRENCY = BigDecimal.ZERO;
    public static final BigDecimal INITIAL_SECONDARY_CURRENCY = new BigDecimal("10");
    public static final BigDecimal INITIAL_TERTIARY_CURRENCY = new BigDecimal("5");
    
    // Base generation and click values
    public static final BigDecimal BASE_CLICK_POWER = BigDecimal.ONE;
    public static final BigDecimal BASE_GENERATION_RATE = new BigDecimal("0.1");
    public static final BigDecimal BASE_AUTOCLICKER_POWER = new BigDecimal("0.1");
    
    // Achievement thresholds
    public static final BigDecimal[] CLICK_ACHIEVEMENTS = {
        new BigDecimal("100"),
        new BigDecimal("1000"),
        new BigDecimal("10000"),
        new BigDecimal("100000")
    };
    
    public static final BigDecimal[] RESOURCE_ACHIEVEMENTS = {
        new BigDecimal("1000"),
        new BigDecimal("1000000"),
        new BigDecimal("1000000000")
    };
    
    public static final int[] BUILDING_COUNT_ACHIEVEMENTS = {10, 25, 50, 100};

    // Building IDs
    public static final String BUILDING_GROWER = "grower";                     // Basic manual grower
    public static final String BUILDING_GREENHOUSE = "greenhouse";             // Small automated growing
    public static final String BUILDING_HYDROPONIC = "hydroponic";            // Water-based growing
    public static final String BUILDING_INDOOR_FARM = "indoor_farm";          // Large indoor operation
    public static final String BUILDING_AGRICULTURAL = "agricultural";         // Full agricultural center
    public static final String BUILDING_RESEARCH_LAB = "research_lab";        // Improves strain quality
    public static final String BUILDING_GENETIC_LAB = "genetic_lab";          // Creates new strains
    public static final String BUILDING_PROCESSING = "processing";            // Processes raw product
    public static final String BUILDING_PACKAGING = "packaging";              // Packages for distribution
    public static final String BUILDING_DISTRIBUTION = "distribution";        // Handles distribution
    public static final String BUILDING_RETAIL = "retail";                    // Retail operations
    public static final String BUILDING_MEDICAL = "medical";                  // Medical dispensary
    public static final String BUILDING_CONSULTING = "consulting";            // Industry consulting
    public static final String BUILDING_MARKETING = "marketing";              // Marketing operations
    public static final String BUILDING_QUALITY_CONTROL = "quality_control";  // Ensures product quality
    public static final String BUILDING_SECURITY = "security";                // Facility security
    public static final String BUILDING_LEGAL = "legal";                     // Legal operations
    public static final String BUILDING_EDUCATION = "education";             // Industry education
    public static final String BUILDING_INNOVATION = "innovation";           // R&D center
    public static final String BUILDING_EMPIRE = "empire";                   // Complete business empire

    // Achievement thresholds and rewards
    public static final String[] BUILDING_IDS = {
        BUILDING_GROWER, BUILDING_GREENHOUSE, BUILDING_HYDROPONIC, BUILDING_INDOOR_FARM,
        BUILDING_AGRICULTURAL, BUILDING_RESEARCH_LAB, BUILDING_GENETIC_LAB, BUILDING_PROCESSING,
        BUILDING_PACKAGING, BUILDING_DISTRIBUTION, BUILDING_RETAIL, BUILDING_MEDICAL,
        BUILDING_CONSULTING, BUILDING_MARKETING, BUILDING_QUALITY_CONTROL, BUILDING_SECURITY,
        BUILDING_LEGAL, BUILDING_EDUCATION, BUILDING_INNOVATION, BUILDING_EMPIRE
    };

    public static final int[] CLICK_ACHIEVEMENT_THRESHOLDS = {
        100, 500, 1000, 5000, 10000, 50000, 100000, 500000, 1000000
    };

    public static final BigDecimal[] RESOURCE_ACHIEVEMENT_THRESHOLDS = {
        new BigDecimal("1000"),
        new BigDecimal("10000"),
        new BigDecimal("100000"),
        new BigDecimal("1000000"),
        new BigDecimal("10000000"),
        new BigDecimal("100000000"),
        new BigDecimal("1000000000"),
        new BigDecimal("10000000000")
    };

    public static final BigDecimal CLICK_ACHIEVEMENT_BONUS = new BigDecimal("1.25"); // 25% bonus per click achievement

    // Building base costs (exponential progression)
    public static final BigDecimal[] BUILDING_BASE_COSTS = {
        new BigDecimal("15"),                    // Grower
        new BigDecimal("100"),                   // Greenhouse
        new BigDecimal("1100"),                  // Hydroponic
        new BigDecimal("12000"),                 // Indoor Farm
        new BigDecimal("130000"),                // Agricultural
        new BigDecimal("1400000"),               // Research Lab
        new BigDecimal("20000000"),              // Genetic Lab
        new BigDecimal("330000000"),             // Processing
        new BigDecimal("5100000000"),            // Packaging
        new BigDecimal("75000000000"),           // Distribution
        new BigDecimal("1000000000000"),         // Retail
        new BigDecimal("14000000000000"),        // Medical
        new BigDecimal("170000000000000"),       // Consulting
        new BigDecimal("2100000000000000"),      // Marketing
        new BigDecimal("26000000000000000"),     // Quality Control
        new BigDecimal("310000000000000000"),    // Security
        new BigDecimal("7100000000000000000"),   // Legal
        new BigDecimal("12000000000000000000"),  // Education
        new BigDecimal("190000000000000000000"), // Innovation
        new BigDecimal("270000000000000000000")  // Empire
    };

    // Building base production (exponential progression)
    public static final BigDecimal[] BUILDING_BASE_PRODUCTION = {
        new BigDecimal("0.1"),      // Grower
        new BigDecimal("1.0"),      // Greenhouse
        new BigDecimal("8.0"),      // Hydroponic
        new BigDecimal("47.0"),     // Indoor Farm
        new BigDecimal("260.0"),    // Agricultural
        new BigDecimal("1400.0"),   // Research Lab
        new BigDecimal("7800.0"),   // Genetic Lab
        new BigDecimal("44000.0"),  // Processing
        new BigDecimal("260000.0"), // Packaging
        new BigDecimal("1600000.0"), // Distribution
        new BigDecimal("10000000.0"), // Retail
        new BigDecimal("65000000.0"), // Medical
        new BigDecimal("430000000.0"), // Consulting
        new BigDecimal("2900000000.0"), // Marketing
        new BigDecimal("21000000000.0"), // Quality Control
        new BigDecimal("150000000000.0"), // Security
        new BigDecimal("1100000000000.0"), // Legal
        new BigDecimal("8300000000000.0"), // Education
        new BigDecimal("64000000000000.0"), // Innovation
        new BigDecimal("510000000000000.0") // Empire
    };

    // Building cost scaling (each purchase increases cost by this percentage)
    public static final BigDecimal[] BUILDING_COST_MULTIPLIERS = {
        new BigDecimal("1.15"),  // +15% Grower
        new BigDecimal("1.15"),  // +15% Greenhouse
        new BigDecimal("1.15"),  // +15% Hydroponic
        new BigDecimal("1.15"),  // +15% Indoor Farm
        new BigDecimal("1.15"),  // +15% Agricultural
        new BigDecimal("1.15"),  // +15% Research Lab
        new BigDecimal("1.15"),  // +15% Genetic Lab
        new BigDecimal("1.15"),  // +15% Processing
        new BigDecimal("1.15"),  // +15% Packaging
        new BigDecimal("1.15"),  // +15% Distribution
        new BigDecimal("1.15"),  // +15% Retail
        new BigDecimal("1.15"),  // +15% Medical
        new BigDecimal("1.15"),  // +15% Consulting
        new BigDecimal("1.15"),  // +15% Marketing
        new BigDecimal("1.15"),  // +15% Quality Control
        new BigDecimal("1.15"),  // +15% Security
        new BigDecimal("1.15"),  // +15% Legal
        new BigDecimal("1.15"),  // +15% Education
        new BigDecimal("1.15"),  // +15% Innovation
        new BigDecimal("1.15")   // +15% Empire
    };

    // Achievement thresholds for buildings (unlocks special bonuses)
    public static final int[] ACHIEVEMENT_THRESHOLDS = {
        1, 5, 10, 25, 50, 75, 100, 150, 200, 250, 300, 350, 400, 450, 500,
        600, 700, 800, 900, 1000, 1500, 2000, 2500, 3000, 4000, 5000,
        6000, 7000, 8000, 9000, 10000
    };

    // Production multipliers for achievements
    public static final BigDecimal[] ACHIEVEMENT_MULTIPLIERS = {
        new BigDecimal("1.0"),   // Base
        new BigDecimal("1.1"),   // +10%
        new BigDecimal("1.25"),  // +25%
        new BigDecimal("1.5"),   // +50%
        new BigDecimal("1.75"),  // +75%
        new BigDecimal("2.0"),   // +100%
        new BigDecimal("2.5"),   // +150%
        new BigDecimal("3.0"),   // +200%
        new BigDecimal("3.5"),   // +250%
        new BigDecimal("4.0"),   // +300%
        new BigDecimal("5.0"),   // +400%
        new BigDecimal("6.0"),   // +500%
        new BigDecimal("7.0"),   // +600%
        new BigDecimal("8.0"),   // +700%
        new BigDecimal("10.0"),  // +900%
        new BigDecimal("12.0"),  // +1100%
        new BigDecimal("15.0"),  // +1400%
        new BigDecimal("20.0"),  // +1900%
        new BigDecimal("25.0"),  // +2400%
        new BigDecimal("30.0"),  // +2900%
        new BigDecimal("40.0"),  // +3900%
        new BigDecimal("50.0"),  // +4900%
        new BigDecimal("75.0"),  // +7400%
        new BigDecimal("100.0"), // +9900%
        new BigDecimal("150.0"), // +14900%
        new BigDecimal("200.0"), // +19900%
        new BigDecimal("300.0"), // +29900%
        new BigDecimal("400.0"), // +39900%
        new BigDecimal("500.0"), // +49900%
        new BigDecimal("750.0"), // +74900%
        new BigDecimal("1000.0") // +99900%
    };

    // Synergy bonuses (when you own certain combinations of buildings)
    public static final BigDecimal SYNERGY_BONUS = new BigDecimal("1.05"); // +5% per synergy
    
    // Building quality levels
    public static final String BUILDING_LOW_QUALITY = "low";
    public static final String BUILDING_MEDIUM_QUALITY = "medium";
    public static final String BUILDING_HIGH_QUALITY = "high";
    public static final String BUILDING_PREMIUM_QUALITY = "premium";
    public static final String BUILDING_MASTER_QUALITY = "master";
    public static final String BUILDING_LEGENDARY_QUALITY = "legendary";
    public static final String BUILDING_MYTHIC_QUALITY = "mythic";
    public static final String BUILDING_DIVINE_QUALITY = "divine";

    // Quality base costs
    public static final BigDecimal LOW_QUALITY_BASE_COST = new BigDecimal("100");
    public static final BigDecimal MEDIUM_QUALITY_BASE_COST = new BigDecimal("1000");
    public static final BigDecimal HIGH_QUALITY_BASE_COST = new BigDecimal("10000");
    public static final BigDecimal PREMIUM_QUALITY_BASE_COST = new BigDecimal("100000");
    public static final BigDecimal MASTER_QUALITY_BASE_COST = new BigDecimal("1000000");
    public static final BigDecimal LEGENDARY_QUALITY_BASE_COST = new BigDecimal("10000000");
    public static final BigDecimal MYTHIC_QUALITY_BASE_COST = new BigDecimal("100000000");
    public static final BigDecimal DIVINE_QUALITY_BASE_COST = new BigDecimal("1000000000");

    // Quality multipliers
    public static final BigDecimal LOW_QUALITY_MULTIPLIER = new BigDecimal("1.1");
    public static final BigDecimal MEDIUM_QUALITY_MULTIPLIER = new BigDecimal("1.25");
    public static final BigDecimal HIGH_QUALITY_MULTIPLIER = new BigDecimal("1.5");
    public static final BigDecimal PREMIUM_QUALITY_MULTIPLIER = new BigDecimal("2.0");
    public static final BigDecimal MASTER_QUALITY_MULTIPLIER = new BigDecimal("3.0");
    public static final BigDecimal LEGENDARY_QUALITY_MULTIPLIER = new BigDecimal("5.0");
    public static final BigDecimal MYTHIC_QUALITY_MULTIPLIER = new BigDecimal("10.0");
    public static final BigDecimal DIVINE_QUALITY_MULTIPLIER = new BigDecimal("25.0");

    // Prestige system constants
    public static final BigDecimal PRESTIGE_REQUIREMENT = new BigDecimal("1000000"); // 1M for first prestige
    public static final BigDecimal PRESTIGE_MULTIPLIER = new BigDecimal("1.15"); // Each prestige requires 15% more
    public static final BigDecimal PRESTIGE_BONUS = new BigDecimal("0.02"); // Each prestige point gives +2% production

    private GameConstants() {
        // Prevent instantiation
    }
}
