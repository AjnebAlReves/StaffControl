package xyz.bt31.staffcontrol.core.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.bt31.staffcontrol.api.IWarning;
import xyz.bt31.staffcontrol.core.StaffControl;
import xyz.bt31.staffcontrol.core.lang.Lang;
import xyz.bt31.staffcontrol.core.model.Warning;

import java.util.UUID;

public class WarnCommand extends Command {

    public WarnCommand(StaffControl staffControl) {
        super(staffControl);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd,
                             String label, String[] args) {
        if (!checkPermission(sender, "staffcontrol.warn")) {
            return true;
        }

        if (args.length < 2) {
            lang.sendWithPrefix(sender, "invalid-usage",
                Lang.usage("/warn <player> <reason>"));
            return true;
        }

        Player target = getTarget(sender, args[0]);
        if (target == null) return true;

        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        String senderName = sender instanceof Player ? sender.getName() : "Console";
        UUID senderUuid = sender instanceof Player ? ((Player) sender).getUniqueId() : UUID.randomUUID();

        IWarning warning = new Warning(senderUuid, senderName, reason);
        staffControl.getWarningStorage().save(warning, target.getUniqueId());

        lang.sendWithPrefix((CommandSender) target, "warn-received",
            Lang.staff(senderName), Lang.reason(reason));
        lang.sendWithPrefix(sender, "warn-issued", Lang.target(target.getName()));

        return true;
    }
}
