package com.gmail.uprial.respawnlimiter.trackers;

import com.gmail.uprial.respawnlimiter.RespawnLimiter;
import com.gmail.uprial.respawnlimiter.common.CustomLogger;
import com.gmail.uprial.respawnlimiter.limiter.PlayerLimiter;
import org.bukkit.entity.Player;

import java.util.*;

import static com.gmail.uprial.respawnlimiter.RespawnLimiterConfig.getMinRecoverySurvivalPeriod;
import static com.gmail.uprial.respawnlimiter.common.Utils.hours2ticks;

public class PlayerTracker extends AbstractTracker {
    private static final int INTERVAL = getMinRecoverySurvivalPeriod();

    private final RespawnLimiter plugin;
    private final CustomLogger customLogger;

    public PlayerTracker(final RespawnLimiter plugin, final CustomLogger customLogger) {
        super(plugin, INTERVAL);

        this.plugin = plugin;
        this.customLogger = customLogger;

        onConfigChange();
    }
    @Override
    public void run() {
        if(plugin.getRespawnLimiterConfig().isEnabled()) {
            for (final Player player : plugin.getServer().getOnlinePlayers()) {
                new PlayerLimiter(plugin, customLogger).updatePlayerDeathsStats(player);
            }
        }
    }

    @Override
    protected void clear() {
    }

    @Override
    protected boolean isEnabled() {
        return plugin.getRespawnLimiterConfig().isEnabled();
    }
}