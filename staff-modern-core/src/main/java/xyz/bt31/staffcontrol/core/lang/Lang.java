package xyz.bt31.staffcontrol.core.lang;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import xyz.bt31.staffcontrol.core.StaffControl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class Lang {

    private static final String MESSAGES_PREFIX = "messages-";
    private static final String DEFAULT_LOCALE = "en";

    private final StaffControl staffControl;
    private final MiniMessage miniMessage;
    private final Map<String, String> messages;

    public Lang(StaffControl staffControl) {
        this.staffControl = staffControl;
        this.miniMessage = MiniMessage.miniMessage();
        this.messages = new HashMap<>();
    }

    public void load() {
        messages.clear();
        String locale = staffControl.getOptions().getLang();
        String fileName = MESSAGES_PREFIX + locale + ".yml";

        File dataFolder = staffControl.getPlugin().getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            saveDefaultLocale(file, locale);
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String key : config.getKeys(true)) {
            if (config.isString(key)) {
                messages.put(key, config.getString(key));
            }
        }
    }

    private void saveDefaultLocale(File file, String locale) {
        String resourcePath = MESSAGES_PREFIX + locale + ".yml";
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (in != null) {
                Files.copy(in, file.toPath());
            } else if (!DEFAULT_LOCALE.equals(locale)) {
                staffControl.getPlugin().getLogger().warning(
                    "Language file " + resourcePath + " not found, falling back to en");
                saveDefaultLocale(file, DEFAULT_LOCALE);
            } else {
                staffControl.getPlugin().getLogger().severe(
                    "Default language file messages-en.yml not found in JAR");
            }
        } catch (IOException e) {
            staffControl.getPlugin().getLogger().log(Level.SEVERE,
                "Could not save " + file.getName(), e);
        }
    }

    public @NotNull String raw(String key) {
        return messages.getOrDefault(key,
            "<red>Missing message key: <yellow>" + key + "</yellow></red>");
    }

    public @NotNull Component deserialize(String key, TagResolver... resolvers) {
        String template = raw(key);
        return miniMessage.deserialize(template, resolvers);
    }

    public @NotNull Component prefix() {
        return deserialize("prefix");
    }

    public void send(Audience audience, String key, TagResolver... resolvers) {
        audience.sendMessage(deserialize(key, resolvers));
    }

    public void sendWithPrefix(Audience audience, String key, TagResolver... resolvers) {
        audience.sendMessage(
            prefix().append(Component.space()).append(deserialize(key, resolvers)));
    }

    public static TagResolver player(String name) {
        return Placeholder.unparsed("player", name);
    }

    public static TagResolver target(String name) {
        return Placeholder.unparsed("target", name);
    }

    public static TagResolver staff(String name) {
        return Placeholder.unparsed("staff", name);
    }

    public static TagResolver reporter(String name) {
        return Placeholder.unparsed("reporter", name);
    }

    public static TagResolver reason(String reason) {
        return Placeholder.unparsed("reason", reason);
    }

    public static TagResolver usage(String usage) {
        return Placeholder.unparsed("usage", usage);
    }
}
