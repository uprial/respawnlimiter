package com.gmail.uprial.respawnlimiter;

import com.gmail.uprial.respawnlimiter.helpers.TestConfigBase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RespawnLimiterTest extends TestConfigBase {
    @Rule
    public final ExpectedException e = ExpectedException.none();

    @Test
    public void testLoadException() throws Exception {
        e.expect(RuntimeException.class);
        e.expectMessage("[ERROR] Empty 'levels' list");
        RespawnLimiter.loadConfig(getPreparedConfig(""), getCustomLogger());
    }
}