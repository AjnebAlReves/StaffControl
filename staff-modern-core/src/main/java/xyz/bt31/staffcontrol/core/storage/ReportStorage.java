package xyz.bt31.staffcontrol.core.storage;

import xyz.bt31.staffcontrol.api.IReport;
import xyz.bt31.staffcontrol.core.model.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportStorage {

    private final DatabaseManager database;
    private final Logger logger;

    public ReportStorage(DatabaseManager database, Logger logger) {
        this.database = database;
        this.logger = logger;
    }

    public void init() {
        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS reports (" +
                "  uuid VARCHAR(36) PRIMARY KEY," +
                "  reporter_uuid VARCHAR(36) NOT NULL," +
                "  reporter_name VARCHAR(64) NOT NULL," +
                "  reason TEXT NOT NULL," +
                "  timestamp BIGINT NOT NULL" +
                ")"
            );
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not create reports table", e);
        }
    }

    public void save(IReport report) {
        String sql = "INSERT OR REPLACE INTO reports (uuid, reporter_uuid, reporter_name, reason, timestamp) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, report.getUuid().toString());
            ps.setString(2, report.getReporterUuid().toString());
            ps.setString(3, report.getReporterName());
            ps.setString(4, report.getReason());
            ps.setLong(5, System.currentTimeMillis());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not save report", e);
        }
    }

    public List<IReport> getByReporter(UUID reporterUuid) {
        List<IReport> results = new ArrayList<>();
        String sql = "SELECT * FROM reports WHERE reporter_uuid = ? ORDER BY timestamp DESC";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, reporterUuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(map(rs));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not query reports", e);
        }
        return results;
    }

    public List<IReport> getAll() {
        List<IReport> results = new ArrayList<>();
        String sql = "SELECT * FROM reports ORDER BY timestamp DESC";
        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                results.add(map(rs));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not query reports", e);
        }
        return results;
    }

    private IReport map(ResultSet rs) throws SQLException {
        return new Report(
            UUID.fromString(rs.getString("uuid")),
            UUID.fromString(rs.getString("reporter_uuid")),
            rs.getString("reporter_name"),
            rs.getString("reason"),
            rs.getLong("timestamp")
        );
    }
}
