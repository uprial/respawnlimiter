package com.gmail.uprial.respawnlimiter.config;

import com.gmail.uprial.respawnlimiter.helpers.TestConfigBase;
import com.gmail.uprial.respawnlimiter.helpers.TestEnum;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.gmail.uprial.respawnlimiter.config.ConfigReaderEnums.getEnum;
import static com.gmail.uprial.respawnlimiter.config.ConfigReaderEnums.getEnumFromString;
import static org.junit.Assert.*;

public class ConfigReaderEnumsTest extends TestConfigBase {
    @Rule
    public final ExpectedException e = ExpectedException.none();

    // ==== getEnum ====
    @Test
    public void testNormalEnum() throws Exception {
        assertEquals(TestEnum.A, getEnum(TestEnum.class, getPreparedConfig("e: A"), "e", "enum"));
    }

    @Test
    public void testWrongEnum() throws Exception {
        e.expect(InvalidConfigException.class);
        e.expectMessage("Invalid com.gmail.uprial.respawnlimiter.helpers.TestEnum 'T' in enum");
        getEnum(TestEnum.class, getPreparedConfig("e: T"), "e", "enum");
    }

    // ==== getEnumFromString ====
    @Test
    public void testWrongEnumString() throws Exception {
        e.expect(InvalidConfigException.class);
        e.expectMessage("Invalid com.gmail.uprial.respawnlimiter.helpers.TestEnum 'T' in enum");
        getEnumFromString(TestEnum.class, "T", "enum", "");
    }

    @Test
    public void testNormalEnumString() throws Exception {
        assertEquals(TestEnum.A, getEnumFromString(TestEnum.class, "A", "enum", ""));
    }

    @Test
    public void testNormalEnumCase() throws Exception {
        assertEquals(TestEnum.A, getEnumFromString(TestEnum.class, "a", "enum", ""));
    }

}