package xyz.bt31.staffcontrol.core;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.bt31.staffcontrol.api.IStaffControl;

public class StaffControlPlugin extends JavaPlugin {

    private static StaffControl instance;

    @Override
    public void onEnable() {
        instance = new StaffControl(this);
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
