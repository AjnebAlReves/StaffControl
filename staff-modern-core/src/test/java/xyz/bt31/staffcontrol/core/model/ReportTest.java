package xyz.bt31.staffcontrol.core.model;

import org.junit.jupiter.api.Test;
import xyz.bt31.staffcontrol.api.IReport;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    @Test
    void constructorWithAllFields() {
        UUID uuid = UUID.randomUUID();
        UUID reporterUuid = UUID.randomUUID();
        long now = System.currentTimeMillis();
        IReport report = new Report(uuid, reporterUuid, "John", "Spamming", now);

        assertEquals(uuid, report.getUuid());
        assertEquals(reporterUuid, report.getReporterUuid());
        assertEquals("John", report.getReporterName());
        assertEquals("Spamming", report.getReason());
    }

    @Test
    void constructorAutoGeneratesUuid() {
        IReport report = new Report(UUID.randomUUID(), "Alice", "Hacking");
        assertNotNull(report.getUuid());
    }

    @Test
    void setReporterName() {
        IReport report = new Report(UUID.randomUUID(), "OldName", "Reason");
        report.setReporterName("NewName");
        assertEquals("NewName", report.getReporterName());
    }
}
