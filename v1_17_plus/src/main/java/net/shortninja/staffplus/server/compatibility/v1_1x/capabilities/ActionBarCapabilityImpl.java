package net.shortninja.staffplus.server.compatibility.v1_1x.capabilities;

import net.shortninja.staffplus.server.compatibility.capabilities.ActionBarCapability;
import net.shortninja.staffplus.server.compatibility.v1_1x.util.ComponentSerializer;
import org.bukkit.entity.Player;

public class ActionBarCapabilityImpl implements ActionBarCapability {
    @Override
    public void send(Player player, String message) {
        player.sendActionBar(ComponentSerializer.fromLegacy(message));
    }
}
