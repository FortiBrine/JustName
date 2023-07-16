package me.fortibrine.justname;

import me.fortibrine.justname.commands.CommandChangeName;
import me.fortibrine.justname.listeners.Listener;
import me.fortibrine.justname.utils.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class JustName extends JavaPlugin {

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
