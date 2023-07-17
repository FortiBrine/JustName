package me.fortibrine.justname.commands;

import me.fortibrine.justname.JustName;
import me.fortibrine.justname.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CommandChangeName implements CommandExecutor {

    private JustName plugin;
    public CommandChangeName(JustName plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = plugin.getConfig();

        if (!(sender instanceof Player)) return false;

        if (args.length < 1) {
            return false;
        }

        Player player = (Player) sender;

        if (args[0].equals("list")) {

            if (!player.hasPermission(plugin.getDescription().getPermissions().get(2))) {
                player.sendMessage(
                        JSONManager.supportMessagesJSON(
                                HEXManager.supportColorsHEX(
                                        config.getString("messages.permission"))).replace("&", "\u00a7"));
                return true;
            }

            ChangeNameInventory inventory = new ChangeNameInventory(plugin, player);

            player.openInventory(inventory.getInventory());

            return true;
        }

        if (args.length < 2) {
            return false;
        }

        if (!player.hasPermission(plugin.getDescription().getPermissions().get(1))) {
            player.sendMessage(
                    JSONManager.supportMessagesJSON(
                            HEXManager.supportColorsHEX(
                                    config.getString("messages.permission")).replace("&", "§")));
            return true;
        }

        String name = args[0];
        String surname = args[1];

        for (char c : name.toCharArray()) {
            if (!((int) 'а' <= c && c <= (int) 'я')) {
                player.sendMessage(
                        JSONManager.supportMessagesJSON(
                                HEXManager.supportColorsHEX(
                                        config.getString("messages.symbols")).replace("&", "§")));

                return true;
            }
        }

        for (char c : surname.toCharArray()) {
            if (!((int) 'а' <= c && c <= (int) 'я')) {
                player.sendMessage(
                        JSONManager.supportMessagesJSON(
                                HEXManager.supportColorsHEX(
                                        config.getString("messages.symbols"))).replace("&", "§"));

                return true;
            }
        }

        name = name.toLowerCase();
        surname = surname.toLowerCase();

        StringBuilder nameBuilder = new StringBuilder(name);
        StringBuilder surnameBuilder = new StringBuilder(surname);

        nameBuilder.setCharAt(0, Character.toUpperCase(name.charAt(0)));
        surnameBuilder.setCharAt(0, Character.toUpperCase(surname.charAt(0)));

        if (name.length() < config.getInt("settings.min-name")) {
            player.sendMessage(
                    JSONManager.supportMessagesJSON(
                            HEXManager.supportColorsHEX(
                                    config.getString("messages.name-too-small"))).replace("&", "§"));

            return true;
        }

        if (surname.length() < config.getInt("settings.min-surname")) {
            player.sendMessage(
                    JSONManager.supportMessagesJSON(
                            HEXManager.supportColorsHEX(
                                    config.getString("messages.surname-too-small"))).replace("&", "§"));

            return true;
        }

        String newName = String.valueOf(nameBuilder.append(' ').append(surnameBuilder));

        ItemStack item = new ItemStack(Material.matchMaterial(config.getString("menu.item.material")));

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(JSONManager.supportMessagesJSON(HEXManager.supportColorsHEX(config.getString("menu.item.name")
                .replace("%player", player.getName())
                .replace("%name", newName))
                .replace("&", "§")));

        List<String> lore = config.getStringList("menu.item.lore");

        lore.replaceAll(s -> JSONManager.supportMessagesJSON(HEXManager.supportColorsHEX(s
                        .replace("%player", player.getName())
                        .replace("%name", newName))
                .replace("&", "§")));

        meta.setLore(lore);

        item.setItemMeta(meta);

        double amount = config.getDouble("settings.money");

        if (config.getString("player." + player.getName()) == null) {

            if (!config.getBoolean("settings.first-nickname-free")) {
                if (!EconomyManager.takeMoney(player, amount)) {
                    player.sendMessage(
                            JSONManager.supportMessagesJSON(
                                    HEXManager.supportColorsHEX(
                                            config.getString("messages.not-enough"))).replace("&", "§"));

                    return true;
                }
            }

            if (!config.getBoolean("settings.first-nickname-need-admin")) {
                config.set("player." + player.getName(), newName);

                plugin.saveConfig();
                plugin.reloadConfig();

                player.setDisplayName(newName);

                player.sendMessage(JSONManager.supportMessagesJSON(HEXManager.supportColorsHEX(config.getString("messages.send")
                                .replace("%player", player.getName())
                                .replace("%name", newName))
                        .replace("&", "§")));
                player.sendMessage(JSONManager.supportMessagesJSON(HEXManager.supportColorsHEX(config.getString("messages.accept")
                                .replace("%player", player.getName())
                                .replace("%name", newName))
                        .replace("&", "§")));

                return true;
            }

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission(plugin.getDescription().getPermissions().get(3))) continue;

                player.sendMessage(JSONManager.supportMessagesJSON(HEXManager.supportColorsHEX(config.getString("messages.request")
                                .replace("%player", player.getName())
                                .replace("%name", newName))
                        .replace("&", "§")));
            }

            player.sendMessage(JSONManager.supportMessagesJSON(HEXManager.supportColorsHEX(config.getString("messages.send")
                            .replace("%player", player.getName())
                            .replace("%name", newName))
                    .replace("&", "§")));

            VariableManager.putName(player.getName(), newName);
            VariableManager.putItemStack(player.getName(), item);

            return true;
        }

        if (!EconomyManager.takeMoney(player, amount)) {
            player.sendMessage(
                    JSONManager.supportMessagesJSON(
                            HEXManager.supportColorsHEX(
                                    config.getString("messages.not-enough"))).replace("&", "§"));

            return true;
        }

        if (!config.getBoolean("settings.need-admin")) {
            config.set("player." + player.getName(), newName);

            plugin.saveConfig();
            plugin.reloadConfig();

            player.setDisplayName(newName);

            player.sendMessage(JSONManager.supportMessagesJSON(HEXManager.supportColorsHEX(config.getString("messages.send")
                            .replace("%player", player.getName())
                            .replace("%name", newName))
                    .replace("&", "§")));
            player.sendMessage(JSONManager.supportMessagesJSON(HEXManager.supportColorsHEX(config.getString("messages.accept")
                            .replace("%player", player.getName())
                            .replace("%name", newName))
                    .replace("&", "§")));

            return true;
        }

        player.sendMessage(JSONManager.supportMessagesJSON(HEXManager.supportColorsHEX(config.getString("messages.send")
                        .replace("%player", player.getName())
                        .replace("%name", newName))
                .replace("&", "§")));

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission(plugin.getDescription().getPermissions().get(3))) continue;

            player.sendMessage(JSONManager.supportMessagesJSON(HEXManager.supportColorsHEX(config.getString("messages.request")
                            .replace("%player", player.getName())
                            .replace("%name", newName))
                    .replace("&", "§")));
        }

        VariableManager.putName(player.getName(), newName);
        VariableManager.putItemStack(player.getName(), item);

        return true;
    }
}
