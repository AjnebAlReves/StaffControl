package xyz.bt31.staffcontrol.api.event;

import xyz.bt31.staffcontrol.api.IReport;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ReportPlayerEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final IReport report;

    public ReportPlayerEvent(IReport report) {
        this.report = report;
    }

    public IReport getReport() {
        return report;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
