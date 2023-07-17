package me.fortibrine.justname.listeners;

import me.fortibrine.justname.JustName;
import me.fortibrine.justname.utils.ChangeNameInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

public class Listener implements org.bukkit.event.Listener {

    private JustName plugin;
    public Listener(JustName plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();

        if (inventory == null) return;
        if (inventory.getHolder() == null) return;
        if (!(inventory.getHolder() instanceof ChangeNameInventory)) return;

        event.setCancelled(true);

        ((ChangeNameInventory) inventory.getHolder()).onInventoryClick(event);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String newName = plugin.getConfig().getString("player." + player.getName());

        if (newName != null) {
            player.setDisplayName(newName);
        }
    }

}
