package me.aricius.kouzelnik;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KouzelnikCommand implements CommandExecutor {
    public KouzelnikEvent ke = new KouzelnikEvent();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("kouzelnik.use")) {
                ke.openKouzelnik(p);
            } else {
                p.sendMessage("§4K tomuto příkazu nemáš přístup.");
            }
        }
        return true;
    }
}
