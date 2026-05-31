package xyz.bt31.staffcontrol.core.capabilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.bt31.staffcontrol.api.capabilities.PlayerVisibilityCapability;

public class ModernPlayerVisibilityCapability implements PlayerVisibilityCapability {

    @Override
    public void hide(Player viewer, Player target) {
        viewer.hidePlayer(Bukkit.getPluginManager().getPlugin("StaffControl"), target);
    }

    @Override
    public void show(Player viewer, Player target) {
        viewer.showPlayer(Bukkit.getPluginManager().getPlugin("StaffControl"), target);
    }

    @Override
    public void setGlowing(Player player, boolean glow) {
        player.setGlowing(glow);
    }
}
