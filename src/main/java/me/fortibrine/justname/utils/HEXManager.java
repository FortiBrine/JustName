package me.fortibrine.justname.utils;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.COLOR_CHAR;

public final class HEXManager {

    public static String supportColorsHEX(String nameMessage) {

        String sversion;

        try {
            sversion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            return nameMessage;
        }

        if (Integer.parseInt(sversion.split("\\_")[1]) < 16) {
            return nameMessage;
        }

        final Pattern hexColorsPattern = Pattern.compile("&#([a-f0-9]{6})");
        final Matcher matcherPattern = hexColorsPattern.matcher(nameMessage);
        StringBuffer buffer = new StringBuffer(nameMessage.length() + 4 * 8);
        while (matcherPattern.find())
        {
            String group = matcherPattern.group(1);
            matcherPattern.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcherPattern.appendTail(buffer).toString();
    }

}
