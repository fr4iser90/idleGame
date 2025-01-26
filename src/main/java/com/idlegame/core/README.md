# Core Game Logic

This package contains the core game systems and logic that drive the idle game mechanics.

## Key Classes

### GameManager.java
- Manages the main game loop
- Handles time-based progression
- Coordinates between different systems
- Tracks game state and progression

### ResourceSystem.java
- Manages all in-game resources
- Tracks resource generation rates
- Handles resource transactions
- Implements passive income generation

### UpgradeSystem.java
- Manages unlockable upgrades
- Handles upgrade costs and scaling
- Applies passive effects
- Tracks upgrade progression

## Game Loop Implementation

The game loop runs on a fixed time step:
1. Update resource generation
2. Process player actions
3. Apply upgrade effects
4. Check for new unlocks
5. Save game state (if auto-save enabled)

## Resource Management

Resources are tracked using a hierarchical system:
- Primary currency (main resource)
- Secondary resources (crafting materials)
- Generation rates (per second/minute)

All resource values are stored as BigDecimal for precision.
