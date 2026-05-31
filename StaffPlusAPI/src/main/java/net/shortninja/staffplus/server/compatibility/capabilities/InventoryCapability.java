package net.shortninja.staffplus.server.compatibility.capabilities;

import org.bukkit.inventory.ItemStack;

public interface InventoryCapability {
    default String getItemName(ItemStack item) {
        return item.hasItemMeta() && item.getItemMeta().hasDisplayName()
            ? item.getItemMeta().getDisplayName()
            : item.getType().toString();
    }
}
