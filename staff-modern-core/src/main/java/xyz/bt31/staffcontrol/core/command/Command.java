package xyz.bt31.staffcontrol.core.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import xyz.bt31.staffcontrol.core.StaffControl;
import xyz.bt31.staffcontrol.core.lang.Lang;

import java.util.ArrayList;
import java.util.List;

public abstract class Command implements CommandExecutor, TabCompleter {

    protected final StaffControl staffControl;
    protected final Lang lang;

    public Command(StaffControl staffControl) {
        this.staffControl = staffControl;
        this.lang = staffControl.getLang();
    }

    @Nullable
    protected Player checkPlayer(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            lang.sendWithPrefix(sender, "player-only");
            return null;
        }
        return player;
    }

    protected boolean checkPermission(CommandSender sender, String permission) {
        if (!sender.hasPermission(permission)) {
            lang.sendWithPrefix(sender, "no-permission");
            return false;
        }
        return true;
    }

    protected Player getTarget(CommandSender sender, String name) {
        Player target = sender.getServer().getPlayerExact(name);
        if (target == null) {
            lang.sendWithPrefix(sender, "user-not-found",
                Lang.target(name));
        }
        return target;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd,
                                       String alias, String[] args) {
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            List<String> matches = new ArrayList<>();
            for (Player p : sender.getServer().getOnlinePlayers()) {
                if (p.getName().toLowerCase().startsWith(prefix)) {
                    matches.add(p.getName());
                }
            }
            return matches;
        }
        return List.of();
    }
}
