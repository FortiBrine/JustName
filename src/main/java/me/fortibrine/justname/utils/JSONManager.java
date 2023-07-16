package me.fortibrine.justname.utils;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public final class JSONManager {

    public static String supportMessagesJSON(String nameMessage) {

        if (nameMessage.startsWith("[json]")) {
            return new TextComponent(ComponentSerializer.parse(nameMessage.substring(5))).toString();
        }
        return nameMessage;
    }
}
