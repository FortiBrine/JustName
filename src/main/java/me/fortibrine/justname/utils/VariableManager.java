package me.fortibrine.justname.utils;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class VariableManager {

    private static Map<String, String> names = new HashMap<>();
    private static Map<String, ItemStack> itemStacks = new HashMap<>();

    public static void putName(String playerName, String name) {
        names.put(playerName, name);
    }

    public static String getName(String playerName) {
        return names.get(playerName);
    }

    public static void removeName(String playerName) {
        names.remove(playerName);
    }

    public static void putItemStack(String playerName, ItemStack item) {
        itemStacks.put(playerName, item);
    }

    public static ItemStack getItemStack(String playerName) {
        return itemStacks.get(playerName);
    }

    public static void removeItemStack(String playerName) {
        itemStacks.remove(playerName);
    }
}
