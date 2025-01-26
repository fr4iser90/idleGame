# User Interface Components

This package contains the JavaFX-based UI implementation for the idle game.

## Key Components

### MainController.java
- Primary controller for the main game view
- Handles UI events and updates
- Bridges between UI and game logic
- Manages view transitions

### main.fxml
- FXML layout definition for main game screen
- Defines UI structure and components
- Includes:
  - Resource display panel
  - Upgrade buttons
  - Progress indicators
  - Main game area

## UI Structure

The UI follows a minimalistic design with:
- Top bar: Resource displays
- Center: Main game area with progress indicators
- Bottom: Upgrade buttons and navigation

## Event Handling

UI events are handled through:
- Button actions
- Mouse events
- Keyboard shortcuts
- Animation callbacks

All UI updates are synchronized with the game loop through observable properties.
