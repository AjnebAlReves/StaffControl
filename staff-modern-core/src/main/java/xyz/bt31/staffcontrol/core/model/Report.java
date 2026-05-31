package xyz.bt31.staffcontrol.core.model;

import xyz.bt31.staffcontrol.api.IReport;

import java.util.UUID;

public class Report implements IReport {

    private final UUID uuid;
    private final UUID reporterUuid;
    private String reporterName;
    private final String reason;
    private final long timestamp;

    public Report(UUID uuid, UUID reporterUuid, String reporterName, String reason, long timestamp) {
        this.uuid = uuid;
        this.reporterUuid = reporterUuid;
        this.reporterName = reporterName;
        this.reason = reason;
        this.timestamp = timestamp;
    }

    public Report(UUID reporterUuid, String reporterName, String reason) {
        this(UUID.randomUUID(), reporterUuid, reporterName, reason, System.currentTimeMillis());
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public String getReporterName() {
        return reporterName;
    }

    @Override
    public UUID getReporterUuid() {
        return reporterUuid;
    }

    @Override
    public void setReporterName(String newName) {
        this.reporterName = newName;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
