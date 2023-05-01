package me.aricius.enchantremover;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class EnchantCommand implements CommandExecutor {
    private final Plugin plugin = Enchantremover.getPlugin(Enchantremover.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!sender.hasPermission("kouzelnik.reload")) {
            sender.sendMessage("§4K tomuto příkazu nemáš přístup.");
            return false;
        }
        if (strings.length > 1) {
            sender.sendMessage("§cToo many arguments!");
            return false;
        }
        if (strings.length == 0) {
            sender.sendMessage("§7Usage: /kouzelnik reload");
            return false;
        }
        if (strings[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage("§bConfig was succesfully reloaded!");
        }
        return false;
    }
}
