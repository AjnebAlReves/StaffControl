package xyz.bt31.staffcontrol.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class AbstractHook implements IHook {
    private final Plugin plugin;

    protected AbstractHook(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean canHook() {
        Plugin hooked = Bukkit.getPluginManager().getPlugin(getPluginName());
        return hooked != null
            && hooked.isEnabled()
            && hooked.getDescription().getVersion().startsWith(getPluginVersion());
    }
}
