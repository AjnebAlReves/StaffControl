package xyz.bt31.staffcontrol.core.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.bt31.staffcontrol.api.IAction;
import xyz.bt31.staffcontrol.api.IGui;
import xyz.bt31.staffcontrol.api.IUser;

import java.util.HashMap;
import java.util.Map;

public class Gui implements IGui {

    private final String title;
    private final Inventory inventory;
    private final Map<Integer, IAction> actions;

    public Gui(String title, int size) {
        this.title = title;
        this.inventory = Bukkit.createInventory(null, size, title);
        this.actions = new HashMap<>();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public IAction getAction(int slot) {
        return actions.get(slot);
    }

    @Override
    public void setItem(int slot, ItemStack item, IAction action) {
        inventory.setItem(slot, item);
        if (action != null) {
            actions.put(slot, action);
        }
    }

    @Override
    public void setGlass(IUser user) {
        short color = user.getGlassColor();
        ItemStack glass = new ItemStack(org.bukkit.Material.valueOf("STAINED_GLASS_PANE"), 1, color);
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, glass);
            }
        }
    }
}
