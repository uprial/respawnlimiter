package com.gmail.uprial.respawnlimiter.listeners;

import com.gmail.uprial.respawnlimiter.RespawnLimiter;
import com.gmail.uprial.respawnlimiter.common.CustomLogger;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

import static com.gmail.uprial.respawnlimiter.common.DoubleHelper.MIN_DOUBLE_VALUE;
import static com.gmail.uprial.respawnlimiter.common.Formatter.format;
import static com.gmail.uprial.respawnlimiter.common.MetadataHelper.getMetadata;
import static com.gmail.uprial.respawnlimiter.common.MetadataHelper.setMetadata;
import static com.gmail.uprial.respawnlimiter.common.Utils.seconds2ticks;
import static org.bukkit.Statistic.*;

public class RespawnLimiterEventListener implements Listener {

    private final RespawnLimiter plugin;

    public RespawnLimiterEventListener(final RespawnLimiter plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        plugin.onPlayerRespawn(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerStatisticIncrementEvent(PlayerStatisticIncrementEvent event) {
        plugin.onPlayerStatisticsUpdate(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBedLeaveEvent(PlayerBedLeaveEvent event) {
        plugin.onPlayerStatisticsUpdate(event.getPlayer());
    }
}
