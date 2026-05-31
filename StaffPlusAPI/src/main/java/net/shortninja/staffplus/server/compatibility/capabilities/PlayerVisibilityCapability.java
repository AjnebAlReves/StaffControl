package net.shortninja.staffplus.server.compatibility.capabilities;

import org.bukkit.entity.Player;

public interface PlayerVisibilityCapability {
    void hide(Player viewer, Player target);

    void show(Player viewer, Player target);

    void setGlowing(Player player, boolean glow);
}
