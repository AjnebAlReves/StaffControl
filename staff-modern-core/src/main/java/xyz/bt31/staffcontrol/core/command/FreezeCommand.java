package xyz.bt31.staffcontrol.core.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.bt31.staffcontrol.core.StaffControl;
import xyz.bt31.staffcontrol.core.lang.Lang;
import xyz.bt31.staffcontrol.core.user.User;

public class FreezeCommand extends Command {

    public FreezeCommand(StaffControl staffControl) {
        super(staffControl);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd,
                             String label, String[] args) {
        if (!checkPermission(sender, "staffcontrol.freeze")) {
            return true;
        }

        if (args.length < 1) {
            lang.sendWithPrefix(sender, "invalid-usage", Lang.usage("/freeze <player>"));
            return true;
        }

        Player target = getTarget(sender, args[0]);
        if (target == null) return true;

        User targetUser = (User) staffControl.getUserManager().get(target.getUniqueId());
        String senderName = sender instanceof Player ? sender.getName() : "Console";

        if (targetUser.isFrozen()) {
            targetUser.setFrozen(false);
            lang.sendWithPrefix((CommandSender) target, "freeze-disabled",
                Lang.staff(senderName));
            lang.sendWithPrefix(sender, "freeze-target-disabled",
                Lang.target(target.getName()));
        } else {
            targetUser.setFrozen(true);
            lang.sendWithPrefix((CommandSender) target, "freeze-enabled",
                Lang.staff(senderName));
            lang.sendWithPrefix(sender, "freeze-target-enabled",
                Lang.target(target.getName()));
        }

        return true;
    }
}
