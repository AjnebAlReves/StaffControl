package xyz.bt31.staffcontrol.core.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.bt31.staffcontrol.core.StaffControl;
import xyz.bt31.staffcontrol.core.lang.Lang;
import xyz.bt31.staffcontrol.core.user.User;

import java.util.List;

public class StaffCommand extends Command {

    public StaffCommand(StaffControl staffControl) {
        super(staffControl);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd,
                             String label, String[] args) {
        if (!checkPermission(sender, "staffcontrol.staff")) {
            return true;
        }

        if (args.length == 0) {
            lang.sendWithPrefix(sender, "invalid-usage",
                Lang.usage("/staff <reload|chat>"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                if (!checkPermission(sender, "staffcontrol.reload")) return true;
                staffControl.getPlugin().reloadConfig();
                staffControl.reloadLang();
                lang.load();
                lang.sendWithPrefix(sender, "reload-complete");
            }
            case "chat" -> {
                Player player = checkPlayer(sender);
                if (player == null) return true;
                User user = (User) staffControl.getUserManager().get(player.getUniqueId());
                boolean toggled = !user.isChatting();
                user.setChatting(toggled);
                if (toggled) {
                    lang.sendWithPrefix(sender, "staff-chat-toggled-on");
                } else {
                    lang.sendWithPrefix(sender, "staff-chat-toggled-off");
                }
            }
            default -> lang.sendWithPrefix(sender, "invalid-usage",
                Lang.usage("/staff <reload|chat>"));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd,
                                       String alias, String[] args) {
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            return List.of("reload", "chat").stream()
                .filter(s -> s.startsWith(prefix))
                .toList();
        }
        return List.of();
    }
}
