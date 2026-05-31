package xyz.bt31.staffcontrol.core.capabilities;

import xyz.bt31.staffcontrol.api.capabilities.*;

public class ModernVersionCapabilities implements VersionCapabilities {

    private final ActionBarCapability actionBar = new ModernActionBarCapability();
    private final PlayerVisibilityCapability visibility = new ModernPlayerVisibilityCapability();
    private final ChatCapability chat = new ModernChatCapability();
    private final InventoryCapability inventory = new ModernInventoryCapability();

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
