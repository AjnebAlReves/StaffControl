package net.shortninja.staffplus.server.compatibility.capabilities;

import org.bukkit.entity.Player;

public interface ActionBarCapability {
    void send(Player player, String message);
}
