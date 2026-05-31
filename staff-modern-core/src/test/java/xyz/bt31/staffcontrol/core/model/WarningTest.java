package xyz.bt31.staffcontrol.core.model;

import org.junit.jupiter.api.Test;
import xyz.bt31.staffcontrol.api.IWarning;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WarningTest {

    @Test
    void constructorWithAllFields() {
        UUID uuid = UUID.randomUUID();
        UUID issuerUuid = UUID.randomUUID();
        long now = System.currentTimeMillis();
        IWarning warning = new Warning(uuid, issuerUuid, "Staffer", "Spamming", now, false);

        assertEquals(uuid, warning.getUuid());
        assertEquals(issuerUuid, warning.getIssuerUuid());
        assertEquals("Staffer", warning.getIssuerName());
        assertEquals("Spamming", warning.getReason());
        assertEquals(now, warning.getTime());
        assertFalse(warning.shouldRemove());
    }

    @Test
    void constructorAutoGeneratesUuid() {
        IWarning warning = new Warning(UUID.randomUUID(), "Staffer", "Hacking");
        assertNotNull(warning.getUuid());
        assertFalse(warning.shouldRemove());
    }

    @Test
    void setRemoved() {
        Warning warning = new Warning(UUID.randomUUID(), "Staffer", "Reason");
        assertFalse(warning.shouldRemove());
        warning.setRemoved(true);
        assertTrue(warning.shouldRemove());
    }

    @Test
    void setIssuerName() {
        IWarning warning = new Warning(UUID.randomUUID(), "OldName", "Reason");
        warning.setIssuerName("NewName");
        assertEquals("NewName", warning.getIssuerName());
    }
}
