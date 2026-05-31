package xyz.bt31.staffcontrol.core.storage;

import xyz.bt31.staffcontrol.core.Options;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {

    private final Logger logger;
    private final Options options;
    private final File dataFolder;
    private Connection connection;

    public DatabaseManager(Logger logger, Options options, File dataFolder) {
        this.logger = logger;
        this.options = options;
        this.dataFolder = dataFolder;
    }

    public Connection getConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed() && connection.isValid(1)) {
                    return connection;
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Database connection invalid, reconnecting", e);
            }
        }
        return connect();
    }

    private Connection connect() {
        try {
            String storageType = options.getStorageType();
            if ("mysql".equalsIgnoreCase(storageType)) {
                String url = "jdbc:mysql://" + options.getMysqlHost() + ":" + options.getMysqlPort()
                    + "/" + options.getMysqlDatabase() + "?useSSL=false";
                connection = DriverManager.getConnection(url, options.getMysqlUsername(), options.getMysqlPassword());
            } else {
                File dbFile = new File(dataFolder, options.getSqliteFile());
                connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
            }
            return connection;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not connect to database", e);
            return null;
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Could not close database connection", e);
            }
        }
    }
}
