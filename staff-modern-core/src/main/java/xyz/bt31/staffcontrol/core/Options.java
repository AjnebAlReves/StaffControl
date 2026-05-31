package xyz.bt31.staffcontrol.core;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.bt31.staffcontrol.api.IOptions;

import java.util.Collections;
import java.util.List;

public class Options implements IOptions {

    private final FileConfiguration config;

    private final String storageType;
    private final String sqliteFile;
    private final String mysqlHost;
    private final int mysqlPort;
    private final String mysqlDatabase;
    private final String mysqlUsername;
    private final String mysqlPassword;

    private final String lang;
    private final boolean vanishOnJoin;
    private final boolean alertXray;
    private final boolean alertMention;
    private final boolean alertNameChange;
    private final List<String> soundNames;
    private final boolean ticketsEnabled;
    private final boolean ticketsKeepOpen;

    public Options(FileConfiguration config) {
        this.config = config;
        config.options().copyDefaults(true);

        storageType = config.getString("storage.type", "sqlite");
        sqliteFile = config.getString("storage.sqlite.file", "staffcontrol.db");
        mysqlHost = config.getString("storage.mysql.host", "localhost");
        mysqlPort = config.getInt("storage.mysql.port", 3306);
        mysqlDatabase = config.getString("storage.mysql.database", "staffcontrol");
        mysqlUsername = config.getString("storage.mysql.username", "root");
        mysqlPassword = config.getString("storage.mysql.password", "");

        lang = config.getString("lang", "en");
        vanishOnJoin = config.getBoolean("vanish.on-join", false);
        alertXray = config.getBoolean("alerts.xray", true);
        alertMention = config.getBoolean("alerts.mention", true);
        alertNameChange = config.getBoolean("alerts.name-change", true);
        soundNames = config.getStringList("sounds");
        ticketsEnabled = config.getBoolean("tickets.enabled", true);
        ticketsKeepOpen = config.getBoolean("tickets.keep-open", false);
    }

    public String getStorageType() {
        return storageType;
    }

    public String getSqliteFile() {
        return sqliteFile;
    }

    public String getMysqlHost() {
        return mysqlHost;
    }

    public int getMysqlPort() {
        return mysqlPort;
    }

    public String getMysqlDatabase() {
        return mysqlDatabase;
    }

    public String getMysqlUsername() {
        return mysqlUsername;
    }

    public String getMysqlPassword() {
        return mysqlPassword;
    }

    public String getLang() {
        return lang;
    }

    public boolean isVanishOnJoin() {
        return vanishOnJoin;
    }

    public boolean isAlertXray() {
        return alertXray;
    }

    public boolean isAlertMention() {
        return alertMention;
    }

    public boolean isAlertNameChange() {
        return alertNameChange;
    }

    public List<String> getSoundNames() {
        return soundNames;
    }

    public boolean isTicketsEnabled() {
        return ticketsEnabled;
    }

    public boolean isTicketsKeepOpen() {
        return ticketsKeepOpen;
    }
}
