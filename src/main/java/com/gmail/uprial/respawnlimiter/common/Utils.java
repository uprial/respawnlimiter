package com.gmail.uprial.respawnlimiter.common;

import java.util.List;

public class Utils {
    // A number of server ticks in one second
    public static final int SERVER_TICKS_IN_SECOND = 20;

    /*
        The daylight cycle is a 20-minute-long cycle between two main light settings.

        See also: https://minecraft.fandom.com/wiki/Daylight_cycle
     */
    public static final int SECONDS_IN_DAY = 20 * 60;

    public static int seconds2ticks(int seconds) {
        return seconds * SERVER_TICKS_IN_SECOND;
    }

    public static int hours2ticks(int hours) {
        return seconds2ticks(SECONDS_IN_DAY) * hours / 24;
    }

    public static int days2ticks(int days) {
        return seconds2ticks(days * SECONDS_IN_DAY);
    }

    public static <T> String joinStrings(String delimiter, List<T> contents) {
        if (contents.size() < 1) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(contents.get(0));
        int contentsSize = contents.size();
        for (int i = 1; i < contentsSize; i++) {
            stringBuilder.append(delimiter);
            stringBuilder.append(contents.get(i));
        }

        return stringBuilder.toString();
    }
}
