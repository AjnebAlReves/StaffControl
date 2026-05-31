package xyz.bt31.staffcontrol.api;

import xyz.bt31.staffcontrol.api.util.IPermissionsHandler;
import xyz.bt31.staffcontrol.api.IUserManager;

public interface IStaffControl {
    static IStaffControl get() {
        throw new UnsupportedOperationException("No implementation bound to IStaffControl.get()");
    }

    IOptions getOptions();
    IPermissionsHandler getPermissions();
    IUserManager getUserManager();
}
