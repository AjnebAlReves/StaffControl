package xyz.bt31.staffcontrol.core.storage;

import xyz.bt31.staffcontrol.api.ITicket;
import xyz.bt31.staffcontrol.core.model.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketStorage {

    private final DatabaseManager database;
    private final Logger logger;

    public TicketStorage(DatabaseManager database, Logger logger) {
        this.database = database;
        this.logger = logger;
    }

    public void init() {
        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS tickets (" +
                "  uuid VARCHAR(36) NOT NULL," +
                "  name VARCHAR(64) NOT NULL," +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  inquiry TEXT NOT NULL," +
                "  handler_name VARCHAR(64) DEFAULT ''," +
                "  has_been_closed BOOLEAN DEFAULT FALSE," +
                "  creation_time BIGINT NOT NULL" +
                ")"
            );
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not create tickets table", e);
        }
    }

    public void save(ITicket ticket) {
        String sql = "INSERT INTO tickets (uuid, name, inquiry, handler_name, has_been_closed, creation_time) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ticket.getUuid().toString());
            ps.setString(2, ticket.getName());
            ps.setString(3, ticket.getInquiry());
            ps.setString(4, ticket.getHandlerName());
            ps.setBoolean(5, ticket.hasBeenClosed());
            ps.setLong(6, ticket.getCreationTime());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not save ticket", e);
        }
    }

    public void update(ITicket ticket) {
        String sql = "UPDATE tickets SET handler_name = ?, has_been_closed = ? WHERE uuid = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ticket.getHandlerName());
            ps.setBoolean(2, ticket.hasBeenClosed());
            ps.setString(3, ticket.getUuid().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not update ticket", e);
        }
    }

    public void remove(UUID ticketUuid) {
        String sql = "DELETE FROM tickets WHERE uuid = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ticketUuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not remove ticket", e);
        }
    }

    public ITicket getByUuid(UUID playerUuid) {
        String sql = "SELECT * FROM tickets WHERE uuid = ? AND has_been_closed = FALSE ORDER BY creation_time DESC LIMIT 1";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, playerUuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not query ticket", e);
        }
        return null;
    }

    public ITicket getById(int id) {
        String sql = "SELECT * FROM tickets WHERE id = ? AND has_been_closed = FALSE";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not query ticket by id", e);
        }
        return null;
    }

    public List<ITicket> getOpenTickets() {
        List<ITicket> results = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE has_been_closed = FALSE ORDER BY creation_time DESC";
        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                results.add(map(rs));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not query open tickets", e);
        }
        return results;
    }

    public int getNextId() {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM tickets";
        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not get next ticket id", e);
        }
        return 1;
    }

    private ITicket map(ResultSet rs) throws SQLException {
        return new Ticket(
            UUID.fromString(rs.getString("uuid")),
            rs.getString("name"),
            rs.getInt("id"),
            rs.getString("inquiry"),
            rs.getLong("creation_time"),
            rs.getString("handler_name"),
            rs.getBoolean("has_been_closed")
        );
    }
}
