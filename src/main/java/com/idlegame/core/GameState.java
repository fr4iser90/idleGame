package com.idlegame.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

public class GameState implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Instant lastSaveTime;
    private boolean autoSaveEnabled;
    private final Map<String, Object> stateData;

    public GameState() {
        this.lastSaveTime = Instant.now();
        this.autoSaveEnabled = true;
        this.stateData = new HashMap<>();
    }

    public void save() {
        // Save current game state
        lastSaveTime = Instant.now();
        
        try {
            // Create game save directory if it doesn't exist
            Files.createDirectories(Paths.get("saves"));
            
            // Save state to file
            ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("saves/gameState.dat")
            );
            out.writeObject(this);
            out.close();
        } catch (Exception e) {
            LoggerFactory.getLogger(GameState.class).error("Failed to save game state", e);
        }
    }

    @SuppressWarnings("unchecked")
    public GameState load() {
        try {
            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("saves/gameState.dat")
            );
            GameState loadedState = (GameState) in.readObject();
            in.close();
            return loadedState;
        } catch (Exception e) {
            LoggerFactory.getLogger(GameState.class).error("Failed to load game state", e);
            return null;
        }
    }

    public boolean shouldAutoSave() {
        return autoSaveEnabled && 
            Instant.now().isAfter(lastSaveTime.plusSeconds(GameConstants.AUTO_SAVE_INTERVAL));
    }

    public void setAutoSaveEnabled(boolean enabled) {
        this.autoSaveEnabled = enabled;
    }

    public void putState(String key, Object value) {
        stateData.put(key, value);
    }

    public Object getState(String key) {
        return stateData.get(key);
    }

    public <T> T getState(String key, Class<T> type) {
        Object value = stateData.get(key);
        return type.isInstance(value) ? type.cast(value) : null;
    }

    public void removeState(String key) {
        stateData.remove(key);
    }

    public void clearState() {
        stateData.clear();
    }

    public Instant getLastSaveTime() {
        return lastSaveTime;
    }
}
