package xyz.bt31.staffcontrol.core.storage;

import xyz.bt31.staffcontrol.api.IWarning;
import xyz.bt31.staffcontrol.core.model.Warning;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WarningStorage {

    private final DatabaseManager database;
    private final Logger logger;

    public WarningStorage(DatabaseManager database, Logger logger) {
        this.database = database;
        this.logger = logger;
    }

    public void init() {
        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS warnings (" +
                "  uuid VARCHAR(36) PRIMARY KEY," +
                "  target_uuid VARCHAR(36) NOT NULL," +
                "  issuer_uuid VARCHAR(36) NOT NULL," +
                "  issuer_name VARCHAR(64) NOT NULL," +
                "  reason TEXT NOT NULL," +
                "  time BIGINT NOT NULL," +
                "  removed BOOLEAN DEFAULT FALSE" +
                ")"
            );
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not create warnings table", e);
        }
    }

    public void save(IWarning warning) {
        String sql = "INSERT OR REPLACE INTO warnings (uuid, target_uuid, issuer_uuid, issuer_name, reason, time, removed) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, warning.getUuid().toString());
            ps.setString(2, "");
            ps.setString(3, warning.getIssuerUuid().toString());
            ps.setString(4, warning.getIssuerName());
            ps.setString(5, warning.getReason());
            ps.setLong(6, warning.getTime());
            ps.setBoolean(7, warning.shouldRemove());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not save warning", e);
        }
    }

    public void save(IWarning warning, UUID targetUuid) {
        String sql = "INSERT OR REPLACE INTO warnings (uuid, target_uuid, issuer_uuid, issuer_name, reason, time, removed) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, warning.getUuid().toString());
            ps.setString(2, targetUuid.toString());
            ps.setString(3, warning.getIssuerUuid().toString());
            ps.setString(4, warning.getIssuerName());
            ps.setString(5, warning.getReason());
            ps.setLong(6, warning.getTime());
            ps.setBoolean(7, warning.shouldRemove());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not save warning", e);
        }
    }

    public List<IWarning> getByTarget(UUID targetUuid) {
        List<IWarning> results = new ArrayList<>();
        String sql = "SELECT * FROM warnings WHERE target_uuid = ? AND removed = FALSE ORDER BY time DESC";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, targetUuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(map(rs));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not query warnings", e);
        }
        return results;
    }

    public void remove(UUID warningUuid) {
        String sql = "UPDATE warnings SET removed = TRUE WHERE uuid = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, warningUuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not remove warning", e);
        }
    }

    private IWarning map(ResultSet rs) throws SQLException {
        return new Warning(
            UUID.fromString(rs.getString("uuid")),
            UUID.fromString(rs.getString("issuer_uuid")),
            rs.getString("issuer_name"),
            rs.getString("reason"),
            rs.getLong("time"),
            rs.getBoolean("removed")
        );
    }
}
