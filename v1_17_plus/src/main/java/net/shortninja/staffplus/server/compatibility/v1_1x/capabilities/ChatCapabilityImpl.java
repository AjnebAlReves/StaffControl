package net.shortninja.staffplus.server.compatibility.v1_1x.capabilities;

import net.shortninja.staffplus.server.compatibility.capabilities.ChatCapability;
import net.shortninja.staffplus.server.compatibility.v1_1x.util.ComponentSerializer;
import org.bukkit.entity.Player;

import java.util.Set;

public class ChatCapabilityImpl implements ChatCapability {
    @Override
    public void sendHoverableMessage(Set<Player> players, String message, String hoverMessage) {
        for (Player player : players) {
            player.spigot().sendMessage(ComponentSerializer.withHover(message, hoverMessage));
        }
    }
}
