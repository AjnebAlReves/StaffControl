package xyz.bt31.staffcontrol.core.model;

import xyz.bt31.staffcontrol.api.ITicket;

import java.util.UUID;

public class Ticket implements ITicket {

    private final UUID uuid;
    private final String name;
    private final int id;
    private final String inquiry;
    private final long creationTime;
    private String handlerName;
    private boolean hasBeenClosed;

    public Ticket(UUID uuid, String name, int id, String inquiry, long creationTime) {
        this.uuid = uuid;
        this.name = name;
        this.id = id;
        this.inquiry = inquiry;
        this.creationTime = creationTime;
        this.handlerName = "";
        this.hasBeenClosed = false;
    }

    public Ticket(UUID uuid, String name, int id, String inquiry, long creationTime, String handlerName, boolean hasBeenClosed) {
        this.uuid = uuid;
        this.name = name;
        this.id = id;
        this.inquiry = inquiry;
        this.creationTime = creationTime;
        this.handlerName = handlerName;
        this.hasBeenClosed = hasBeenClosed;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getInquiry() {
        return inquiry;
    }

    @Override
    public String getHandlerName() {
        return handlerName;
    }

    @Override
    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    @Override
    public boolean isOpen() {
        return handlerName.isEmpty();
    }

    @Override
    public boolean hasBeenClosed() {
        return hasBeenClosed;
    }

    @Override
    public void setHasBeenClosed(boolean hasBeenClosed) {
        this.hasBeenClosed = hasBeenClosed;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }
}
