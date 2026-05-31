package xyz.bt31.staffcontrol.core;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.bt31.staffcontrol.core.command.*;
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

        registerCommands();
        registerListeners();

        instance.getLang().sendWithPrefix(getServer().getConsoleSender(), "reload-complete");
        getLogger().info("StaffControl v" + getDescription().getVersion() + " enabled");
    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().info("StaffControl disabled");
    }

    private void registerCommands() {
        Command vanish = new VanishCommand(instance);
        getCommand("vanish").setExecutor(vanish);
        getCommand("vanish").setTabCompleter(vanish);

        Command freeze = new FreezeCommand(instance);
        getCommand("freeze").setExecutor(freeze);
        getCommand("freeze").setTabCompleter(freeze);

        Command report = new ReportCommand(instance);
        getCommand("report").setExecutor(report);
        getCommand("report").setTabCompleter(report);

        Command warn = new WarnCommand(instance);
        getCommand("warn").setExecutor(warn);
        getCommand("warn").setTabCompleter(warn);

        Command staff = new StaffCommand(instance);
        getCommand("staff").setExecutor(staff);
        getCommand("staff").setTabCompleter(staff);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(
            new PlayerJoinListener(instance, instance.getUserManager()), this);
        getServer().getPluginManager().registerEvents(
            new PlayerQuitListener(instance.getUserManager()), this);
        getServer().getPluginManager().registerEvents(
            new ChatAlertListener(instance), this);
    }

    public static StaffControl get() {
        return instance;
    }
}
