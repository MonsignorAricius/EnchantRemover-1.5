package me.aricius.enchantremover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemStackUtil {
    public ItemStack item;

    public ItemStackUtil() {
    }

    public ItemStackUtil setItem(Material material) {
        this.item = new ItemStack(material);
        return this;
    }

    public ItemStackUtil setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemStackUtil setName(String name) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStackUtil skull(OfflinePlayer player) {
        SkullMeta skull = (SkullMeta)this.item.getItemMeta();
        skull.setOwningPlayer(player);
        this.item.setItemMeta(skull);
        return this;
    }

    public ItemStackUtil Spawner(int entityID, Player player) {
        this.item = new ItemStack(Material.SPAWNER, 1);
        BlockStateMeta blockMeta = (BlockStateMeta)this.item.getItemMeta();
        BlockState blockState = blockMeta.getBlockState();
        CreatureSpawner spawner = (CreatureSpawner)blockState;
        spawner.setCreatureTypeByName(EntityType.fromId(entityID).getName());
        blockMeta.setBlockState(spawner);
        this.item.setItemMeta(blockMeta);
        return this;
    }

    public ItemStackUtil setEnchants(Map<Enchantment, Integer> enchantments) {
        Object[] enchants = enchantments.keySet().toArray();
        Enchantment[] newEnchants = Arrays.copyOf(enchants, enchants.length, Enchantment[].class);
        Enchantment[] var4 = newEnchants;
        int var5 = newEnchants.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Enchantment e = var4[var6];
            this.item.addUnsafeEnchantment(e, enchantments.get(e));
        }

        return this;
    }

    public ItemStackUtil enchantedBook(Enchantment enchantment, int enchantLvl) {
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta)this.item.getItemMeta();
        meta.addStoredEnchant(enchantment, enchantLvl, true);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStackUtil addLore(String lore) {
        ArrayList<String> loreList = new ArrayList<>();
        if (this.item.getItemMeta().hasLore()) {
            loreList.addAll(this.item.getItemMeta().getLore());
        }

        loreList.add(lore);
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(loreList);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStack buildItem() {
        return this.item;
    }
}
