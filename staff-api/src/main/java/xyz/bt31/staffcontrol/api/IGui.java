package xyz.bt31.staffcontrol.api;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface IGui {
    String getTitle();
    Inventory getInventory();
    IAction getAction(int slot);
    void setItem(int slot, ItemStack item, IAction action);
    void setGlass(IUser user);
}
