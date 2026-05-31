package xyz.bt31.staffcontrol.core;

import xyz.bt31.staffcontrol.api.IOptions;
import xyz.bt31.staffcontrol.api.IStaffControl;
import xyz.bt31.staffcontrol.api.IUserManager;
import xyz.bt31.staffcontrol.api.util.IPermissionsHandler;

public class StaffControl implements IStaffControl {

    private final StaffControlPlugin plugin;
    private final UserManager userManager;
    private final PermissionsHandler permissionsHandler;
    private final Options options;

    public StaffControl(StaffControlPlugin plugin) {
        this.plugin = plugin;
        this.userManager = new UserManager();
        this.permissionsHandler = new PermissionsHandler();
        this.options = new Options();
    }

    @Override
    public IOptions getOptions() {
        return options;
    }

    @Override
    public IPermissionsHandler getPermissions() {
        return permissionsHandler;
    }

    @Override
    public IUserManager getUserManager() {
        return userManager;
    }

    public StaffControlPlugin getPlugin() {
        return plugin;
    }
}
