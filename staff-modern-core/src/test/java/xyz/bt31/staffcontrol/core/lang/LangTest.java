package xyz.bt31.staffcontrol.core.lang;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LangTest {

    @Test
    void tagResolverPlayer() {
        TagResolver resolver = Lang.player("TestPlayer");
        assertNotNull(resolver);
    }

    @Test
    void tagResolverTarget() {
        TagResolver resolver = Lang.target("TargetPlayer");
        assertNotNull(resolver);
    }

    @Test
    void tagResolverStaff() {
        TagResolver resolver = Lang.staff("StaffPlayer");
        assertNotNull(resolver);
    }

    @Test
    void tagResolverReporter() {
        TagResolver resolver = Lang.reporter("ReporterPlayer");
        assertNotNull(resolver);
    }

    @Test
    void tagResolverReason() {
        TagResolver resolver = Lang.reason("Some reason");
        assertNotNull(resolver);
    }

    @Test
    void tagResolverUsage() {
        TagResolver resolver = Lang.usage("/command <arg>");
        assertNotNull(resolver);
    }

    @Test
    void tagResolverMessage() {
        TagResolver resolver = Lang.message("Hello world");
        assertNotNull(resolver);
    }
}
