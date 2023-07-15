package me.fortibrine.justname.utils;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {

    public static Economy economy;

    public static void init() {

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        economy = rsp.getProvider();
    }

    public static boolean takeMoney(Player player, double amount) {
        if (!economy.has(player, amount)) {
            return false;
        }

        return economy.withdrawPlayer(player, amount).transactionSuccess();
    }

}
