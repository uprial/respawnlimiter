package com.gmail.uprial.respawnlimiter;

import com.gmail.uprial.respawnlimiter.common.CustomLogger;
import com.gmail.uprial.respawnlimiter.config.InvalidConfigException;
import com.gmail.uprial.respawnlimiter.limiter.PlayerLimiter;
import com.gmail.uprial.respawnlimiter.listeners.RespawnLimiterEventListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import static com.gmail.uprial.respawnlimiter.RespawnLimiterCommandExecutor.COMMAND_NS;

public final class RespawnLimiter extends JavaPlugin {
    private final String CONFIG_FILE_NAME = "config.yml";
    private final File configFile = new File(getDataFolder(), CONFIG_FILE_NAME);

    private CustomLogger consoleLogger = null;
    private RespawnLimiterConfig respawnLimiterConfig = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        consoleLogger = new CustomLogger(getLogger());
        respawnLimiterConfig = loadConfig(getConfig(), consoleLogger);

        getServer().getPluginManager().registerEvents(new RespawnLimiterEventListener(this, consoleLogger), this);

        getCommand(COMMAND_NS).setExecutor(new RespawnLimiterCommandExecutor(this));
        consoleLogger.info("Plugin enabled");
    }

    public RespawnLimiterConfig getRespawnLimiterConfig() {
        return respawnLimiterConfig;
    }

    public Player getPlayerByName(String playerName) {
        Collection<? extends Player> players = getServer().getOnlinePlayers();
        Iterator<? extends Player> iterator = players.iterator();
        //noinspection WhileLoopReplaceableByForEach
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if(player.getName().equalsIgnoreCase(playerName)) {
                return player;
            }
        }

        return null;
    }

    void reloadConfig(CustomLogger userLogger) {
        reloadConfig();
        respawnLimiterConfig = loadConfig(getConfig(), userLogger, consoleLogger);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        consoleLogger.info("Plugin disabled");
    }

    @Override
    public void saveDefaultConfig() {
        if (!configFile.exists()) {
            saveResource(CONFIG_FILE_NAME, false);
        }
    }

    @Override
    public FileConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(configFile);
    }

    static RespawnLimiterConfig loadConfig(FileConfiguration config, CustomLogger customLogger) {
        return loadConfig(config, customLogger, null);
    }

    private static RespawnLimiterConfig loadConfig(FileConfiguration config, CustomLogger mainLogger, CustomLogger secondLogger) {
        RespawnLimiterConfig respawnLimiterConfig = null;
        try {
            boolean isDebugMode = RespawnLimiterConfig.isDebugMode(config, mainLogger);
            mainLogger.setDebugMode(isDebugMode);
            if(secondLogger != null) {
                secondLogger.setDebugMode(isDebugMode);
            }

            respawnLimiterConfig = RespawnLimiterConfig.getFromConfig(config, mainLogger);
        } catch (InvalidConfigException e) {
            mainLogger.error(e.getMessage());
        }

        return respawnLimiterConfig;
    }
}
