package me.fortibrine.justname.listeners;

import me.fortibrine.justname.JustName;
import me.fortibrine.justname.utils.ChangeNameInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class Listener implements org.bukkit.event.Listener {

//    private JustName plugin;
//    public Listener(JustName plugin) {
//        this.plugin = plugin;
//    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();

        if (!(inventory.getHolder() instanceof ChangeNameInventory)) return;

        ((ChangeNameInventory) inventory.getHolder()).onInventoryClick(event);
    }

}
