package xyz.bt31.staffcontrol.core.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.bt31.staffcontrol.api.IUser;
import xyz.bt31.staffcontrol.core.StaffControl;
import xyz.bt31.staffcontrol.core.lang.Lang;

public class StaffChatListener implements Listener {

    private final StaffControl staffControl;

    public StaffChatListener(StaffControl staffControl) {
        this.staffControl = staffControl;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onStaffChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        IUser user = staffControl.getUserManager().get(player.getUniqueId());

        if (!user.isChatting()) {
            return;
        }

        event.setCancelled(true);

        String message = event.getMessage();

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission("staffcontrol.staff")) {
                staffControl.getLang().send(online, "staff-chat-format",
                    Lang.player(player.getName()),
                    Lang.message(message));
            }
        }
    }
}
