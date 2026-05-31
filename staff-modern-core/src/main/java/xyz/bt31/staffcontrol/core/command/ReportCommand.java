package xyz.bt31.staffcontrol.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.bt31.staffcontrol.api.IReport;
import xyz.bt31.staffcontrol.core.StaffControl;
import xyz.bt31.staffcontrol.core.lang.Lang;
import xyz.bt31.staffcontrol.core.model.Report;

public class ReportCommand extends Command {

    public ReportCommand(StaffControl staffControl) {
        super(staffControl);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd,
                             String label, String[] args) {
        if (!checkPermission(sender, "staffcontrol.report")) {
            return true;
        }

        Player player = checkPlayer(sender);
        if (player == null) return true;

        if (args.length < 2) {
            lang.sendWithPrefix(sender, "invalid-usage",
                Lang.usage("/report <player> <reason>"));
            return true;
        }

        Player target = getTarget(sender, args[0]);
        if (target == null) return true;

        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));

        IReport report = new Report(player.getUniqueId(), player.getName(), reason);
        staffControl.getReportStorage().save(report);

        lang.sendWithPrefix(sender, "report-sent");
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission("staffcontrol.reports.receive")) {
                lang.sendWithPrefix(online, "report-received",
                    Lang.reporter(player.getName()),
                    Lang.reason(reason));
            }
        }

        return true;
    }
}
