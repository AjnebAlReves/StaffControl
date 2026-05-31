package net.shortninja.staffplus.server.compatibility.capabilities;

import org.bukkit.entity.Player;

import java.util.Set;

public interface ChatCapability {
    void sendHoverableMessage(Set<Player> players, String message, String hoverMessage);
}
