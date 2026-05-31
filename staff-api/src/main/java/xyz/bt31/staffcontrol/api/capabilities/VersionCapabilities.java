package xyz.bt31.staffcontrol.api.capabilities;

public interface VersionCapabilities {
    ActionBarCapability actionBar();
    PlayerVisibilityCapability visibility();
    ChatCapability chat();
    InventoryCapability inventory();

    default boolean hasActionBar() {
        return true;
    }

    default boolean hasPlayerVisibility() {
        return true;
    }

    default boolean hasChat() {
        return true;
    }

    default boolean hasInventory() {
        return true;
    }
}
