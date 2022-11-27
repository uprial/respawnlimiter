package com.gmail.uprial.respawnlimiter;

import com.gmail.uprial.respawnlimiter.common.CustomLogger;
import com.gmail.uprial.respawnlimiter.config.ConfigReaderSimple;
import com.gmail.uprial.respawnlimiter.config.InvalidConfigException;
import com.gmail.uprial.respawnlimiter.limiter.RecoveryInterval;
import com.google.common.collect.ImmutableMap;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

import static com.gmail.uprial.respawnlimiter.common.DoubleHelper.MAX_DOUBLE_VALUE;
import static com.gmail.uprial.respawnlimiter.common.Utils.days2ticks;
import static com.gmail.uprial.respawnlimiter.config.ConfigReaderEnums.getEnum;
import static com.gmail.uprial.respawnlimiter.config.ConfigReaderNumbers.checkDoubleValue;

public final class RespawnLimiterConfig {
    private final boolean enabled;
    private final List<Double> levels;
    private final RecoveryInterval recoveryInterval;

    private static final Map<RecoveryInterval, Integer> RECOVERY_INTERVAL_2_TICKS = ImmutableMap.<RecoveryInterval, Integer>builder()
            .put(RecoveryInterval.HOUR, days2ticks(1) / 24)
            .put(RecoveryInterval.DAY, days2ticks(1))
            .put(RecoveryInterval.WEEK, days2ticks(7))
            .build();

    private RespawnLimiterConfig(final boolean enabled, final List<Double> levels, final RecoveryInterval recoveryInterval) {
        this.enabled = enabled;
        this.levels = levels;
        this.recoveryInterval = recoveryInterval;
    }

    static boolean isDebugMode(FileConfiguration config, CustomLogger customLogger) throws InvalidConfigException {
        return ConfigReaderSimple.getBoolean(config, customLogger, "debug", "'debug' flag", false);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public double getMaxHealthMultiplier(int sequentialDeaths) {
        return levels.get(sequentialDeaths);
    }

    public int getMaxSequentialDeaths() {
        return levels.size() - 1;
    }

    public int getRecoveryInterval() {
        return RECOVERY_INTERVAL_2_TICKS.get(recoveryInterval);
    }

    public String getRecoveryIntervalName() {
        return recoveryInterval.toString();
    }

    public static RespawnLimiterConfig getFromConfig(FileConfiguration config, CustomLogger customLogger) throws InvalidConfigException {
        final boolean enabled = ConfigReaderSimple.getBoolean(config, customLogger, "enabled", "'enabled' flag", true);

        final List<?> handlersConfig = config.getList("levels");
        if((handlersConfig == null) || (handlersConfig.size() <= 0)) {
            throw new InvalidConfigException("Empty 'levels' list");
        }

        final List<Double> levels = new ArrayList<>();

        final int levelsConfigSize = handlersConfig.size();
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

        final RecoveryInterval recoveryInterval = getEnum(RecoveryInterval.class, config,
                "recovery-interval", "recovery interval");

        return new RespawnLimiterConfig(enabled, levels, recoveryInterval);
    }

    public String toString() {
        return String.format("enabled: %b, levels: %s, recovery-interval: %s",
                enabled, levels, recoveryInterval);
    }
}
