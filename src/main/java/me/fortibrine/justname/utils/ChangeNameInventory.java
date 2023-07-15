package me.fortibrine.justname.utils;

import me.fortibrine.justname.JustName;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ChangeNameInventory implements InventoryHolder {
    private final Inventory inventory;
    private final JustName plugin;
    private final Player player;
    private final FileConfiguration config;

    public ChangeNameInventory(JustName plugin, Player player, String name) {
        this.plugin = plugin;
        this.player = player;
        this.config = plugin.getConfig();
        this.inventory = Bukkit.createInventory(this, 54, config.getString("menu.title"));
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void onInventoryClick(InventoryClickEvent event) {

    }

}
