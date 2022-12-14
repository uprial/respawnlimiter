package com.gmail.uprial.respawnlimiter.limiter;

import com.gmail.uprial.respawnlimiter.RespawnLimiter;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import static com.gmail.uprial.respawnlimiter.common.PersistenceHelper.*;
import static com.gmail.uprial.respawnlimiter.common.PersistenceHelper.setIntegerPersistentMetadata;
import static org.bukkit.Statistic.MOB_KILLS;
import static org.bukkit.Statistic.TIME_SINCE_DEATH;

public class PlayerStats {
    private final RespawnLimiter plugin;
    private final Player player;

    private static final String MK_SEQUENTIAL_DEATHS = "sequential-deaths";
    private static final String MK_MAX_HEALTH_MULTIPLIER = "max-health-multiplier";
    private static final String MK_TIME_SINCE_DEATH_USED = "time-since-death-used";
    private static final String MK_MOB_KILLS_USED = "time-mob-kills-used";

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

    public Integer getTimeSinceDeath() {
        return player.getStatistic(TIME_SINCE_DEATH);
    }

    public Integer getTimeSinceDeathUsed() {
        return getIntegerPersistentMetadata(plugin, player, MK_TIME_SINCE_DEATH_USED);
    }

    public void setTimeSinceDeathUsed(final Integer timeSinceDeathUsed) {
        setIntegerPersistentMetadata(plugin, player, MK_TIME_SINCE_DEATH_USED, timeSinceDeathUsed);
    }

    public Double getMaxHealthMultiplier() {
        return getDoublePersistentMetadata(plugin, player, MK_MAX_HEALTH_MULTIPLIER);
    }

    public void setMaxHealthMultiplier(final Double maxHealthMultiplier) {
        setDoublePersistentMetadata(plugin, player, MK_MAX_HEALTH_MULTIPLIER, maxHealthMultiplier);
    }

    public Integer getMobKills() {
        return player.getStatistic(MOB_KILLS);
    }

    public Integer getMobKillsUsed() {
        return getIntegerPersistentMetadata(plugin, player, MK_MOB_KILLS_USED);
    }

    public void setMobKillsUsed(final Integer mobKillsUsed) {
        setIntegerPersistentMetadata(plugin, player, MK_MOB_KILLS_USED, mobKillsUsed);
    }

    public String toString() {
        return String.format("sequential-deaths: %d, max-health-multiplier: %.4f" +
                        ", time-since-death: %d, time-since-death-used: %d" +
                        ", mob-kills: %d, mob-kills-used: %d",
                getSequentialDeaths(),
                getMaxHealthMultiplier(),
                getTimeSinceDeath(),
                getTimeSinceDeathUsed(),
                getMobKills(),
                getMobKillsUsed()
                );
    }

}
