package com.gmail.uprial.respawnlimiter;

import com.gmail.uprial.respawnlimiter.config.InvalidConfigException;
import com.gmail.uprial.respawnlimiter.helpers.TestConfigBase;
import org.bukkit.configuration.InvalidConfigurationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class CreaturesConfigTest extends TestConfigBase {
    @Rule
    public final ExpectedException e = ExpectedException.none();

    @Test
    public void testEmptyDebug() throws Exception {
        e.expect(RuntimeException.class);
        e.expectMessage("Empty 'debug' flag. Use default value false");
        RespawnLimiterConfig.isDebugMode(getPreparedConfig(""), getDebugFearingCustomLogger());
    }

    @Test
    public void testNormalDebug() throws Exception {
        assertTrue(RespawnLimiterConfig.isDebugMode(getPreparedConfig("debug: true"), getDebugFearingCustomLogger()));
    }

    @Test
    public void testEmpty() throws Exception {
        e.expect(RuntimeException.class);
        e.expectMessage("Empty 'enabled' flag. Use default value true");
        loadConfig(getDebugFearingCustomLogger(), "");
    }

    @Test
    public void testNotMap() throws Exception {
        e.expect(InvalidConfigurationException.class);
        e.expectMessage("Top level is not a Map.");
        loadConfig("x");
    }

    @Test
    public void testEmptyLevels() throws Exception {
        e.expect(InvalidConfigException.class);
        e.expectMessage("Empty 'levels' list");
        loadConfig(getDebugFearingCustomLogger(), "enabled: true");
    }

    @Test
    public void testEmptyLevelsList() throws Exception {
        e.expect(InvalidConfigException.class);
        e.expectMessage("Empty 'levels' list");
        loadConfig("enabled: true",
                "levels:");
    }

    @Test
    public void testNoValidLevelsDefinitions() throws Exception {
        e.expect(InvalidConfigException.class);
        e.expectMessage("There are no valid levels definitions");
        loadConfig(getIndifferentCustomLogger(), "enabled: true",
                        "levels:",
                        "- -99");
    }

    @Test
    public void testNormalConfig() throws Exception {
        assertEquals(
                "enabled: true, levels: [100.0]",
                loadConfig("enabled: true",
                        "levels:",
                        " - 100").toString());
    }
}