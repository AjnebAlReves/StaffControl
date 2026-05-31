package xyz.bt31.staffcontrol.api;

import java.util.UUID;

public interface ITicket {
    UUID getUuid();
    String getName();
    int getId();
    String getInquiry();
    String getHandlerName();
    void setHandlerName(String handlerName);
    boolean isOpen();
    boolean hasBeenClosed();
    void setHasBeenClosed(boolean hasBeenClosed);
    long getCreationTime();
}
