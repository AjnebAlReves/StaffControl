package xyz.bt31.staffcontrol.core;

import xyz.bt31.staffcontrol.api.IOptions;
import xyz.bt31.staffcontrol.api.IStaffControl;
import xyz.bt31.staffcontrol.api.util.IPermissionsHandler;
import xyz.bt31.staffcontrol.core.lang.Lang;
import xyz.bt31.staffcontrol.core.staffmode.InspectGui;
import xyz.bt31.staffcontrol.core.staffmode.StaffModeManager;
import xyz.bt31.staffcontrol.core.storage.DatabaseManager;
import xyz.bt31.staffcontrol.core.storage.ReportStorage;
import xyz.bt31.staffcontrol.core.storage.TicketStorage;
import xyz.bt31.staffcontrol.core.storage.WarningStorage;

public class StaffControl implements IStaffControl {

    private final StaffControlPlugin plugin;
    private final UserManager userManager;
    private final PermissionsHandler permissionsHandler;
    private final Options options;
    private final DatabaseManager database;
    private final ReportStorage reportStorage;
    private final WarningStorage warningStorage;
    private final TicketStorage ticketStorage;
    private final StaffModeManager staffModeManager;
    private final InspectGui inspectGui;
    private Lang lang;

    public StaffControl(StaffControlPlugin plugin) {
        this.plugin = plugin;
        this.userManager = new UserManager();
        this.permissionsHandler = new PermissionsHandler();
        this.options = new Options(plugin.getConfig());
        this.database = new DatabaseManager(plugin.getLogger(), options, plugin.getDataFolder());
        this.reportStorage = new ReportStorage(database, plugin.getLogger());
        this.warningStorage = new WarningStorage(database, plugin.getLogger());
        this.ticketStorage = new TicketStorage(database, plugin.getLogger());
        this.staffModeManager = new StaffModeManager(this);
        this.inspectGui = new InspectGui(this);
        this.lang = new Lang(this);
        initStorage();
    }

    private void initStorage() {
        reportStorage.init();
        warningStorage.init();
        ticketStorage.init();
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

    public DatabaseManager getDatabase() {
        return database;
    }

    public ReportStorage getReportStorage() {
        return reportStorage;
    }

    public WarningStorage getWarningStorage() {
        return warningStorage;
    }

    public TicketStorage getTicketStorage() {
        return ticketStorage;
    }

    public StaffModeManager getStaffModeManager() {
        return staffModeManager;
    }

    public InspectGui getInspectGui() {
        return inspectGui;
    }

    public void reloadLang() {
        this.lang = new Lang(this);
        lang.load();
    }
}
