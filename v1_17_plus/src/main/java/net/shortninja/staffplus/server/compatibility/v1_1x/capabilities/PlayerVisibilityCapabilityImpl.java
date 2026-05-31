package net.shortninja.staffplus.server.compatibility.v1_1x.capabilities;

import net.shortninja.staffplus.server.compatibility.capabilities.PlayerVisibilityCapability;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerVisibilityCapabilityImpl implements PlayerVisibilityCapability {
    private final Plugin plugin;

    public PlayerVisibilityCapabilityImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void hide(Player viewer, Player target) {
        viewer.hidePlayer(plugin, target);
    }

    @Override
    public void show(Player viewer, Player target) {
        viewer.showPlayer(plugin, target);
    }

    @Override
    public void setGlowing(Player player, boolean glow) {
        player.setGlowing(glow);
    }
}
