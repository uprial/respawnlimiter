package com.gmail.uprial.respawnlimiter.listeners;

import com.gmail.uprial.respawnlimiter.RespawnLimiter;
import com.gmail.uprial.respawnlimiter.common.CustomLogger;
import com.gmail.uprial.respawnlimiter.limiter.PlayerLimiter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

public class RespawnLimiterEventListener implements Listener {

    private final RespawnLimiter plugin;
    private final CustomLogger customLogger;

    public RespawnLimiterEventListener(final RespawnLimiter plugin, final CustomLogger customLogger) {
        this.plugin = plugin;
        this.customLogger = customLogger;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if(plugin.getRespawnLimiterConfig().isEnabled()) {
            new PlayerLimiter(plugin, customLogger).registerPlayerDeath(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerStatisticIncrementEvent(PlayerStatisticIncrementEvent event) {
        updatePlayerDeathsStats(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBedLeaveEvent(PlayerBedLeaveEvent event) {
        updatePlayerDeathsStats(event.getPlayer());
    }

    private void updatePlayerDeathsStats(final Player player) {
        if(plugin.getRespawnLimiterConfig().isEnabled()) {
            new PlayerLimiter(plugin, customLogger).updatePlayerDeathsStats(player);
        }
    }
}
