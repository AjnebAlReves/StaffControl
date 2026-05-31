package xyz.bt31.staffcontrol.core.staffmode;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.bt31.staffcontrol.core.StaffControl;
import xyz.bt31.staffcontrol.core.lang.Lang;
import xyz.bt31.staffcontrol.core.user.User;

import java.util.HashMap;
import java.util.Map;

public class StaffModeManager {

    private final StaffControl staffControl;
    private final Map<Integer, StaffModeItem> items;

    public StaffModeManager(StaffControl staffControl) {
        this.staffControl = staffControl;
        this.items = new HashMap<>();
        loadItems();
    }

    public void reload() {
        loadItems();
    }

    private void loadItems() {
        items.clear();
        ConfigurationSection section = staffControl.getPlugin().getConfig()
            .getConfigurationSection("staff-mode.items");
        if (section == null) return;

        for (String key : section.getKeys(false)) {
            int slot = section.getInt(key + ".slot", -1);
            if (slot < 0 || slot > 8) continue;

            Material material = Material.matchMaterial(section.getString(key + ".material", "STONE"));
            if (material == null) material = Material.STONE;

            String name = section.getString(key + ".name", "");
            String command = section.getString(key + ".command", "");
            int amount = section.getInt(key + ".amount", 1);

            items.put(slot, new StaffModeItem(material, amount, name, command));
        }
    }

    public void toggle(Player player) {
        User user = (User) staffControl.getUserManager().get(player.getUniqueId());
        if (user.isStaffMode()) {
            disable(player, user);
        } else {
            enable(player, user);
        }
    }

    public void enable(Player player, User user) {
        user.setStaffMode(true);
        giveItems(player);
        staffControl.getLang().sendWithPrefix(player, "staff-mode-enabled");
    }

    public void disable(Player player, User user) {
        user.setStaffMode(false);
        clearItems(player);
        staffControl.getLang().sendWithPrefix(player, "staff-mode-disabled");
    }

    public void disable(Player player) {
        User user = (User) staffControl.getUserManager().get(player.getUniqueId());
        if (user.isStaffMode()) {
            disable(player, user);
        }
    }

    private void giveItems(Player player) {
        for (Map.Entry<Integer, StaffModeItem> entry : items.entrySet()) {
            ItemStack item = entry.getValue().build();
            player.getInventory().setItem(entry.getKey(), item);
        }
    }

    private void clearItems(Player player) {
        for (int slot : items.keySet()) {
            player.getInventory().setItem(slot, null);
        }
    }

    public void handleItemClick(Player player, int slot) {
        StaffModeItem modeItem = items.get(slot);
        if (modeItem == null) return;

        String command = modeItem.getCommand();
        if (command == null || command.isEmpty()) return;

        command = command.replace("%player%", player.getName());
        player.performCommand(command.startsWith("/") ? command.substring(1) : command);
    }

    public Map<Integer, StaffModeItem> getItems() {
        return items;
    }

    public static class StaffModeItem {
        private final Material material;
        private final int amount;
        private final String name;
        private final String command;

        public StaffModeItem(Material material, int amount, String name, String command) {
            this.material = material;
            this.amount = amount;
            this.name = name;
            this.command = command;
        }

        public ItemStack build() {
            ItemStack item = new ItemStack(material, amount);
            if (name != null && !name.isEmpty()) {
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', name));
                item.setItemMeta(meta);
            }
            return item;
        }

        public String getCommand() {
            return command;
        }

        public Material getMaterial() {
            return material;
        }

        public int getAmount() {
            return amount;
        }

        public String getName() {
            return name;
        }
    }
}
