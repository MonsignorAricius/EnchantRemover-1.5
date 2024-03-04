package me.aricius.kouzelnik;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public Kouzelnik() {
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new KouzelnikEvent(), this);
        this.getServer().getPluginManager().registerEvents(new ObchodnikEvent(), this);
        this.getServer().getPluginManager().registerEvents(this, this);
        plugin = getPlugin(Kouzelnik.class);
        getCommand("unenchant").setExecutor(new KouzelnikCommand());
        getCommand("obchodnik").setExecutor(new ObchodnikCommand());
    }

    @EventHandler
    public void onPlayerClickOnItem(InventoryClickEvent e) {
        if (e.getRawSlot() == e.getSlot() && e.getView().getTitle().equals(reference.IVNNAME)) {
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.ENCHANTED_BOOK) {
                this.removeEnchant(e, e.getSlot() - 29);
            }
            e.setCancelled(true);
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
            if (e.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
                e.setCancelled(true);
            }
        }
    }

    private void removeEnchant(InventoryClickEvent e, int index) {
        Player player = (Player) e.getWhoClicked();
        World worl = player.getWorld();
        ItemStack item = e.getCurrentItem();
        if (item != null && item.getType() == Material.ENCHANTED_BOOK) {
            Enchantment enchantment = enchants[index];
            int newLvl = player.getLevel() - getCost(player.getInventory().getItemInMainHand().getEnchantmentLevel(enchantment));
            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                player.closeInventory();
            } else if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage("§8["+ChatColor.of("#FB608A")+"§lKouzelník§8]" + " §7Nemáš dostatek místa.");
                player.closeInventory();
                Location loca = player.getLocation();
                worl.playSound(loca, Sound.ENTITY_VILLAGER_NO, 10.0F, 1.0F);
            } else if (newLvl < 0) {
                player.sendMessage("§8["+ChatColor.of("#FB608A")+"§lKouzelník§8]" + " §7Nemáš dost na zaplacení. Potřebuješ ješte "+ChatColor.of("#FB608A")+newLvl * -1 + " §7XP.");
                player.closeInventory();
                Location loca = player.getLocation();
                worl.playSound(loca, Sound.ENTITY_VILLAGER_NO, 10.0F, 1.0F);
            } else {
                ItemMeta meta = item.getItemMeta();
                meta.setLore(null);
                item.setItemMeta(meta);
                player.getInventory().addItem(item);
                player.getInventory().getItemInMainHand().removeEnchantment(enchantment);
                if (player.getGameMode() != GameMode.CREATIVE) {
                    player.setLevel(newLvl);
                }
                player.closeInventory();
                Location loc = player.getLocation();
                ItemStackUtil.ColorAndSize(Color.PURPLE, 1.5F);
                ItemStackUtil.CreateBouncingSphere(plugin, player, Particle.REDSTONE, 1, 1.5F, 8, 0.0);
                player.playSound(loc, Sound.BLOCK_NOTE_BLOCK_CHIME, 10.0F, 1.0F);
            }
        }
    }

    public static int getCost(int lvl) {
        int newLvl = 0;

        for(int i = 0; i < lvl; ++i) {
            newLvl += 8;
        }

        return newLvl;
    }

    @EventHandler
    public void onObchodnik(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(reference.INVNAME2)) {
            if (e.getCurrentItem() != null) {
                Player p = (Player)e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                if (item != null) {
                    if (p.getInventory().firstEmpty() != -1) {
                        int slot = e.getRawSlot();
                        int[] bannedSlots = new int[]{19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};
                        for (int bslot : bannedSlots) {
                            if (slot == bslot) {
                                p.closeInventory();
                                return;
                            }
                        }
                        if (item.getType() == Material.BOW) {
                            finishTrade(p, item, Material.OAK_LOG, 1);
                        }
                        if (item.getType() == Material.LEATHER_HELMET) {
                            finishTrade(p, item, Material.LEATHER, 1);
                        }
                        if (item.getType() == Material.LEATHER_CHESTPLATE) {
                            finishTrade(p, item, Material.LEATHER, 1);
                        }
                        if (item.getType() == Material.LEATHER_LEGGINGS) {
                            finishTrade(p, item, Material.LEATHER, 1);
                        }
                        if (item.getType() == Material.LEATHER_BOOTS) {
                            finishTrade(p, item, Material.LEATHER, 1);
                        }
                        if (item.getType() == Material.OAK_SAPLING) {
                            if (item.getAmount() == 64) {
                                finishTrade(p, item, Material.EMERALD, 1);
                            } else {
                                p.closeInventory();
                                p.updateInventory();
                                p.sendMessage("§8["+ChatColor.of("#FB608A")+"§lObchodník§8]" + " §7Stack nemá 64 saplingů.");
                            }
                        }
                        if (item.getType() == Material.SPIDER_EYE) {
                            if (item.getAmount() >= 64) {
                                finishTrade(p, item, Material.EMERALD, 6);
                            } else {
                                p.closeInventory();
                                p.updateInventory();
                                p.sendMessage("§8["+ChatColor.of("#FB608A")+"§lObchodník§8]" + " §7Stack nemá 64 očí.");
                            }
                        }
                    } else {
                        p.sendMessage(reference.obchodnikInvFull);
                        p.closeInventory();
                        p.updateInventory();
                    }
                }
            }
        }
    }

    public void finishTrade(Player p, ItemStack price, Material reward, int amount) {
        p.getInventory().removeItem(price);
        p.getInventory().addItem(new ItemStack(reward, amount));
        Location loc = p.getLocation();
        p.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 10.0F, 1.0F);
    }
}
