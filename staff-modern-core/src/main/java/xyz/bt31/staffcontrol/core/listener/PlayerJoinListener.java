package xyz.bt31.staffcontrol.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.bt31.staffcontrol.api.IUser;
import xyz.bt31.staffcontrol.api.VanishType;
import xyz.bt31.staffcontrol.core.StaffControl;
import xyz.bt31.staffcontrol.core.user.UserManager;

public class PlayerJoinListener implements Listener {

    private final StaffControl staffControl;
    private final UserManager userManager;

    public PlayerJoinListener(StaffControl staffControl, UserManager userManager) {
        this.staffControl = staffControl;
        this.userManager = userManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        IUser user = userManager.get(player.getUniqueId());
        user.setOnline(true);

        if (staffControl.getOptions().isVanishOnJoin()
                && player.hasPermission("staffcontrol.vanish.auto")) {
            user.setVanishType(VanishType.TOTAL);
            for (Player online : player.getServer().getOnlinePlayers()) {
                if (!online.hasPermission("staffcontrol.vanish.see")) {
                    online.hidePlayer(staffControl.getPlugin(), player);
                }
            }
        }
    }
}
