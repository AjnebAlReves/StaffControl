package xyz.bt31.staffcontrol.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.bt31.staffcontrol.api.ITicket;
import xyz.bt31.staffcontrol.core.StaffControl;
import xyz.bt31.staffcontrol.core.lang.Lang;
import xyz.bt31.staffcontrol.core.staffmode.TicketHandler;

import java.util.List;

public class TicketCommand extends Command {

    private final TicketHandler ticketHandler;

    public TicketCommand(StaffControl staffControl) {
        super(staffControl);
        this.ticketHandler = new TicketHandler(staffControl);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd,
                             String label, String[] args) {
        if (args.length == 0) {
            lang.sendWithPrefix(sender, "invalid-usage",
                Lang.usage("/ticket <message>"));
            return true;
        }

        String arg = args[0].toLowerCase();

        if (arg.equals("list") && sender.hasPermission("staffcontrol.tickets")) {
            listTickets(sender);
            return true;
        }

        if ((arg.equals("respond") || arg.equals("close")) && sender.hasPermission("staffcontrol.tickets")) {
            if (args.length < 3) {
                lang.sendWithPrefix(sender, "invalid-usage",
                    Lang.usage("/ticket " + arg + " <player|id> <message>"));
                return true;
            }
            if (arg.equals("respond")) {
                handleRespond(sender, args);
            } else {
                handleClose(sender, args);
            }
            return true;
        }

        if (sender instanceof Player player) {
            ITicket ticket = ticketHandler.getTicketByPlayer(player);
            if (ticket == null || ticket.hasBeenClosed()) {
                ticketHandler.createTicket(player, String.join(" ", args));
            } else {
                ticketHandler.sendResponse(player, ticket, String.join(" ", args), false);
            }
        } else {
            lang.sendWithPrefix(sender, "player-only");
        }

        return true;
    }

    private void handleRespond(CommandSender sender, String[] args) {
        ITicket ticket = resolveTicket(args[1]);
        if (ticket == null) {
            lang.sendWithPrefix(sender, "ticket-not-found",
                Lang.unparsed("target", args[1]));
            return;
        }
        String response = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));
        ticketHandler.sendResponse((Player) sender, ticket, response, true);
    }

    private void handleClose(CommandSender sender, String[] args) {
        ITicket ticket = resolveTicket(args[1]);
        if (ticket == null) {
            lang.sendWithPrefix(sender, "ticket-not-found",
                Lang.unparsed("target", args[1]));
            return;
        }
        String reason = args.length >= 3
            ? String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length))
            : "Closed by staff";
        ticketHandler.closeTicket(ticket, reason);
    }

    private void listTickets(CommandSender sender) {
        List<ITicket> tickets = ticketHandler.getOpenTickets();
        if (tickets.isEmpty()) {
            lang.sendWithPrefix(sender, "ticket-list-empty");
            return;
        }
        for (ITicket ticket : tickets) {
            lang.send(sender, "ticket-list-entry",
                Lang.unparsed("id", String.valueOf(ticket.getId())),
                Lang.target(ticket.getName()),
                Lang.reason(ticket.getInquiry()));
        }
    }

    private ITicket resolveTicket(String option) {
        Player player = Bukkit.getPlayer(option);
        if (player != null) {
            return ticketHandler.getTicketByPlayer(player);
        }
        try {
            int id = Integer.parseInt(option);
            return ticketHandler.getTicketById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd,
                                       String alias, String[] args) {
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            return List.of("list", "respond", "close").stream()
                .filter(s -> s.startsWith(prefix))
                .toList();
        }
        if (args.length == 2 && (args[0].equalsIgnoreCase("respond") || args[0].equalsIgnoreCase("close"))) {
            String prefix = args[1].toLowerCase();
            return sender.getServer().getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(n -> n.toLowerCase().startsWith(prefix))
                .toList();
        }
        return List.of();
    }
}
