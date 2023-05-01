package me.aricius.enchantremover;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class UnenchantEvent implements Listener {
    private final Plugin plugin = Enchantremover.getPlugin(Enchantremover.class);
    
    @EventHandler
    public void onPlayerClickNPC(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        Entity npc = e.getRightClicked();
        if (e.getHand() == EquipmentSlot.HAND) {
            if (npc.hasMetadata("NPC") && npc.getName().equals(plugin.getConfig().getString("NPCname"))) {
                if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                    player.sendMessage(ChatColor.RED + "Kouzelník:" + ChatColor.LIGHT_PURPLE + " Táhle věc není začarovaná.");
                } else if (player.getInventory().getItemInMainHand().getEnchantments().size() >= 1) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    Inventory inv = this.plugin.getServer().createInventory(player, 54, reference.IVNNAME);
                    inv.setItem(13, (new ItemStackUtil()).setItem(item.getType()).setName(item.getItemMeta().getDisplayName()).setEnchants(item.getEnchantments()).buildItem());
                    inv.setItem(12, (new ItemStackUtil()).setItem(Material.NAME_TAG).setName("Klikni na knižku, kterou chceš získat").buildItem());
                    Enchantremover.amountOfEnchants = 0;
                    for(int i = 0; i < item.getEnchantments().size(); ++i) {
                        int newI = i + 18;
                        Object[] enchants = item.getEnchantments().keySet().toArray();
                        Enchantment[] newEnchants = Arrays.copyOf(enchants, enchants.length, Enchantment[].class);
                        int itemCost = Enchantremover.getCost(item.getEnchantmentLevel(newEnchants[i]));
                        inv.setItem(newI, (new ItemStackUtil()).setItem(Material.ENCHANTED_BOOK).enchantedBook(newEnchants[i], item.getEnchantmentLevel(newEnchants[i])).addLore("Cena: " + itemCost + " krystalů").buildItem());
                        Enchantremover.enchants = newEnchants;
                        ++Enchantremover.amountOfEnchants;
                    }
                    player.openInventory(inv);
                } else {
                    player.sendMessage(ChatColor.RED + "Kouzelník:" + ChatColor.LIGHT_PURPLE + " Táhle věc není začarovaná.");
                }
            }
        }
    }
}
