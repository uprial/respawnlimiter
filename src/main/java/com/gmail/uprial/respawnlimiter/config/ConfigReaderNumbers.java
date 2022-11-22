package com.gmail.uprial.respawnlimiter.config;

import com.gmail.uprial.respawnlimiter.common.CustomLogger;
import org.bukkit.configuration.file.FileConfiguration;

import static com.gmail.uprial.respawnlimiter.common.DoubleHelper.*;

public final class ConfigReaderNumbers {
    public static void checkDoubleValue(String title, double min, double max, double doubleValue) throws InvalidConfigException {
        //noinspection IfStatementWithTooManyBranches
        if(!isLengthOfLeftPartOfDoubleGood(doubleValue)) {
            throw new InvalidConfigException(String.format("A left part of %s has too many digits", title));
        } else if(!isLengthOfRightPartOfDoubleGood(doubleValue)) {
            throw new InvalidConfigException(String.format("A right part of %s has too many digits", title));
        } else if ((min - Double.MIN_VALUE) > doubleValue) {
            throw new InvalidConfigException(String.format("A %s should be at least %s", title, formatDoubleValue(min)));
        } else if ((max + Double.MIN_VALUE) < doubleValue) {
            throw new InvalidConfigException(String.format("A %s should be at most %s", title, formatDoubleValue(max)));
        }
    }
}

