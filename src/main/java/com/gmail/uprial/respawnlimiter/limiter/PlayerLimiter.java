package com.gmail.uprial.respawnlimiter.limiter;

import com.gmail.uprial.respawnlimiter.RespawnLimiter;
import com.gmail.uprial.respawnlimiter.common.CustomLogger;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import static com.gmail.uprial.respawnlimiter.common.DoubleHelper.MIN_DOUBLE_VALUE;
import static com.gmail.uprial.respawnlimiter.common.Formatter.format;

public class PlayerLimiter {
    /*
        Because of rounding of float point variables we need to make sure that
        health of entity is lower than its max. health.
        So, we reduce an entity's health by this value.
    */
    private final static double HEALTH_REDUCTION = 0.000001;

    private final RespawnLimiter plugin;
    private final CustomLogger customLogger;

    public PlayerLimiter(final RespawnLimiter plugin, final CustomLogger customLogger) {
        this.plugin = plugin;
        this.customLogger = customLogger;
    }

    public void registerPlayerDeath(final Player player) {
        final PlayerStats playerStats = new PlayerStats(plugin, player);
        final int sequentialDeaths =
                Math.min(getOrDefault(playerStats.getSequentialDeaths(), 0) + 1,
                        plugin.getRespawnLimiterConfig().getMaxSequentialDeaths());

        // Update
        updatePlayerMaxHealth(player, sequentialDeaths);

        // Waste all the time since death
        playerStats.setSequentialDeaths(sequentialDeaths);
        playerStats.setTimeSinceDeathUsed(0);
    }

    public void updatePlayerDeathsStats(final Player player) {
        final PlayerStats playerStats = new PlayerStats(plugin, player);
        int sequentialDeaths = getOrDefault(playerStats.getSequentialDeaths(), 0);
        if(sequentialDeaths > 0) {
            final int period = plugin.getRespawnLimiterConfig().getRecoveryInterval();
            int timeSinceDeath = playerStats.getTimeSinceDeath();
            int timeSinceDeathUsed = getOrDefault(playerStats.getTimeSinceDeathUsed(), 0);

            timeSinceDeath -= timeSinceDeathUsed;

            if(timeSinceDeath > period) {
                while ((timeSinceDeath > period) && (sequentialDeaths > 0)) {
                    timeSinceDeath -= period;
                    timeSinceDeathUsed += period;
                    sequentialDeaths -= 1;
                }

                // Update
                updatePlayerMaxHealth(player, sequentialDeaths);

                // Save stats
                playerStats.setSequentialDeaths(sequentialDeaths);
                playerStats.setTimeSinceDeathUsed(timeSinceDeathUsed);
            }
        }
    }

    public void resetPlayerDeathsStats(final Player player) {
        final PlayerStats playerStats = new PlayerStats(plugin, player);
        playerStats.setSequentialDeaths(0);
        playerStats.setTimeSinceDeathUsed(0);
        // Update
        updatePlayerMaxHealth(player, 0);
    }

    private void updatePlayerMaxHealth(final Player player, final int sequentialDeaths) {
        final PlayerStats playerStats = new PlayerStats(plugin, player);

        final double newMaxHealthMultiplier = plugin.getRespawnLimiterConfig().getMaxHealthMultiplier(sequentialDeaths);
        final double oldMaxHealthMultiplier = getOrDefault(playerStats.getMaxHealthMultiplier(), 1.0D);

        // Performance optimisation: only if the new and the old multipliers are different
        if(Math.abs(oldMaxHealthMultiplier - newMaxHealthMultiplier) > MIN_DOUBLE_VALUE) {
            final AttributeInstance maxHealthAttributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            final double oldMaxHealth = maxHealthAttributeInstance.getBaseValue();
            final double newMaxHealth = oldMaxHealth / oldMaxHealthMultiplier * newMaxHealthMultiplier;

            /*
                WARNING: the following two lines must have nothing in between,
                or any exception will prevent stats from the save
                and break the future control of max health.
             */
            maxHealthAttributeInstance.setBaseValue(newMaxHealth);
            playerStats.setMaxHealthMultiplier(newMaxHealthMultiplier);

            if(newMaxHealthMultiplier < oldMaxHealthMultiplier) {
                player.setHealth(newMaxHealth - HEALTH_REDUCTION);
            }

            if(customLogger.isDebugMode()) {
                customLogger.debug(String.format("Changed max. health of %s from %.2f to %.2f",
                        format(player), oldMaxHealth, newMaxHealth));
            }
            new CustomLogger(plugin.getLogger(), player).info(
                    String.format("You have %d/%d sequential deaths," +
                                    " which scaled your maximum health to %d%% for one %s",
                            sequentialDeaths,
                            plugin.getRespawnLimiterConfig().getMaxSequentialDeaths(),
                            Math.round(newMaxHealthMultiplier * 100),
                            plugin.getRespawnLimiterConfig().getRecoveryIntervalName()));
        }
    }

    private <T> T getOrDefault(T value, T defaultValue) {
        return (value != null) ? value : defaultValue;
    }
}
