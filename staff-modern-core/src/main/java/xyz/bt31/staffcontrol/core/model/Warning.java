package xyz.bt31.staffcontrol.core.model;

import xyz.bt31.staffcontrol.api.IWarning;

import java.util.UUID;

public class Warning implements IWarning {

    private final UUID uuid;
    private final UUID issuerUuid;
    private String issuerName;
    private final String reason;
    private final long time;
    private boolean removed;

    public Warning(UUID uuid, UUID issuerUuid, String issuerName, String reason, long time, boolean removed) {
        this.uuid = uuid;
        this.issuerUuid = issuerUuid;
        this.issuerName = issuerName;
        this.reason = reason;
        this.time = time;
        this.removed = removed;
    }

    public Warning(UUID issuerUuid, String issuerName, String reason) {
        this(UUID.randomUUID(), issuerUuid, issuerName, reason, System.currentTimeMillis(), false);
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public String getIssuerName() {
        return issuerName;
    }

    @Override
    public UUID getIssuerUuid() {
        return issuerUuid;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public void setIssuerName(String newName) {
        this.issuerName = newName;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean shouldRemove() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    @Override
    public String getName() {
        return reason;
    }
}
