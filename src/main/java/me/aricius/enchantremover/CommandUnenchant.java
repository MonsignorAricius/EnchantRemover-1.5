package me.aricius.enchantremover;

import java.util.Arrays;
import net.md_5.bungee.api.ChatColor;
import net.seanomik.JustAnAPI.utils.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class CommandUnenchant implements CommandExecutor {
    private Plugin plugin = Enchantremover.getPlugin(Enchantremover.class);

    public CommandUnenchant() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            Enchantremover.player = player;
            if (!player.hasPermission("unenchanter.use")) {
                sender.sendMessage(reference.CHATPREFIX + ChatColor.RED + "Your not allowed to do this!");
                return true;
            }

            if (player.getInventory().getItemInMainHand().getType() == Material.AIR && player.getInventory().getItemInMainHand().getType() != Material.TRIDENT && player.getInventory().getItemInMainHand().getType() != Material.LEATHER_BOOTS) {
                if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                    player.sendMessage(ChatColor.RED + "Kouzelník:" + ChatColor.LIGHT_PURPLE + " Táhle věc není začarovaná.");
                }
            } else if (player.getInventory().getItemInMainHand().getEnchantments().size() >= 1) {
                ItemStack item = player.getInventory().getItemInMainHand();
                Inventory inv = this.plugin.getServer().createInventory(player, 27, reference.IVNNAME);
                inv.setItem(0, (new ItemStackUtil()).setItem(item.getType()).setName(item.getItemMeta().getDisplayName()).setEnchants(item.getEnchantments()).buildItem());
                inv.setItem(1, (new ItemStackUtil()).setItem(Material.NAME_TAG).setName("Klikni na knižku, kterou chceš získat").buildItem());
                Enchantremover.amountOfEnchants = 0;

                for(int i = 0; i < item.getEnchantments().size(); ++i) {
                    int newI = i + 9;
                    Object[] enchants = item.getEnchantments().keySet().toArray();
                    Enchantment[] newEnchants = (Enchantment[])Arrays.copyOf(enchants, enchants.length, Enchantment[].class);
                    int itemCost = Enchantremover.getCost(item.getEnchantmentLevel(newEnchants[i]));
                    inv.setItem(newI, (new ItemStackUtil()).setItem(Material.ENCHANTED_BOOK).enchantedBook(newEnchants[i], item.getEnchantmentLevel(newEnchants[i])).addLore("Cena: " + itemCost + " XP").buildItem());
                    Enchantremover.enchants = newEnchants;
                    ++Enchantremover.amountOfEnchants;
                }

                player.openInventory(inv);
            } else {
                player.sendMessage(ChatColor.RED + "Kouzelník:" + ChatColor.LIGHT_PURPLE + " Táhle věc není začarovaná.");
            }
        } else {
            sender.sendMessage(reference.CHATPREFIX + ChatColor.RED + "Only players can use this command!");
        }

        return true;
    }
}
