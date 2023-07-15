package me.fortibrine.justname;

import me.fortibrine.justname.commands.CommandChangeName;
import me.fortibrine.justname.listeners.Listener;
import me.fortibrine.justname.utils.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class JustName extends JavaPlugin {

    private Map<Player, String> playerNames = new HashMap<>();
    private Map<Player, ItemStack> playerItems = new HashMap<>();
    private Map<Player, Inventory> playerInventories = new HashMap<>();

    @Override
    public void onEnable() {
        File config = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
        }

        Bukkit.getPluginManager().registerEvents(new Listener(), this);

        this.getCommand("changename").setExecutor(new CommandChangeName(this));

        EconomyManager.init();
    }

}
