package com.gmail.uprial.respawnlimiter;

import com.gmail.uprial.respawnlimiter.limiter.PlayerLimiter;
import com.gmail.uprial.respawnlimiter.limiter.PlayerStats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.uprial.respawnlimiter.common.CustomLogger;
import org.bukkit.entity.Player;

class RespawnLimiterCommandExecutor implements CommandExecutor {
    public static final String COMMAND_NS = "respawnlimiter";

    private final RespawnLimiter plugin;

    RespawnLimiterCommandExecutor(RespawnLimiter plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(COMMAND_NS)) {
            CustomLogger customLogger = new CustomLogger(plugin.getLogger(), sender);

            if((args.length >= 1) && (args[0].equalsIgnoreCase("reload"))) {
                if (sender.hasPermission(COMMAND_NS + ".reload")) {
                    plugin.reloadConfig(customLogger);
                    customLogger.info("RespawnLimiter config reloaded.");
                    return true;
                }
            }
            else if((args.length >= 1) && (args[0].equalsIgnoreCase("info"))) {
                if (sender.hasPermission(COMMAND_NS + ".info")) {
                    final String playerName = (args.length < 2) ? sender.getName() : args[1];
                    final Player player = plugin.getPlayerByName(playerName);
                    if(player == null) {
                        customLogger.error(String.format("Player '%s' is not exists.", playerName));
                        return false;
                    } else {
                        customLogger.info(new PlayerStats(plugin, player).toString());
                        return true;
                    }
                }
            }
            else if((args.length >= 1) && (args[0].equalsIgnoreCase("reset"))) {
                if (sender.hasPermission(COMMAND_NS + ".reset")) {
                    final String playerName = (args.length < 2) ? sender.getName() : args[1];
                    final Player player = plugin.getPlayerByName(playerName);
                    if(player == null) {
                        customLogger.error(String.format("Player '%s' is not exists.", playerName));
                        return false;
                    } else {
                        new PlayerLimiter(plugin, customLogger).resetPlayerDeathsStats(player);
                        customLogger.info(new PlayerStats(plugin, player).toString());
                        return true;
                    }
                }
            }
            else if((args.length == 0) || (args[0].equalsIgnoreCase("help"))) {
                String helpString = "==== RespawnLimiter help ====\n";

                if (sender.hasPermission(COMMAND_NS + ".reload")) {
                    helpString += '/' + COMMAND_NS + " reload - reload config from disk\n";
                }
                if (sender.hasPermission(COMMAND_NS + ".info")) {
                    helpString += '/' + COMMAND_NS + " info [@player] - show player limits\n";
                }
                if (sender.hasPermission(COMMAND_NS + ".reset")) {
                    helpString += '/' + COMMAND_NS + " reset [@player] - reset player limits\n";
                }

                customLogger.info(helpString);
                return true;
            }
        }
        return false;
    }
}
