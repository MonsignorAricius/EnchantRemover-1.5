package me.aricius.enchantremover;

import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.seanomik.JustAnAPI.utils.ParticleUtil;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        this.getCommand("obchodnik").setExecutor(new CommandObchodnik());
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
    public void onPlayerClickOnItem2(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (player.hasPotionEffect(PotionEffectType.UNLUCK)) {
            if (e.getCurrentItem() != null && this.isClickedBow(e)) {
                this.giveForBow(e);
            } else if (e.getCurrentItem() != null && this.isClickedLH(e)) {
                this.giveForLH(e);
            } else if (e.getCurrentItem() != null && this.isClickedLC(e)) {
                this.giveForLC(e);
            } else if(e.getCurrentItem() != null && this.isClickedLL(e)) {
                this.giveForLL(e);
            } else if (e.getCurrentItem() != null && this.isClickedLB(e)) {
                this.giveForLB(e);
            } else if (e.getCurrentItem() != null && this.isClickedOS(e)){
                this.giveForOS(e);
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMoveItem(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(reference.IVNNAME) || e.getView().getTitle().equals(reference.INVNAME2)) {
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
        if (e.getView().getTitle().equals(reference.IVNNAME) || e.getView().getTitle().equals(reference.INVNAME2)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.UNLUCK, 60000, 1, false, false, false));
            player.setCanPickupItems(false);
        }
    }

    @EventHandler
    public void onPlayerCloseInvEventUnenchant(InventoryCloseEvent e) {
        if (e.getView().getTitle().equals(reference.IVNNAME) || e.getView().getTitle().equals(reference.INVNAME2)) {
            player.removePotionEffect(PotionEffectType.UNLUCK);
            player.setCanPickupItems(true);
        }
    }

    @EventHandler
    public void onSwapItemInHand(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(reference.IVNNAME) || e.getView().getTitle().equals(reference.INVNAME2)) {
            if (e.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
                e.setCancelled(true);
            }
        }
    }

    private boolean isClickedBook(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        return item.getType() == Material.ENCHANTED_BOOK;
    }
    private boolean isClickedBow(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        return item.getType() == Material.BOW;
    }
    private boolean isClickedLH(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        return item.getType() == Material.LEATHER_HELMET;
    }
    private boolean isClickedLC(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        return item.getType() == Material.LEATHER_CHESTPLATE;
    }
    private boolean isClickedLL(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        return item.getType() == Material.LEATHER_LEGGINGS;
    }
    private boolean isClickedLB(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        return item.getType() == Material.LEATHER_BOOTS;
    }
    private boolean isClickedOS(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        return item.getType() == Material.OAK_SAPLING;
    }

    private void removeEnchant(InventoryClickEvent e, int index) {
        ItemStack item = e.getCurrentItem();
        Enchantment enchantment = enchants[index];
        int enchantmentLvl = player.getInventory().getItemInMainHand().getEnchantmentLevel(enchantment);
        int newLvl = player.getLevel() - getCost(player.getInventory().getItemInMainHand().getEnchantmentLevel(enchantment));
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.closeInventory();
        } else if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.RED + "Kouzelník: " + ChatColor.LIGHT_PURPLE + "Nemáš dostatek místa.");
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

    private void giveForBow(InventoryClickEvent e) {
        ItemStack itemis = e.getCurrentItem();
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.RED + "Obchodník:" + ChatColor.LIGHT_PURPLE + "Nemáš dostatek místa. (Hotbar se nepočíta)");
            player.closeInventory();
        }
        if (itemis != null && itemis.getItemMeta().getDisplayName().equalsIgnoreCase(reference.nameBow)) {
            player.closeInventory();
        } else {
            player.getInventory().removeItem(itemis);
            player.getInventory().addItem(new ItemStack(Material.OAK_LOG, 2));
            player.updateInventory();
            Location loc = player.getLocation();
            player.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 10.0F, 1.0F);
        }
    }
    private void giveForLH(InventoryClickEvent e) {
        ItemStack itemis = e.getCurrentItem();
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.RED + "Obchodník:" + ChatColor.LIGHT_PURPLE + "Nemáš dostatek místa. (Hotbar se nepočíta)");
            player.closeInventory();
        }
        if (itemis != null && itemis.getItemMeta().getDisplayName().equalsIgnoreCase(reference.nameLH)) {
            player.closeInventory();
        } else {
            player.getInventory().removeItem(itemis);
            player.getInventory().addItem(new ItemStack(Material.LEATHER, 4));
            player.updateInventory();
            Location loc = player.getLocation();
            player.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 10.0F, 1.0F);
        }
    }
    private void giveForLC(InventoryClickEvent e) {
        ItemStack itemis = e.getCurrentItem();
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.RED + "Obchodník:" + ChatColor.LIGHT_PURPLE + "Nemáš dostatek místa. (Hotbar se nepočíta)");
            player.closeInventory();
        }
        if (itemis != null && itemis.getItemMeta().getDisplayName().equalsIgnoreCase(reference.nameLC)) {
            player.closeInventory();
        } else {
            player.getInventory().removeItem(itemis);
            player.getInventory().addItem(new ItemStack(Material.LEATHER, 4));
            player.updateInventory();
            Location loc = player.getLocation();
            player.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 10.0F, 1.0F);
        }
    }
    private void giveForLL(InventoryClickEvent e) {
        ItemStack itemis = e.getCurrentItem();
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.RED + "Obchodník:" + ChatColor.LIGHT_PURPLE + "Nemáš dostatek místa. (Hotbar se nepočíta)");
            player.closeInventory();
        }
        if (itemis != null && itemis.getItemMeta().getDisplayName().equalsIgnoreCase(reference.nameLL)) {
            player.closeInventory();
        } else {
            player.getInventory().removeItem(itemis);
            player.getInventory().addItem(new ItemStack(Material.LEATHER, 4));
            player.updateInventory();
            Location loc = player.getLocation();
            player.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 10.0F, 1.0F);
        }
    }
    private void giveForLB(InventoryClickEvent e) {
        ItemStack itemis = e.getCurrentItem();
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.RED + "Obchodník:" + ChatColor.LIGHT_PURPLE + "Nemáš dostatek místa. (Hotbar se nepočíta)");
            player.closeInventory();
        }
        if (itemis != null && itemis.getItemMeta().getDisplayName().equalsIgnoreCase(reference.nameLB)) {
            player.closeInventory();
        } else {
            player.getInventory().removeItem(itemis);
            player.getInventory().addItem(new ItemStack(Material.LEATHER, 4));
            player.updateInventory();
            Location loc = player.getLocation();
            player.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 10.0F, 1.0F);
        }
    }
    private void giveForOS(InventoryClickEvent e) {
        ItemStack itemis = e.getCurrentItem();
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.RED + "Obchodník:" + ChatColor.LIGHT_PURPLE + "Nemáš dostatek místa. (Hotbar se nepočíta)");
            player.closeInventory();
        }
        if (itemis != null && itemis.getItemMeta().getDisplayName().equalsIgnoreCase(reference.nameOak)) {
            player.closeInventory();
        } else if (itemis.getAmount() != 64) {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "Obchodník: " + ChatColor.LIGHT_PURPLE + "Stack nemá 64 saplingů.");
        } else {
            player.getInventory().removeItem(itemis);
            player.getInventory().addItem(new ItemStack(Material.EMERALD, 1));
            player.updateInventory();
            Location loc = player.getLocation();
            player.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 10.0F, 1.0F);
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
