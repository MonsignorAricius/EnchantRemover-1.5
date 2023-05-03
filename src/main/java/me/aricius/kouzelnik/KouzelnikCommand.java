package me.aricius.kouzelnik;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class KouzelnikCommand implements CommandExecutor {
    private final Plugin plugin = Kouzelnik.getPlugin(Kouzelnik.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!sender.hasPermission("§8["+ChatColor.of("#FB608A")+"§lKouzelník§8]"+ "§7kouzelnik.reload")) {
            sender.sendMessage("§8["+ChatColor.of("#FB608A")+"§lKouzelník§8]"+" §4K tomuto příkazu nemáš přístup.");
            return false;
        }
        if (strings.length > 1) {
            sender.sendMessage("§8["+ChatColor.of("#FB608A")+"§lKouzelník§8]"+" §cToo many arguments!");
            return false;
        }
        if (strings.length == 0) {
            sender.sendMessage("§8["+ChatColor.of("#FB608A")+"§lKouzelník§8]"+" §7Usage: /kouzelnik reload");
            return false;
        }
        if (strings[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage("§8["+ChatColor.of("#FB608A")+"§lKouzelník§8]"+" §7Config was succesfully reloaded!");
        }
        return false;
    }
}
