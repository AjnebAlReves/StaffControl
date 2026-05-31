package xyz.bt31.staffcontrol.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsTest {

    @Test
    void escapeNull() {
        assertNull(JsonUtils.escape(null));
    }

    @Test
    void escapePlainString() {
        assertEquals("hello", JsonUtils.escape("hello"));
    }

    @Test
    void escapeDoubleQuote() {
        assertEquals("say \\\"hi\\\"", JsonUtils.escape("say \"hi\""));
    }

    @Test
    void escapeBackslash() {
        assertEquals("a\\\\b", JsonUtils.escape("a\\b"));
    }

    @Test
    void escapeNewline() {
        assertEquals("line1\\nline2", JsonUtils.escape("line1\nline2"));
    }

    @Test
    void escapeTab() {
        assertEquals("col1\\tcol2", JsonUtils.escape("col1\tcol2"));
    }

    @Test
    void escapeControlCharacter() {
        String input = "a" + (char) 0x01 + "b";
        String expected = "a\\u0001b";
        assertEquals(expected, JsonUtils.escape(input));
    }

    @Test
    void escapeSlash() {
        assertEquals("a\\/b", JsonUtils.escape("a/b"));
    }
}
