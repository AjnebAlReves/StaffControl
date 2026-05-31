package xyz.bt31.staffcontrol.core.capabilities;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import xyz.bt31.staffcontrol.api.capabilities.ActionBarCapability;

public class ModernActionBarCapability implements ActionBarCapability {

    @Override
    public void send(Player player, String message) {
        player.sendActionBar(Component.text(message));
    }
}
