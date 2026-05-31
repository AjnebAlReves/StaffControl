package net.shortninja.staffplus.server.compatibility.v1_1x;

import net.shortninja.staffplus.IStaffPlus;
import net.shortninja.staffplus.server.compatibility.AbstractProtocol;
import net.shortninja.staffplus.server.compatibility.capabilities.VersionCapabilities;
import net.shortninja.staffplus.server.compatibility.v1_1x.capabilities.V1_17Capabilities;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.Set;

public class Protocol_v1_17_R1 extends AbstractProtocol {
    private final VersionCapabilities capabilities;

    public Protocol_v1_17_R1(IStaffPlus staffPlus) {
        super(staffPlus);
        this.capabilities = new V1_17Capabilities((Plugin) staffPlus);
    }

    @Override
    public ItemStack addNbtString(ItemStack item, String value) {
        if (item == null) {
            return item;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        meta.getPersistentDataContainer().set(
            new NamespacedKey((Plugin) staffPlus, NBT_IDENTIFIER),
            PersistentDataType.STRING,
            value
        );
        item.setItemMeta(meta);

        return item;
    }

    @Override
    public String getNbtString(ItemStack item) {
        if (item == null) {
            return "";
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return "";
        }

        String value = meta.getPersistentDataContainer().get(
            new NamespacedKey((Plugin) staffPlus, NBT_IDENTIFIER),
            PersistentDataType.STRING
        );

        return value != null ? value : "";
    }

    @Override
    public void registerCommand(String match, Command command) {
        try {
            Object server = Bukkit.getServer();
            Method getCommandMap = server.getClass().getMethod("getCommandMap");
            Object commandMap = getCommandMap.invoke(server);
            Method register = commandMap.getClass().getMethod("register", String.class, Command.class);
            register.invoke(commandMap, match, command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listVanish(Player player, boolean shouldEnable) {
        Plugin plugin = (Plugin) staffPlus;

        if (shouldEnable) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.hidePlayer(plugin, player);
            }
        } else {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(plugin, player);
            }
        }
    }

    @Override
    public void sendHoverableJsonMessage(Set<Player> players, String message, String hoverMessage) {
        capabilities.chat().sendHoverableMessage(players, message, hoverMessage);
    }

    @Override
    public String getSound(Object object) {
        return null;
    }

    @Override
    public void inject(Player player) {
    }

    @Override
    public void uninject(Player player) {
    }

    @Override
    public VersionCapabilities capabilities() {
        return capabilities;
    }
}
