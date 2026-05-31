package xyz.bt31.staffcontrol.core;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.bt31.staffcontrol.api.IStaffControl;

public class StaffControlPlugin extends JavaPlugin {

    private StaffControl staffControl;

    @Override
    public void onEnable() {
        staffControl = new StaffControl(this);
        getLogger().info("StaffControl v" + getDescription().getVersion() + " enabled");
    }

    @Override
    public void onDisable() {
        if (staffControl != null) {
            staffControl = null;
        }
        getLogger().info("StaffControl disabled");
    }

    public StaffControl getStaffControl() {
        return staffControl;
    }
}
