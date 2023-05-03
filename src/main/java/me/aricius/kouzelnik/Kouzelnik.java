package me.aricius.kouzelnik;

import java.util.List;

import me.aricius.endlesscrystal.EndlessCrystal;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class Kouzelnik extends JavaPlugin implements Listener {
    public static int amountOfEnchants;
    public static Enchantment[] enchants;
    public static Plugin plugin;
    public static EndlessCrystal eapi;

    public Kouzelnik() {
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new KouzelnikEvent(), this);
        this.getServer().getPluginManager().registerEvents(this, this);
        getCommand("kouzelnik").setExecutor(new KouzelnikCommand());
        plugin = getPlugin(Kouzelnik.class);
        this.saveDefaultConfig();
        if (getServer().getPluginManager().getPlugin("EndlessCrystal") == null) {
            getLogger().severe("EndlessCrystal plugin must be installed for this plugin to work! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            eapi = getPlugin(EndlessCrystal.class);
        }
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }

    @EventHandler
    public void onPlayerClickOnItem(InventoryClickEvent e) {
        if (e.getRawSlot() == e.getSlot() && e.getView().getTitle().equals(reference.IVNNAME)) {
            if (e.getCurrentItem() != null && this.isClickedBook(e)) {
                this.removeEnchant(e, e.getSlot() - 29);
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
            Player player = (Player) e.getPlayer();
            player.addPotionEffect(new PotionEffect(PotionEffectType.UNLUCK, 60000, 1, false, false, false));
            player.setCanPickupItems(false);
        }
    }

    @EventHandler
    public void onPlayerCloseInvEventUnenchant(InventoryCloseEvent e) {
        if (e.getView().getTitle().equals(reference.IVNNAME)) {
            Player player = (Player) e.getPlayer();
            player.removePotionEffect(PotionEffectType.UNLUCK);
            player.setCanPickupItems(true);
        }
    }

    @EventHandler
    public void onSwapItemInHand(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(reference.IVNNAME)) {
            if (e.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
                e.setCancelled(true);
            }
        }
    }

    private boolean isClickedBook(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        return item.getType() == Material.ENCHANTED_BOOK;
    }

    private void removeEnchant(InventoryClickEvent e, int index) {
        Player player = (Player) e.getWhoClicked();
        World worl = player.getWorld();
        ItemStack item = e.getCurrentItem();
        Enchantment enchantment = enchants[index];
        int newLvl = eapi.getAPI().look(player.getUniqueId()) - getCost(player.getInventory().getItemInMainHand().getEnchantmentLevel(enchantment));
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.closeInventory();
        } else if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage("§8["+ChatColor.of("#FB608A")+"§lKouzelník§8]" + " §7Nemáš dostatek místa.");
            player.closeInventory();
            Location loca = player.getLocation();
            worl.playSound(loca, Sound.ENTITY_VILLAGER_NO, 10.0F, 1.0F);
        } else if (newLvl < 0) {
            if (newLvl * -1 == 1) {
                player.sendMessage("§8["+ChatColor.of("#FB608A")+"§lKouzelník§8]" + " §7Nemáš dost na zaplacení. Potřebuješ ješte "+ChatColor.of("#FB608A")+newLvl * -1 + " §7krystalů.");
                player.closeInventory();
                Location loca = player.getLocation();
                worl.playSound(loca, Sound.ENTITY_VILLAGER_NO, 10.0F, 1.0F);
            } else {
                player.sendMessage("§8["+ChatColor.of("#FB608A")+"§lKouzelník§8]" + " §7Nemáš dost na zaplacení. Potřebuješ ješte "+ChatColor.of("#FB608A")+newLvl * -1 + " §7krystalů.");
                player.closeInventory();
                Location loca = player.getLocation();
                worl.playSound(loca, Sound.ENTITY_VILLAGER_NO, 10.0F, 1.0F);
            }
        } else {
            ItemMeta meta = item.getItemMeta();
            meta.setLore((List)null);
            item.setItemMeta(meta);
            player.getInventory().addItem(new ItemStack[]{item});
            player.getInventory().getItemInMainHand().removeEnchantment(enchantment);
            eapi.getAPI().set(player.getUniqueId(), newLvl);
            player.closeInventory();
            Location loc = player.getLocation();
            player.playSound(loc, Sound.BLOCK_NOTE_BLOCK_CHIME, 10.0F, 1.0F);
            Location loc2 = player.getLocation().add(0.0, 2.0, 0.0);
            worl.spawnParticle(Particle.TOTEM, loc2, 30);
        }

    }

    public static int getCost(int lvl) {
        int newLvl = 0;

        for(int i = 0; i < lvl; ++i) {
            newLvl += plugin.getConfig().getInt("pricePerEnchLevel");
        }

        return newLvl;
    }
}
