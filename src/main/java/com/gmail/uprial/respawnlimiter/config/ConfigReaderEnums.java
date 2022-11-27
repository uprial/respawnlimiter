package com.gmail.uprial.respawnlimiter.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

import static com.gmail.uprial.respawnlimiter.config.ConfigReaderSimple.getString;

public final class ConfigReaderEnums {

    public static <T extends Enum> T getEnum(Class<T> enumType, FileConfiguration config, String key, String title) throws InvalidConfigException {
        String string = getString(config, key, title);
        return getEnumFromString(enumType, string, title, "");
    }

    static <T extends Enum> T getEnumFromString(Class<T> enumType, String string, String title, String desc) throws InvalidConfigException {
        try {
            //noinspection unchecked,RedundantCast
            return (T)Enum.valueOf(enumType, string.toUpperCase(Locale.getDefault()));
        } catch (IllegalArgumentException ignored) {
            throw new InvalidConfigException(String.format("Invalid %s '%s' in %s%s", enumType.getName(), string, title, desc));
        }
    }
}
