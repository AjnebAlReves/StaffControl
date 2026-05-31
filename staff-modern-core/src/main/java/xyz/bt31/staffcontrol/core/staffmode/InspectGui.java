package xyz.bt31.staffcontrol.core.staffmode;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import xyz.bt31.staffcontrol.api.IGui;
import xyz.bt31.staffcontrol.core.StaffControl;
import xyz.bt31.staffcontrol.core.lang.Lang;

import java.util.HashMap;
import java.util.Map;

public class InspectGui {

    private final StaffControl staffControl;

    public InspectGui(StaffControl staffControl) {
        this.staffControl = staffControl;
    }

    public IGui create(Player staff, Player target) {
        Gui gui = new Gui("Inventory: " + target.getName(), 45);

        PlayerInventory inv = target.getInventory();

        gui.setItem(0, inv.getHelmet(), null);
        gui.setItem(1, inv.getChestplate(), null);
        gui.setItem(2, inv.getLeggings(), null);
        gui.setItem(3, inv.getBoots(), null);

        gui.setItem(5, inv.getItemInOffHand(), null);

        for (int i = 0; i < 27; i++) {
            gui.setItem(9 + i, inv.getItem(i), null);
        }

        for (int i = 0; i < 9; i++) {
            gui.setItem(36 + i, inv.getItem(i + 27), null);
        }

        return gui;
    }
}
