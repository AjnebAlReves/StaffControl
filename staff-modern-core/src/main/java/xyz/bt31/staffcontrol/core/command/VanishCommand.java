package xyz.bt31.staffcontrol.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.bt31.staffcontrol.api.VanishType;
import xyz.bt31.staffcontrol.core.StaffControl;
import xyz.bt31.staffcontrol.core.StaffControlPlugin;
import xyz.bt31.staffcontrol.core.lang.Lang;
import xyz.bt31.staffcontrol.core.user.User;

public class VanishCommand extends Command {

    public VanishCommand(StaffControl staffControl) {
        super(staffControl);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd,
                             String label, String[] args) {
        if (!checkPermission(sender, "staffcontrol.vanish")) {
            return true;
        }

        Player player = checkPlayer(sender);
        if (player == null) return true;

        User user = (User) staffControl.getUserManager().get(player.getUniqueId());

        if (args.length == 0) {
            toggleVanish(player, user);
        } else {
            Player target = getTarget(sender, args[0]);
            if (target == null) return true;
            User targetUser = (User) staffControl.getUserManager().get(target.getUniqueId());
            toggleVanish(target, targetUser);
            if (targetUser.getVanishType() != VanishType.NONE) {
                lang.sendWithPrefix(sender, "freeze-target-enabled",
                    Lang.target(target.getName()));
            } else {
                lang.sendWithPrefix(sender, "freeze-target-disabled",
                    Lang.target(target.getName()));
            }
        }

        return true;
    }

    private void toggleVanish(Player player, User user) {
        StaffControlPlugin plugin = staffControl.getPlugin();

        if (user.getVanishType() == VanishType.NONE) {
            user.setVanishType(VanishType.TOTAL);
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (!online.equals(player) && !online.hasPermission("staffcontrol.staff")) {
                    online.hidePlayer(plugin, player);
                    player.hidePlayer(plugin, online);
                }
            }
            lang.sendWithPrefix(player, "vanish-enabled");
        } else {
            user.setVanishType(VanishType.NONE);
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (!online.equals(player)) {
                    online.showPlayer(plugin, player);
                    player.showPlayer(plugin, online);
                }
            }
            lang.sendWithPrefix(player, "vanish-disabled");
        }
    }
}
