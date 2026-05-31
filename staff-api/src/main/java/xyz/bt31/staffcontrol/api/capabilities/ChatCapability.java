package xyz.bt31.staffcontrol.api.capabilities;

import java.util.Set;
import org.bukkit.entity.Player;

public interface ChatCapability {
    void sendHoverableMessage(Set<Player> players, String message, String hoverMessage);
}
