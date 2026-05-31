package xyz.bt31.staffcontrol.api.capabilities;

import org.bukkit.inventory.ItemStack;

public interface InventoryCapability {
    default String getItemName(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            return item.getItemMeta().getDisplayName();
        }
        return item.getType().name();
    }
}
