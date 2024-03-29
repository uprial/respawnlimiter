package com.gmail.uprial.respawnlimiter.common;

import com.google.common.collect.Lists;
import org.junit.Test;

import static com.gmail.uprial.respawnlimiter.common.Utils.*;
import static org.junit.Assert.assertEquals;

public class UtilsTest {
    @Test
    public void testSeconds2ticks() throws Exception {
        assertEquals(40, seconds2ticks(2));
    }

    @Test
    public void testJoinEmptyStrings() throws Exception {
        assertEquals("", joinStrings(",", Lists.newArrayList(new String[]{})));
    }

    @Test
    public void testJoinOneString() throws Exception {
        assertEquals("a", joinStrings(",", Lists.newArrayList("a")));
    }

    @Test
    public void testJoinSeveralStrings() throws Exception {
        assertEquals("a,b", joinStrings(",", Lists.newArrayList("a", "b")));
    }

    @Test
    public void testHours2ticks() throws Exception {
        assertEquals(1000, hours2ticks(1));
        assertEquals(2000, hours2ticks(2));
    }
    @Test
    public void testDays2ticks() throws Exception {
        assertEquals(24000, days2ticks(1));
        assertEquals(72000, days2ticks(3));
    }
}