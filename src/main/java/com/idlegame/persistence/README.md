# Game Persistence

This package handles saving and loading game state using JSON serialization.

## Key Components

### SaveManager.java
- Handles saving and loading operations
- Manages auto-save functionality
- Implements versioning for save files
- Handles save file validation

### GameState.java
- Serializable representation of game state
- Contains:
  - Resource values
  - Upgrade states
  - Game progression
  - Timestamps

## Save System Features

- JSON-based save files
- Automatic backup system
- Save file encryption
- Version migration support
- Cloud save integration (future)

## File Structure

Save files are stored in:
- Local: ~/.idlegame/saves/
- Cloud: (future implementation)

File naming convention:
[timestamp]_[version]_[hash].json

## Error Handling

The system includes:
- Save file validation
- Corruption detection
- Automatic recovery
- User notifications
