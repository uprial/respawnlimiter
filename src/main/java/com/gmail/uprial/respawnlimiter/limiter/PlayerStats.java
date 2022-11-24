package com.gmail.uprial.respawnlimiter.limiter;

import com.gmail.uprial.respawnlimiter.RespawnLimiter;
import org.bukkit.entity.Player;

import static com.gmail.uprial.respawnlimiter.common.PersistenceHelper.*;
import static com.gmail.uprial.respawnlimiter.common.PersistenceHelper.setIntegerPersistentMetadata;
import static org.bukkit.Statistic.TIME_SINCE_DEATH;

public class PlayerStats {
    private final RespawnLimiter plugin;
    private final Player player;

    private static final String MK_SEQUENTIAL_DEATHS = "sequential-deaths";
    private static final String MK_MAX_HEALTH_MULTIPLIER = "max-health-multiplier";
    private static final String MK_TIME_SINCE_DEATH_USED = "time-since-death-used";

    public PlayerStats(final RespawnLimiter plugin, final Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public Integer getSequentialDeaths() {
        return getIntegerPersistentMetadata(plugin, player, MK_SEQUENTIAL_DEATHS);
    }

    public void setSequentialDeaths(final Integer sequentialDeaths) {
        setIntegerPersistentMetadata(plugin, player, MK_SEQUENTIAL_DEATHS, sequentialDeaths);
    }

    public Integer getTimeSinceDeathUsed() {
        return getIntegerPersistentMetadata(plugin, player, MK_TIME_SINCE_DEATH_USED);
    }

    public void setTimeSinceDeathUsed(final Integer timeSinceDeathUsed) {
        setIntegerPersistentMetadata(plugin, player, MK_TIME_SINCE_DEATH_USED, timeSinceDeathUsed);
    }

    public Integer getTimeSinceDeath() {
        return player.getStatistic(TIME_SINCE_DEATH);
    }

    public Double getMaxHealthMultiplier() {
        return getDoublePersistentMetadata(plugin, player, MK_MAX_HEALTH_MULTIPLIER);
    }

    public void setMaxHealthMultiplier(final Double maxHealthMultiplier) {
        setDoublePersistentMetadata(plugin, player, MK_MAX_HEALTH_MULTIPLIER, maxHealthMultiplier);
    }

    public String toString() {
        return String.format("sequential-deaths: %d, time-since-death: %d, " +
                        "time-since-death-used: %d, max-health-multiplier: %.4f",
                getSequentialDeaths(),
                getTimeSinceDeath(),
                getTimeSinceDeathUsed(),
                getMaxHealthMultiplier());
    }

}
