package xyz.bt31.staffcontrol.core.staffmode;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.bt31.staffcontrol.api.IAction;
import xyz.bt31.staffcontrol.api.IGui;
import xyz.bt31.staffcontrol.api.IUser;
import xyz.bt31.staffcontrol.core.StaffControl;
import xyz.bt31.staffcontrol.core.user.User;

public class StaffModeListener implements Listener {

    private final StaffControl staffControl;
    private final StaffModeManager staffModeManager;

    public StaffModeListener(StaffControl staffControl, StaffModeManager staffModeManager) {
        this.staffControl = staffControl;
        this.staffModeManager = staffModeManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onStaffItemInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        User user = (User) staffControl.getUserManager().get(player.getUniqueId());
        if (!user.isStaffMode()) return;

        event.setCancelled(true);

        int slot = player.getInventory().getHeldItemSlot();
        staffModeManager.handleItemClick(player, slot);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        User user = (User) staffControl.getUserManager().get(player.getUniqueId());

        if (!user.isStaffMode()) return;

        if (event.getInventory() == player.getInventory()) {
            event.setCancelled(true);
            return;
        }

        IGui gui = user.getCurrentGui().orElse(null);
        if (gui != null && event.getInventory().equals(gui.getInventory())) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            IAction action = gui.getAction(slot);
            if (action != null) {
                action.click(player, event.getCurrentItem(), slot);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = (User) staffControl.getUserManager().get(player.getUniqueId());
        if (user.isStaffMode()) {
            user.setStaffMode(false);
        }
        user.setChatting(false);
    }
}
