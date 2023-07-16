package me.fortibrine.justname.utils;

import me.fortibrine.justname.JustName;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ChangeNameInventory implements InventoryHolder {
    private final Inventory inventory;
    private final JustName plugin;
    private final Player player;
    private final FileConfiguration config;

    public ChangeNameInventory(JustName plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.config = plugin.getConfig();
        this.inventory = Bukkit.createInventory(this, 54, config.getString("menu.title"));

        for (String playerName : VariableManager.keySetItemStacks()) {
            this.inventory.addItem(VariableManager.getItemStacks(playerName));
        }
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCursor() == null) return;

        String name = null;

        for (String playerName : VariableManager.keySetItemStacks()) {
            ItemStack itemStack = VariableManager.getItemStacks(playerName);

            if (event.getCursor() == itemStack) {
                name = playerName;
                break;
            }
        }

        if (name == null) return;

        String newName = VariableManager.getName(name);

        if (event.getClick() == ClickType.LEFT) {
            config.set("player." + name, newName);

            plugin.saveConfig();
            plugin.reloadConfig();

            VariableManager.removeName(name);
            VariableManager.removeItemStack(name);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission(plugin.getDescription().getPermissions().get(3))) continue;

                player.sendMessage(JSONManager.supportMessagesJSON(HEXManager.supportColorsHEX(config.getString("messages.accept")
                                .replace("%player", player.getName())
                                .replace("%name", newName))
                        .replace("&", "§")));
            }
        } else if (event.getClick() == ClickType.RIGHT) {
            VariableManager.removeName(name);
            VariableManager.removeItemStack(name);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission(plugin.getDescription().getPermissions().get(3))) continue;

                player.sendMessage(JSONManager.supportMessagesJSON(HEXManager.supportColorsHEX(config.getString("messages.decline")
                                .replace("%player", player.getName())
                                .replace("%name", newName))
                        .replace("&", "§")));
            }
        }

    }

}
