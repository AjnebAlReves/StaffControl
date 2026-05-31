package xyz.bt31.staffcontrol.core;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.bt31.staffcontrol.api.util.IPermissionsHandler;

public class PermissionsHandler implements IPermissionsHandler {

    @Override
    public boolean has(Player player, String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public boolean hasOnly(Player player, String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public boolean has(CommandSender sender, String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public boolean isOp(Player player) {
        return player.isOp();
    }

    @Override
    public boolean isOp(CommandSender sender) {
        return sender.isOp();
    }

    @Override
    public int getStaffCount() {
        return (int) plugin().getServer().getOnlinePlayers().stream()
            .filter(p -> p.hasPermission("staffcontrol.staff"))
            .count();
    }

    private StaffControlPlugin plugin() {
        return StaffControlPlugin.getPlugin(StaffControlPlugin.class);
    }
}
