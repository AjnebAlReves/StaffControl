package xyz.bt31.staffcontrol.core.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import xyz.bt31.staffcontrol.core.StaffControl;
import xyz.bt31.staffcontrol.core.user.User;

public class FreezeLoginListener implements Listener {

    private final StaffControl staffControl;

    public FreezeLoginListener(StaffControl staffControl) {
        this.staffControl = staffControl;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        User user = (User) staffControl.getUserManager().get(player.getUniqueId());
        if (user.isFrozen()) {
            Component message = staffControl.getLang().deserialize("freeze-login-blocked");
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, message);
        }
    }
}
