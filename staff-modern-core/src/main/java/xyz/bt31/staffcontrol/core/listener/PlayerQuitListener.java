package xyz.bt31.staffcontrol.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.bt31.staffcontrol.api.IUser;
import xyz.bt31.staffcontrol.core.user.UserManager;

public class PlayerQuitListener implements Listener {

    private final UserManager userManager;

    public PlayerQuitListener(UserManager userManager) {
        this.userManager = userManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        IUser user = userManager.get(event.getPlayer().getUniqueId());
        user.setOnline(false);
    }
}
