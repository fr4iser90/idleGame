package com.idlegame.core;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class GameState {
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
    }

    public void load() {
        // Load game state from persistence
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
}
