package xyz.bt31.staffcontrol.api;

public interface IHook {
    String getPluginName();
    String getPluginVersion();
    void onEnable();
    void onDisable();
    boolean canHook();
}
