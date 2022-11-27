package com.gmail.uprial.respawnlimiter;

import com.gmail.uprial.respawnlimiter.common.CustomLogger;
import com.gmail.uprial.respawnlimiter.config.ConfigReaderSimple;
import com.gmail.uprial.respawnlimiter.config.InvalidConfigException;
import com.gmail.uprial.respawnlimiter.limiter.RecoverySurvivalPeriod;
import com.google.common.collect.ImmutableMap;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

import static com.gmail.uprial.respawnlimiter.common.DoubleHelper.MAX_DOUBLE_VALUE;
import static com.gmail.uprial.respawnlimiter.common.Utils.days2ticks;
import static com.gmail.uprial.respawnlimiter.config.ConfigReaderEnums.getEnum;
import static com.gmail.uprial.respawnlimiter.config.ConfigReaderNumbers.checkDoubleValue;
import static com.gmail.uprial.respawnlimiter.config.ConfigReaderNumbers.getInt;

public final class RespawnLimiterConfig {
    private final boolean enabled;
    private final List<Double> levels;
    private final RecoverySurvivalPeriod recoverySurvivalPeriod;
    private final Integer recoveryMobKills;

    private static final Map<RecoverySurvivalPeriod, Integer> RECOVERY_SURVIVAL_PERIOD_2_TICKS = ImmutableMap.<RecoverySurvivalPeriod, Integer>builder()
            .put(RecoverySurvivalPeriod.HOUR, days2ticks(1) / 24)
            .put(RecoverySurvivalPeriod.DAY, days2ticks(1))
            .put(RecoverySurvivalPeriod.WEEK, days2ticks(7))
            .build();

    private RespawnLimiterConfig(final boolean enabled, final List<Double> levels,
                                 final RecoverySurvivalPeriod recoverySurvivalPeriod,
                                 final Integer recoveryMobKills) {
        this.enabled = enabled;
        this.levels = levels;
        this.recoverySurvivalPeriod = recoverySurvivalPeriod;
        this.recoveryMobKills = recoveryMobKills;
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

    public int getRecoverySurvivalPeriod() {
        return RECOVERY_SURVIVAL_PERIOD_2_TICKS.get(recoverySurvivalPeriod);
    }

    public String getRecoverySurvivalPeriodName() {
        return recoverySurvivalPeriod.toString();
    }

    public int getRecoveryMobKills() {
        return recoveryMobKills;
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

        final RecoverySurvivalPeriod recoverySurvivalPeriod = getEnum(RecoverySurvivalPeriod.class, config,
                "recovery-survival-period", "recovery survival period");

        final Integer recoveryMobKills = getInt(config, customLogger,
                "recovery-mob-kills", "recovery mob kills", 1, Integer.MAX_VALUE);

        return new RespawnLimiterConfig(enabled, levels, recoverySurvivalPeriod, recoveryMobKills);
    }

    public String toString() {
        return String.format("enabled: %b, levels: %s, recovery-survival-period: %s, recovery-mob-kills: %d",
                enabled, levels, recoverySurvivalPeriod, recoveryMobKills);
    }
}
