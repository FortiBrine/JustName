package me.fortibrine.justname.commands;

import me.fortibrine.justname.JustName;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandChangeName implements CommandExecutor {

    private JustName plugin;
    public CommandChangeName(JustName plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
