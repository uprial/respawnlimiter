package com.gmail.uprial.respawnlimiter.config;

import com.gmail.uprial.respawnlimiter.common.CustomLogger;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public final class ConfigReaderSimple {

    public static String getString(FileConfiguration config, String key, String title) throws InvalidConfigException {
        String string = config.getString(key);

        if(string == null) {
            throw new InvalidConfigException(String.format("Null %s", title));
        }
        if(string.length() < 1) {
            throw new InvalidConfigException(String.format("Empty %s", title));
        }

        return string;
    }

    @SuppressWarnings({"BooleanParameter", "BooleanMethodNameMustStartWithQuestion"})
    public static boolean getBoolean(FileConfiguration config, CustomLogger customLogger, String key, String title, boolean defaultValue) throws InvalidConfigException {
        String strValue = config.getString(key);

        if(strValue == null) {
            customLogger.debug(String.format("Empty %s. Use default value %b", title, defaultValue));
            return defaultValue;
        } else if(strValue.equalsIgnoreCase("true")) {
            return true;
        } else if(strValue.equalsIgnoreCase("false")) {
            return false;
        } else {
            throw new InvalidConfigException(String.format("Invalid %s", title));
        }
    }
}
