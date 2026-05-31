package net.shortninja.staffplus.server.compatibility.v1_1x.util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public final class ComponentSerializer {

    public static BaseComponent[] fromLegacy(String legacy) {
        return TextComponent.fromLegacyText(legacy);
    }

    public static BaseComponent[] withHover(String message, String hoverMessage) {
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(message));
        component.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(hoverMessage).create()
        ));
        return new BaseComponent[]{component};
    }

    private ComponentSerializer() {
    }
}
