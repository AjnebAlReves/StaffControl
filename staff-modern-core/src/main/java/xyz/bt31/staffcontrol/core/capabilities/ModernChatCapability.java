package xyz.bt31.staffcontrol.core.capabilities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import xyz.bt31.staffcontrol.api.capabilities.ChatCapability;

import java.util.Set;

public class ModernChatCapability implements ChatCapability {

    @Override
    public void sendHoverableMessage(Set<Player> players, String message, String hoverMessage) {
        Component component = LegacyComponentSerializer.legacySection().deserialize(message)
            .hoverEvent(HoverEvent.showText(
                LegacyComponentSerializer.legacySection().deserialize(hoverMessage)));

        for (Player player : players) {
            player.sendMessage(component);
        }
    }
}
