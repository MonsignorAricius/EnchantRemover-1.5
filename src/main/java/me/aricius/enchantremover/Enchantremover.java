package me.aricius.enchantremover;

import java.util.List;
import java.util.Objects;

import net.md_5.bungee.api.ChatColor;
import net.seanomik.JustAnAPI.utils.ParticleUtil;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import world.bentobox.bentobox.BentoBox;

public final class Enchantremover extends JavaPlugin implements Listener {
    public static int amountOfEnchants;
    public static Player player;
    public static Inventory unEnchantInv;
    public static Enchantment[] enchants;
    public static Plugin plugin;

    public Enchantremover() {
    }

    public void onEnable() {
        this.getCommand("unenchant").setExecutor(new CommandUnenchant());
        this.getServer().getPluginManager().registerEvents(this, this);
        plugin = getPlugin(Enchantremover.class);
    }

    @EventHandler
    public void onPlayerClickOnItem(InventoryClickEvent e) {
        if (e.getRawSlot() == e.getSlot() && e.getView().getTitle().equals(reference.IVNNAME)) {
            if (e.getCurrentItem() != null && this.isClickedBook(e)) {
                this.removeEnchant(e, e.getSlot() - 9);
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMoveItem(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(reference.IVNNAME)) {
            if (e.isRightClick() || e.isLeftClick() || e.isShiftClick()) {
                e.setCancelled(true);
            }
            if (e.getAction().equals(InventoryAction.DROP_ONE_SLOT)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerOpenInvEventUnenchant(InventoryOpenEvent e) {
        if (e.getView().getTitle().equals(reference.IVNNAME)) {
            player.setCanPickupItems(false);
        }
    }

    @EventHandler
    public void onPlayerCloseInvEventUnenchant(InventoryCloseEvent e) {
        if (e.getView().getTitle().equals(reference.IVNNAME)) {
            player.setCanPickupItems(true);
        }
    }

    private boolean isClickedBook(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        return item.getType() == Material.ENCHANTED_BOOK;
    }

    private void removeEnchant(InventoryClickEvent e, int index) {
        ItemStack item = e.getCurrentItem();
        Enchantment enchantment = enchants[index];
        int enchantmentLvl = player.getInventory().getItemInMainHand().getEnchantmentLevel(enchantment);
        int newLvl = player.getLevel() - getCost(player.getInventory().getItemInMainHand().getEnchantmentLevel(enchantment));
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.closeInventory();
        } else if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.RED + "Kouzelník:" + ChatColor.LIGHT_PURPLE + " Nemáš dostatek místa.");
        } else if (newLvl < 0 && player.getGameMode() != GameMode.CREATIVE) {
            if (newLvl * -1 == 1) {
                player.sendMessage(ChatColor.RED + "Kouzelník: " + ChatColor.LIGHT_PURPLE + "Nemáš dost na zaplacení. Potřebuješ ješte " + newLvl * -1 + " XP.");
            } else {
                player.sendMessage(ChatColor.RED + "Kouzelník: " + ChatColor.LIGHT_PURPLE + "Nemáš dost na zaplacení. Potřebuješ ješte " + newLvl * -1 + " XP.");
            }
        } else {
            ItemMeta meta = item.getItemMeta();
            meta.setLore((List)null);
            item.setItemMeta(meta);
            player.getInventory().addItem(new ItemStack[]{item});
            player.getInventory().getItemInMainHand().removeEnchantment(enchantment);
            if (player.getGameMode() != GameMode.CREATIVE) {
                player.setLevel(newLvl);
            }

            player.closeInventory();
            Location loc = player.getLocation();
            ParticleUtil.ColorAndSize(Color.PURPLE, 1.5F);
            ParticleUtil.CreateBouncingSphere(plugin, player, Particle.REDSTONE, 1, 1.5F, 8, 0.0D);
            player.playSound(loc, Sound.BLOCK_NOTE_BLOCK_CHIME, 10.0F, 1.0F);
        }

    }

    public static int getCost(int lvl) {
        int newLvl = 0;

        for(int i = 0; i < lvl; ++i) {
            newLvl += 3;
        }

        return newLvl;
    }

    private static String convertIntToRoman(int number) {
        String newNumber = "";
        if (number == 1) {
            newNumber = "I ";
        } else if (number == 2) {
            newNumber = "II ";
        } else if (number == 3) {
            newNumber = "III ";
        } else if (number == 4) {
            newNumber = "IV ";
        } else if (number == 5) {
            newNumber = "V ";
        }

        return newNumber;
    }
}
