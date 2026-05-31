package xyz.bt31.staffcontrol.core.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.bt31.staffcontrol.core.StaffControl;

public class ChatAlertListener implements Listener {

    private final StaffControl staffControl;

    public ChatAlertListener(StaffControl staffControl) {
        this.staffControl = staffControl;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (staffControl.getOptions().isAlertMention() && message.toLowerCase().contains("@staff")) {
            Component alert = Component.text()
                .append(Component.text("[STAFF]", NamedTextColor.RED))
                .append(Component.text(" " + player.getName() + ": ", NamedTextColor.GRAY))
                .append(Component.text(message, NamedTextColor.WHITE))
                .hoverEvent(HoverEvent.showText(
                    Component.text("Click to teleport to " + player.getName())))
                .build();

            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("staffcontrol.alerts") && !online.equals(player)) {
                    online.sendMessage(alert);
                }
            }
        }

        if (staffControl.getOptions().isAlertNameChange()) {
            String displayName = player.getDisplayName();
            if (!displayName.equals(player.getName())) {
                Component alert = Component.text()
                    .append(Component.text("[NAME_CHANGE]", NamedTextColor.YELLOW))
                    .append(Component.text(" " + player.getName() + " → ", NamedTextColor.GRAY))
                    .append(Component.text(displayName, NamedTextColor.WHITE))
                    .build();

                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.hasPermission("staffcontrol.alerts") && !online.equals(player)) {
                        online.sendMessage(alert);
                    }
                }
            }
        }
    }
}
