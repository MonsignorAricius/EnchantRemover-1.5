package me.aricius.enchantremover;

import net.md_5.bungee.api.ChatColor;
import net.seanomik.JustAnAPI.utils.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class CommandObchodnik implements CommandExecutor {

    private Plugin plugin = Enchantremover.getPlugin(Enchantremover.class);

    public CommandObchodnik() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            Enchantremover.player = player;
            if (!player.hasPermission("obchodnik.use")) {
                sender.sendMessage(reference.CHATPREFIX + ChatColor.RED + "Your not allowed to do this!");
                return true;
            }
                Inventory inv2 = this.plugin.getServer().createInventory(player, 18, reference.INVNAME2);
                inv2.setItem(0, (new ItemStackUtil()).setItem(Material.BOW).setName(reference.nameBow).buildItem());
                inv2.setItem(1, (new ItemStackUtil()).setItem(Material.LEATHER_HELMET).setName(reference.nameLH).buildItem());
                inv2.setItem(2, (new ItemStackUtil()).setItem(Material.LEATHER_CHESTPLATE).setName(reference.nameLC).buildItem());
                inv2.setItem(3, (new ItemStackUtil()).setItem(Material.LEATHER_LEGGINGS).setName(reference.nameLL).buildItem());
                inv2.setItem(4, (new ItemStackUtil()).setItem(Material.LEATHER_BOOTS).setName(reference.nameLB).buildItem());
                inv2.setItem(5, (new ItemStackUtil()).setItem(Material.OAK_SAPLING).setAmount(64).setName(reference.nameOak).buildItem());
                inv2.setItem(9, (new ItemStackUtil()).setItem(Material.OAK_LOG).setAmount(2).setName("Odměna za luk").buildItem());
                inv2.setItem(10, (new ItemStackUtil()).setItem(Material.LEATHER).setAmount(4).setName("Odměna za leather helmu").buildItem());
                inv2.setItem(11, (new ItemStackUtil()).setItem(Material.LEATHER).setAmount(4).setName("Odměna za leather chestplate").buildItem());
                inv2.setItem(12, (new ItemStackUtil()).setItem(Material.LEATHER).setAmount(4).setName("Odměna za leather tepláky").buildItem());
                inv2.setItem(13, (new ItemStackUtil()).setItem(Material.LEATHER).setAmount(4).setName("Odměna za leather boty").buildItem());
                inv2.setItem(14, (new ItemStackUtil()).setItem(Material.EMERALD).setName("Odměna za oak saplingy").buildItem());
                player.openInventory(inv2);
        }
        return true;
    }
}
