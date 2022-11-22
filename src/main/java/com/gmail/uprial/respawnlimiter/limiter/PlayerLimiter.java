package com.gmail.uprial.respawnlimiter.limiter;

import com.gmail.uprial.respawnlimiter.RespawnLimiter;
import com.gmail.uprial.respawnlimiter.common.CustomLogger;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import static com.gmail.uprial.respawnlimiter.common.DoubleHelper.MIN_DOUBLE_VALUE;
import static com.gmail.uprial.respawnlimiter.common.Formatter.format;
import static com.gmail.uprial.respawnlimiter.common.PersistenceHelper.*;
import static com.gmail.uprial.respawnlimiter.common.Utils.seconds2ticks;
import static org.bukkit.Statistic.TIME_SINCE_DEATH;

public class PlayerLimiter {
    /*
        Because of rounding of float point variables we need to make sure that
        health of entity is lower than its max. health.
        So, we reduce an entity's health by this value.
    */
    private final static double HEALTH_REDUCTION = 0.000001;

    private final RespawnLimiter plugin;
    private final CustomLogger customLogger;

    private static final String MK_SEQUENTIAL_DEATHS = "sequential-deaths";
    private static final String MK_MAX_HEALTH_MULTIPLIER = "max-health-multiplier";
    private static final String MK_TIME_SINCE_DEATH_USED = "time-since-death-used";

    public PlayerLimiter(final RespawnLimiter plugin, final CustomLogger customLogger) {
        this.plugin = plugin;
        this.customLogger = customLogger;
    }

    public void onPlayerRespawn(final Player player) {
        final int sequentialDeaths = getPlayerMetadataValue(player, MK_SEQUENTIAL_DEATHS, 0) + 1;
        updatePlayerMaxHealth(player, sequentialDeaths);

        // Waste all the time since death
        setPlayerMetadataValue(player, MK_SEQUENTIAL_DEATHS, sequentialDeaths);
        setPlayerMetadataValue(player, MK_TIME_SINCE_DEATH_USED, 0);
    }

    public void onPlayerStatisticsUpdate(final Player player) {
        int sequentialDeaths = getPlayerMetadataValue(player, MK_SEQUENTIAL_DEATHS, 0);
        customLogger.debug(String.format("Sequential deaths - OLD: %d", sequentialDeaths));
        if(sequentialDeaths > 0) {
            final int period = seconds2ticks(60);
            int timeSinceDeath = player.getStatistic(TIME_SINCE_DEATH);
            int timeSinceDeathUsed = getPlayerMetadataValue(player, MK_TIME_SINCE_DEATH_USED, 0);

            timeSinceDeath -= timeSinceDeathUsed;

            customLogger.debug(String.format("Time since death: %d", timeSinceDeath));
            customLogger.debug(String.format("Time since death used - OLD: %d", timeSinceDeathUsed));
            if(timeSinceDeath > period) {
                while ((timeSinceDeath > period) && (sequentialDeaths > 0)) {
                    timeSinceDeath -= period;
                    timeSinceDeathUsed += period;
                    sequentialDeaths -= 1;
                }

                customLogger.debug(String.format("Sequential deaths - NEW: %d", sequentialDeaths));
                customLogger.debug(String.format("Time since death used - NEW: %d", timeSinceDeathUsed));
                updatePlayerMaxHealth(player, sequentialDeaths);
                setPlayerMetadataValue(player, MK_SEQUENTIAL_DEATHS, sequentialDeaths);
                setPlayerMetadataValue(player, MK_TIME_SINCE_DEATH_USED, timeSinceDeathUsed);
            }
        }
    }

    public String getPlayerStatistics(final Player player) {
        Integer sequentialDeaths = getPlayerMetadataValue(player, MK_SEQUENTIAL_DEATHS, (Integer)null);
        Integer timeSinceDeathUsed = getPlayerMetadataValue(player, MK_TIME_SINCE_DEATH_USED, (Integer)null);
        int timeSinceDeath = player.getStatistic(TIME_SINCE_DEATH);
        return String.format("Sequential deaths: %d, time since death: %d, time since death used: %s",
                sequentialDeaths, timeSinceDeath, timeSinceDeathUsed);
    }

    public void resetPlayerStatistics(final Player player) {
        setPlayerMetadataValue(player, MK_SEQUENTIAL_DEATHS, 0);
        setPlayerMetadataValue(player, MK_TIME_SINCE_DEATH_USED, 0);
    }

    private void updatePlayerMaxHealth(final Player player, int sequentialDeaths) {
        final double newMaxHealthMultiplier = plugin.getRespawnLimiterConfig().getMaxHealthMultiplier(sequentialDeaths);
        final double oldMaxHealthMultiplier = getPlayerMetadataValue(player, MK_MAX_HEALTH_MULTIPLIER,1.0);

        // Performance optimisation: only if the new and the old multipliers are different
        if(Math.abs(oldMaxHealthMultiplier - newMaxHealthMultiplier) > MIN_DOUBLE_VALUE) {
            final AttributeInstance maxHealthAttributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            final double oldMaxHealth = maxHealthAttributeInstance.getBaseValue();
            final double newMaxHealth = oldMaxHealth / oldMaxHealthMultiplier * newMaxHealthMultiplier;

            maxHealthAttributeInstance.setBaseValue(newMaxHealth);
            player.setHealth(newMaxHealth - HEALTH_REDUCTION);

            if(customLogger.isDebugMode()) {
                customLogger.debug(String.format("Change max. health of %s from %.2f to %.2f",
                        format(player), oldMaxHealth, newMaxHealth));
            }

            setPlayerMetadataValue(player, MK_MAX_HEALTH_MULTIPLIER, newMaxHealthMultiplier);
        }
    }

    private Double getPlayerMetadataValue(final Player player, final String key, final Double defaultValue) {
        Double value = getDoublePersistentMetadata(plugin, player, key);
        return (value == null) ? defaultValue : value;
    }

    private Integer getPlayerMetadataValue(final Player player, final String key, final Integer defaultValue) {
        Integer value = getIntegerPersistentMetadata(plugin, player, key);
        return (value == null) ? defaultValue : value;
    }

    private void setPlayerMetadataValue(final Player player, final String key, final Double value) {
        setDoublePersistentMetadata(plugin, player, key, value);
    }

    private void setPlayerMetadataValue(final Player player, final String key, final Integer value) {
        setIntegerPersistentMetadata(plugin, player, key, value);
    }
}
