package xyz.bt31.staffcontrol.core;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.bt31.staffcontrol.core.listener.ChatAlertListener;
import xyz.bt31.staffcontrol.core.listener.PlayerJoinListener;
import xyz.bt31.staffcontrol.core.listener.PlayerQuitListener;

public class StaffControlPlugin extends JavaPlugin {

    private static StaffControl instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = new StaffControl(this);
        instance.getLang().load();

        getServer().getPluginManager().registerEvents(
            new PlayerJoinListener(instance, instance.getUserManager()), this);
        getServer().getPluginManager().registerEvents(
            new PlayerQuitListener(instance.getUserManager()), this);
        getServer().getPluginManager().registerEvents(
            new ChatAlertListener(instance), this);

        instance.getLang().sendWithPrefix(getServer().getConsoleSender(), "reload-complete");
        getLogger().info("StaffControl v" + getDescription().getVersion() + " enabled");
    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().info("StaffControl disabled");
    }

    public static StaffControl get() {
        return instance;
    }
}
