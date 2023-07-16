package me.fortibrine.justname.listeners;

import me.fortibrine.justname.JustName;
import me.fortibrine.justname.utils.ChangeNameInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();

        if (inventory == null) return;
        if (inventory.getHolder() == null) return;
        if (!(inventory.getHolder() instanceof ChangeNameInventory)) return;

        event.setCancelled(true);

        ((ChangeNameInventory) inventory.getHolder()).onInventoryClick(event);
    }

}
