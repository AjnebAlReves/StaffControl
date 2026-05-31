package xyz.bt31.staffcontrol.api.capabilities;

import org.bukkit.entity.Player;

public interface ActionBarCapability {
    void send(Player player, String message);
}
