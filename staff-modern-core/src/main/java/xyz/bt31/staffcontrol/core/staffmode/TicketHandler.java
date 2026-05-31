package xyz.bt31.staffcontrol.core.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.bt31.staffcontrol.api.ITicket;
import xyz.bt31.staffcontrol.core.StaffControl;
import xyz.bt31.staffcontrol.core.lang.Lang;
import xyz.bt31.staffcontrol.core.model.Ticket;
import xyz.bt31.staffcontrol.core.Options;

import java.util.List;
import java.util.UUID;

public class TicketHandler {

    private final StaffControl staffControl;

    public TicketHandler(StaffControl staffControl) {
        this.staffControl = staffControl;
    }

    public ITicket createTicket(Player player, String inquiry) {
        int id = staffControl.getTicketStorage().getNextId();
        Ticket ticket = new Ticket(player.getUniqueId(), player.getName(), id, inquiry, System.currentTimeMillis());
        staffControl.getTicketStorage().save(ticket);

        staffControl.getLang().sendWithPrefix(player, "ticket-submitted",
            Lang.unparsed("id", String.valueOf(id)));

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission("staffcontrol.tickets") && !online.equals(player)) {
                staffControl.getLang().sendWithPrefix(online, "ticket-opened",
                    Lang.target(player.getName()),
                    Lang.unparsed("id", String.valueOf(id)),
                    Lang.reason(inquiry));
            }
        }

        return ticket;
    }

    public ITicket getTicketByPlayer(Player player) {
        return staffControl.getTicketStorage().getByUuid(player.getUniqueId());
    }

    public ITicket getTicketById(int id) {
        return staffControl.getTicketStorage().getById(id);
    }

    public List<ITicket> getOpenTickets() {
        return staffControl.getTicketStorage().getOpenTickets();
    }

    public void sendResponse(Player sender, ITicket ticket, String response, boolean isStaffResponse) {
        Player target = Bukkit.getPlayer(isStaffResponse ? ticket.getName() : ticket.getHandlerName());

        if (isStaffResponse) {
            if (target != null) {
                staffControl.getLang().sendWithPrefix(target, "ticket-response-user",
                    Lang.staff(sender.getName()),
                    Lang.unparsed("id", String.valueOf(ticket.getId())),
                    Lang.reason(response));
            }
            staffControl.getLang().sendWithPrefix(sender, "ticket-response-staff",
                Lang.target(ticket.getName()),
                Lang.unparsed("id", String.valueOf(ticket.getId())),
                Lang.reason(response));
        } else {
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("staffcontrol.tickets") && !online.equals(sender)) {
                    staffControl.getLang().sendWithPrefix(online, "ticket-response-user",
                        Lang.staff(sender.getName()),
                        Lang.unparsed("id", String.valueOf(ticket.getId())),
                        Lang.reason(response));
                }
            }
            staffControl.getLang().sendWithPrefix(sender, "ticket-submitted",
                Lang.unparsed("id", String.valueOf(ticket.getId())));
        }

        if (!staffControl.getOptions().isTicketsKeepOpen() && isStaffResponse) {
            ticket.setHandlerName(sender.getName());
            staffControl.getTicketStorage().update(ticket);
        }
    }

    public void closeTicket(ITicket ticket, String reason) {
        ticket.setHasBeenClosed(true);
        staffControl.getTicketStorage().update(ticket);

        Player ticketOwner = Bukkit.getPlayer(ticket.getName());
        if (ticketOwner != null) {
            staffControl.getLang().sendWithPrefix(ticketOwner, "ticket-closed",
                Lang.unparsed("id", String.valueOf(ticket.getId())),
                Lang.reason(reason));
        }

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission("staffcontrol.tickets")) {
                staffControl.getLang().sendWithPrefix(online, "ticket-removed",
                    Lang.target(ticket.getName()),
                    Lang.unparsed("id", String.valueOf(ticket.getId())),
                    Lang.reason(reason));
            }
        }
    }

    public void closeTicketOnQuit(UUID playerUuid) {
        ITicket ticket = staffControl.getTicketStorage().getByUuid(playerUuid);
        if (ticket != null && !ticket.hasBeenClosed()) {
            ticket.setHasBeenClosed(true);
            staffControl.getTicketStorage().update(ticket);

            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("staffcontrol.tickets")) {
                    staffControl.getLang().sendWithPrefix(online, "ticket-removed",
                        Lang.target(ticket.getName()),
                        Lang.unparsed("id", String.valueOf(ticket.getId())),
                        Lang.reason("Player logged off"));
                }
            }
        }
    }
}
