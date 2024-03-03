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

public class ObchodnikEvent implements Listener {
    private final Plugin plugin = Kouzelnik.getPlugin(Kouzelnik.class);

    @EventHandler
    public void onPlayerClickNPC(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        Entity npc = e.getRightClicked();
        if (e.getHand() == EquipmentSlot.HAND) {
            if (npc.hasMetadata("NPC") && npc.getName().equals("Obchodník")) {
                Inventory inv2 = this.plugin.getServer().createInventory(player, 54, reference.INVNAME2);
                int[] edgeSlotsPurple = new int[]{0, 2, 4, 6, 8, 18, 26, 36, 44, 46 ,48 ,50 ,52};
                ItemStack isPurple = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
                int[] edgeSlotsMagenta = new int[]{1, 3, 5, 7, 9, 17, 27, 35, 45, 47, 49, 51, 53};
                ItemStack isMagenta = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
                for (int i : edgeSlotsPurple) {
                    inv2.setItem(i, isPurple);
                }
                for (int i : edgeSlotsMagenta) {
                    inv2.setItem(i, isMagenta);
                }
                inv2.setItem(19, (new ItemStackUtil()).setItem(Material.BOW).setName(reference.nameBow).buildItem());
                inv2.setItem(20, (new ItemStackUtil()).setItem(Material.LEATHER_HELMET).setName(reference.nameLH).buildItem());
                inv2.setItem(21, (new ItemStackUtil()).setItem(Material.LEATHER_CHESTPLATE).setName(reference.nameLC).buildItem());
                inv2.setItem(22, (new ItemStackUtil()).setItem(Material.LEATHER_LEGGINGS).setName(reference.nameLL).buildItem());
                inv2.setItem(23, (new ItemStackUtil()).setItem(Material.LEATHER_BOOTS).setName(reference.nameLB).buildItem());
                inv2.setItem(24, (new ItemStackUtil()).setItem(Material.OAK_SAPLING).setAmount(64).setName(reference.nameOak).buildItem());
                inv2.setItem(25, (new ItemStackUtil()).setItem(Material.SPIDER_EYE).setAmount(64).setName(reference.nameEye).buildItem());
                inv2.setItem(28, (new ItemStackUtil()).setItem(Material.OAK_LOG).setName("Odměna za luk").buildItem());
                inv2.setItem(29, (new ItemStackUtil()).setItem(Material.LEATHER).setName("Odměna za leather helmu").buildItem());
                inv2.setItem(30, (new ItemStackUtil()).setItem(Material.LEATHER).setName("Odměna za leather chestplate").buildItem());
                inv2.setItem(31, (new ItemStackUtil()).setItem(Material.LEATHER).setName("Odměna za leather tepláky").buildItem());
                inv2.setItem(32, (new ItemStackUtil()).setItem(Material.LEATHER).setName("Odměna za leather boty").buildItem());
                inv2.setItem(33, (new ItemStackUtil()).setItem(Material.EMERALD).setName("Odměna za oak saplingy").buildItem());
                inv2.setItem(34, (new ItemStackUtil()).setItem(Material.EMERALD).setAmount(6).setName("Odměna za spider oči").buildItem());
                player.openInventory(inv2);
            }
        }
    }
}
