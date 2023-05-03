package me.aricius.kouzelnik;

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

public class KouzelnikEvent implements Listener {
    private final Plugin plugin = Kouzelnik.getPlugin(Kouzelnik.class);
    
    @EventHandler
    public void onPlayerClickNPC(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        Entity npc = e.getRightClicked();
        if (e.getHand() == EquipmentSlot.HAND) {
            if (npc.hasMetadata("NPC") && npc.getName().equals(plugin.getConfig().getString("NPCname"))) {
                if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                    player.sendMessage("§8["+ChatColor.of("#FB608A")+"§lKouzelník§8]"+" §7Nemáš v ruce žádný začarovaný item.");
                } else if (player.getInventory().getItemInMainHand().getEnchantments().size() >= 1) {
                    if (player.getInventory().getItemInMainHand().getEnchantments().size() <= 10) {
                        ItemStack item = player.getInventory().getItemInMainHand();
                        Inventory inv = this.plugin.getServer().createInventory(player, 54, reference.IVNNAME);
                        inv.setItem(14, (new ItemStackUtil()).setItem(item.getType()).setName(item.getItemMeta().getDisplayName()).setEnchants(item.getEnchantments()).buildItem());
                        inv.setItem(12, (new ItemStackUtil()).setItem(Material.NAME_TAG).setName(ChatColor.of("#FB608A")+"Klikni na enchant, který chceš získat").buildItem());
                        int[] edgeSlotsPurple = new int[]{0, 2, 4, 6, 8, 18, 26, 36, 44, 46 ,48 ,50 ,52};
                        ItemStack isPurple = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
                        int[] edgeSlotsMagenta = new int[]{1, 3, 5, 7, 9, 17, 27, 35, 45, 47, 49, 51, 53};
                        ItemStack isMagenta = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
                        for (int i : edgeSlotsPurple) {
                            inv.setItem(i, isPurple);
                        }
                        for (int i : edgeSlotsMagenta) {
                            inv.setItem(i, isMagenta);
                        }
                        Kouzelnik.amountOfEnchants = 0;
                        for(int i = 0; i < item.getEnchantments().size(); ++i) {
                            int newI = i + 29;
                            if (newI == 34) {
                                newI = 38;
                            } else if (newI == 35) {
                                newI = 39;
                            } else if (newI == 36) {
                                newI = 40;
                            } else if (newI == 37) {
                                newI = 41;
                            } else if (newI == 38) {
                                newI = 42;
                            }
                            Object[] enchants = item.getEnchantments().keySet().toArray();
                            Enchantment[] newEnchants = Arrays.copyOf(enchants, enchants.length, Enchantment[].class);
                            int itemCost = Kouzelnik.getCost(item.getEnchantmentLevel(newEnchants[i]));
                            inv.setItem(newI, (new ItemStackUtil()).setItem(Material.ENCHANTED_BOOK).enchantedBook(newEnchants[i], item.getEnchantmentLevel(newEnchants[i])).addLore("Cena: " + itemCost + " krystalů").buildItem());
                            Kouzelnik.enchants = newEnchants;
                            ++Kouzelnik.amountOfEnchants;
                        }
                        player.openInventory(inv);
                    } else {
                        player.sendMessage("§8["+ChatColor.of("#FB608A")+"§lKouzelník§8]"+" §7Omlouvám se, ale AT mě nenaučil přijímat itemy s více než 10 enchantů. Příště se snad polepším.");
                    }
                } else {
                    player.sendMessage ("§8["+ChatColor.of("#FB608A")+"§lKouzelník§8]"+" §7Tahle věc není začarovaná.");
                }
            }
        }
    }
}
