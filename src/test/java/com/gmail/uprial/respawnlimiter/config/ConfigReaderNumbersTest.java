package com.gmail.uprial.respawnlimiter.config;

import com.gmail.uprial.respawnlimiter.helpers.TestConfigBase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static com.gmail.uprial.respawnlimiter.config.ConfigReaderNumbers.checkDoubleValue;
import static com.gmail.uprial.respawnlimiter.config.ConfigReaderNumbers.getInt;
import static org.junit.Assert.*;

@SuppressWarnings("ClassWithTooManyMethods")
public class ConfigReaderNumbersTest extends TestConfigBase {
    @Rule
    public final ExpectedException e = ExpectedException.none();

    // ==== getInt ====
    @Test
    public void testEmptyInt() throws Exception {
        e.expect(RuntimeException.class);
        e.expectMessage("Empty value number. Use default value 0");
        getInt(getPreparedConfig(""), getDebugFearingCustomLogger(), "n", "value number", 0, 100, 0);
    }

    @Test
    public void testEmptyIntWithoutDefault() throws Exception {
        e.expect(InvalidConfigException.class);
        e.expectMessage("Empty value number");
        getInt(getPreparedConfig(""), getCustomLogger(), "n", "value number", 0, 100);
    }

    @Test
    public void testWrongInt() throws Exception {
        e.expect(InvalidConfigException.class);
        e.expectMessage("A value number is not an integer");
        getInt(getPreparedConfig("n: 1.0"), getParanoiacCustomLogger(), "n", "value number", 0, 100, 0);
    }

    @Test
    public void testSmallInt() throws Exception {
        e.expect(InvalidConfigException.class);
        e.expectMessage("A value number should be at least 0");
        getInt(getPreparedConfig("n: -1"), getParanoiacCustomLogger(), "n", "value number", 0, 100, 0);
    }

    @Test
    public void testBigInt() throws Exception {
        e.expect(InvalidConfigException.class);
        e.expectMessage("A value number should be at most 100");
        getInt(getPreparedConfig("n: 1000"), getParanoiacCustomLogger(), "n", "value number", 0, 100, 0);
    }

    @Test
    public void testNormalInt() throws Exception {
        assertEquals(50, getInt(getPreparedConfig("n: 50"), getParanoiacCustomLogger(), "n", "value number", 0, 100, 0));
    }

    @Test
    public void testIntMinMaxConflict() throws Exception {
        e.expect(InternalConfigurationError.class);
        e.expectMessage("Max value of value number is greater than max value");
        getInt(getPreparedConfig(""), getParanoiacCustomLogger(), "n", "value number", 200, 100, 0);
    }

    // ==== checkDoubleValue ====

    @Test
    public void testSmallDouble() throws Exception {
        e.expect(InvalidConfigException.class);
        e.expectMessage("A double value should be at least 0");
        checkDoubleValue("double value", 0, 100, -1);
    }

    @Test
    public void testBigDouble() throws Exception {
        e.expect(InvalidConfigException.class);
        e.expectMessage("A double value should be at most 100");
        checkDoubleValue("double value", 0, 100, 1000);
    }

    @Test
    public void testBigLeftPart() throws Exception {
        e.expect(InvalidConfigException.class);
        e.expectMessage("A left part of double value has too many digits");
        checkDoubleValue("double value", 0, 100, 123456789012.0001);
    }

    @Test
    public void testBigRightPart() throws Exception {
        e.expect(InvalidConfigException.class);
        e.expectMessage("A right part of double value has too many digits");
        checkDoubleValue("double value", 0, 100, 12345678901.00001);
    }
}