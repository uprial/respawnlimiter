package com.gmail.uprial.respawnlimiter;

import com.gmail.uprial.respawnlimiter.common.CustomLogger;
import com.gmail.uprial.respawnlimiter.config.ConfigReaderSimple;
import com.gmail.uprial.respawnlimiter.config.InvalidConfigException;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

import static com.gmail.uprial.respawnlimiter.common.DoubleHelper.MAX_DOUBLE_VALUE;
import static com.gmail.uprial.respawnlimiter.config.ConfigReaderNumbers.checkDoubleValue;
import static java.lang.Math.min;

public final class RespawnLimiterConfig {
    private final boolean enabled;
    private final List<Double> levels;

    private RespawnLimiterConfig(boolean enabled, List<Double> levels) {
        this.enabled = enabled;
        this.levels = levels;
    }

    static boolean isDebugMode(FileConfiguration config, CustomLogger customLogger) throws InvalidConfigException {
        return ConfigReaderSimple.getBoolean(config, customLogger, "debug", "'debug' flag", false);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public double getMaxHealthMultiplier(int sequentialDeaths) {
        return levels.get(min(sequentialDeaths, levels.size()));
    }

    public static RespawnLimiterConfig getFromConfig(FileConfiguration config, CustomLogger customLogger) throws InvalidConfigException {
        final boolean enabled = ConfigReaderSimple.getBoolean(config, customLogger, "enabled", "'enabled' flag", true);

        List<?> handlersConfig = config.getList("levels");
        if((handlersConfig == null) || (handlersConfig.size() <= 0)) {
            throw new InvalidConfigException("Empty 'levels' list");
        }

        List<Double> levels = new ArrayList<>();

        int levelsConfigSize = handlersConfig.size();
        for(int i = 0; i < levelsConfigSize; i++) {
            try {
                Object item = handlersConfig.get(i);
                if (item == null) {
                    throw new InvalidConfigException(String.format("Null key in 'levels' at pos %d", i));
                }
                if (!(item instanceof Double) && !(item instanceof Integer)) {
                    throw new InvalidConfigException(String.format("Key '%s' in 'levels' at pos %d is not a double", item.toString(), i));
                }
                double level = ((Number) item).doubleValue();
                checkDoubleValue(String.format("'levels' at pos %d", i), 0.01, MAX_DOUBLE_VALUE, level);
                levels.add(level);
            } catch (InvalidConfigException e) {
                customLogger.error(e.getMessage());
            }
        }

        if(levels.size() < 1) {
            throw new InvalidConfigException("There are no valid levels definitions");
        }

        return new RespawnLimiterConfig(enabled, levels);
    }

    public String toString() {
        return String.format("enabled: %b, levels: %s", enabled, levels);
    }
}
