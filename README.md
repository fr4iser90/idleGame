# Idle Game Project

A minimalistic idle game implementation in Java using JavaFX.

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── idlegame/
│   │           ├── core/       # Core game logic
│   │           ├── ui/         # JavaFX UI components
│   │           ├── resources/  # Game assets
│   │           └── persistence/ # Save/load functionality
│   └── resources/              # Resource files
└── test/                       # Unit tests
```

## Development Setup

### Requirements
- Java 17+
- Maven 3.8+
- JavaFX SDK

### Build & Run
```bash
# Using nix-shell
nix-shell --run "mvn clean javafx:run"  

# Standard Maven build
mvn clean package
java -jar target/idlegame.jar
```

## Key Features
- Passive income generation
- Click-based actions
- Time-based progression
- Save/load functionality
- Minimal JavaFX UI

## Documentation
See package-specific README files for detailed documentation:
- [Core Game Logic](src/main/java/com/idlegame/core/README.md)
- [UI Components](src/main/java/com/idlegame/ui/README.md)
- [Persistence](src/main/java/com/idlegame/persistence/README.md)
- [Resources](src/main/java/com/idlegame/resources/README.md)

## Contributing
1. Fork the repository
2. Create a feature branch
3. Submit a pull request
