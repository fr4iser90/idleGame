# Idle Game - Main Package

This package contains the core implementation of the idle game. It's organized into several sub-packages:

## Sub-packages

### core/
Contains the core game logic including:
- GameManager.java - Main game loop and state management
- ResourceSystem.java - Handles resource generation and tracking
- UpgradeSystem.java - Manages upgrades and their effects

### ui/
Contains JavaFX UI components:
- MainController.java - Controller for main game UI
- main.fxml - FXML layout for main game screen

### resources/
Game resource files including:
- Images
- Sounds
- Configuration files

### persistence/
Save/load functionality:
- SaveManager.java - Handles saving/loading game state
- GameState.java - Serializable game state representation

## Architecture Overview

The game follows a Model-View-Controller pattern:
- Model: core/ package
- View: ui/ package
- Controller: MainController.java

Game state is persisted through the persistence/ package using JSON serialization.
