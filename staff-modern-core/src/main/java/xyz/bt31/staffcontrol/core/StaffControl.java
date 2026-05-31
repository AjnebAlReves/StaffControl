package xyz.bt31.staffcontrol.core;

import xyz.bt31.staffcontrol.api.IOptions;
import xyz.bt31.staffcontrol.api.IStaffControl;
import xyz.bt31.staffcontrol.api.util.IPermissionsHandler;
import xyz.bt31.staffcontrol.core.lang.Lang;

public class StaffControl implements IStaffControl {

    private final StaffControlPlugin plugin;
    private final UserManager userManager;
    private final PermissionsHandler permissionsHandler;
    private final Options options;
    private Lang lang;

    public StaffControl(StaffControlPlugin plugin) {
        this.plugin = plugin;
        this.userManager = new UserManager();
        this.permissionsHandler = new PermissionsHandler();
        this.options = new Options(plugin.getConfig());
        this.lang = new Lang(this);
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
    public UserManager getUserManager() {
        return userManager;
    }

    public StaffControlPlugin getPlugin() {
        return plugin;
    }

    public Lang getLang() {
        return lang;
    }

    public void reloadLang() {
        this.lang = new Lang(this);
        lang.load();
    }
}
