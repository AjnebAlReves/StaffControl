package xyz.bt31.staffcontrol.api;

import java.util.UUID;

public interface IReport {
    String getReason();
    String getReporterName();
    UUID getReporterUuid();
    void setReporterName(String newName);
    UUID getUuid();
}
