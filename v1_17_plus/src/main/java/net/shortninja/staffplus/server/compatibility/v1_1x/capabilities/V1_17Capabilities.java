package net.shortninja.staffplus.server.compatibility.v1_1x.capabilities;

import net.shortninja.staffplus.server.compatibility.capabilities.*;
import org.bukkit.plugin.Plugin;

public class V1_17Capabilities implements VersionCapabilities {
    private final ActionBarCapability actionBar;
    private final PlayerVisibilityCapability visibility;
    private final ChatCapability chat;
    private final InventoryCapability inventory;

    public V1_17Capabilities(Plugin plugin) {
        this.actionBar = new ActionBarCapabilityImpl();
        this.visibility = new PlayerVisibilityCapabilityImpl(plugin);
        this.chat = new ChatCapabilityImpl();
        this.inventory = new InventoryCapabilityImpl();
    }

    @Override
    public ActionBarCapability actionBar() {
        return actionBar;
    }

    @Override
    public PlayerVisibilityCapability visibility() {
        return visibility;
    }

    @Override
    public ChatCapability chat() {
        return chat;
    }

    @Override
    public InventoryCapability inventory() {
        return inventory;
    }
}
