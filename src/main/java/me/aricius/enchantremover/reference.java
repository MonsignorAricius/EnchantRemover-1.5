package me.aricius.enchantremover;

import net.md_5.bungee.api.ChatColor;
import net.seanomik.JustAnAPI.JustAnAPI;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;

public class reference {
    public static String CHATPREFIX;
    public static String CHATPREFIX2;
    public static String IVNNAME;
    public static String INVNAME2;
    public static JustAnAPI api;
    public static String nameBow;
    public static String nameLH;
    public static String nameLC;
    public static String nameLL;
    public static String nameLB;
    public static String nameOak;

    public reference() {
    }

    public static String getEnchantName(String name) {
        if (name.equalsIgnoreCase("Damage_All")) {
            return "Sharpness";
        } else if (name.equalsIgnoreCase("Damage_Undead")) {
            return "Smite";
        } else if (name.equalsIgnoreCase("Fire_Aspect")) {
            return "Fire Aspect";
        } else if (name.equalsIgnoreCase("Loot_Bonus_Mobs")) {
            return "Looting";
        } else if (name.equalsIgnoreCase("DAMAGE_ARTHROPODS")) {
            return "Bane";
        } else if (name.equalsIgnoreCase("DURABILITY")) {
            return "Unbreaking";
        } else if (name.equalsIgnoreCase("DIG_Speed")) {
            return "Efficiency";
        } else if (name.equalsIgnoreCase("Loot_bonus_blocks")) {
            return "Fortune";
        } else if (name.equalsIgnoreCase("Arrow_Damage")) {
            return "Power";
        } else if (name.equalsIgnoreCase("Arrow_knockback")) {
            return "Punch";
        } else if (name.equalsIgnoreCase("ARROW_INFINITE")) {
            return "Infinity";
        } else if (name.equalsIgnoreCase("Arrow_Fire")) {
            return "Flame";
        } else if (name.equalsIgnoreCase("KNOCKBACK")) {
            return "Knockback";
        } else if (name.equalsIgnoreCase("DEPTH_STRIDER")) {
            return "DepthStrider";
        } else if (name.equalsIgnoreCase("PROTECTION_ENVIRONMENTAL")) {
            return "Protection";
        } else if (name.equalsIgnoreCase("PROTECTION_PROJECTILE")) {
            return "ProjectileProt";
        } else if (name.equalsIgnoreCase("PROTECTION_EXPLOSIONS")) {
            return "BlastProt";
        } else if (name.equalsIgnoreCase("PROTECTION_FIRE")) {
            return "FireProt";
        } else if (name.equalsIgnoreCase("PROTECTION_FALL")) {
            return "FallProt";
        } else if (name.equalsIgnoreCase("WATER_WORKER")) {
            return "Aqua";
        } else if (name.equalsIgnoreCase("OXYGEN")) {
            return "Respiration";
        } else if (name.equalsIgnoreCase("VANISHING_CURSE")) {
            return "Curse Of Vanishing";
        } else if (name.equalsIgnoreCase("BINDING_CURSE")) {
            return "Curse Of Binding";
        } else if (name.equalsIgnoreCase("CHANNELING")) {
            return "Channeling";
        } else if (name.equalsIgnoreCase("LOYALTY")) {
            return "Loyalty";
        } else if (name.equalsIgnoreCase("IMPALING")) {
            return "Impaling";
        } else if (name.equalsIgnoreCase("RIPTIDE")) {
            return "Riptide";
        } else if (name.equalsIgnoreCase("LURE")) {
            return "Lure";
        } else {
            return name.equalsIgnoreCase("LUCK") ? "Luck" : name;
        }
    }

    static {
        CHATPREFIX = ChatColor.RED + "[" + ChatColor.RED + "Kouzelník" + ChatColor.RED + "] ";
        CHATPREFIX2 = ChatColor.RED + "[" + ChatColor.RED + "Obchodník" + ChatColor.RED + "] ";
        IVNNAME = ChatColor.DARK_PURPLE + "Kouzelník";
        INVNAME2 = ChatColor.DARK_PURPLE + "Obchodník";
        api = (JustAnAPI)Bukkit.getServer().getPluginManager().getPlugin("JustAnAPI");
        nameBow = "Klikni na luk v inventáři, který chceš prodat";
        nameLH = "Klikni na leather helmu v inventáři, kterou chceš prodat";
        nameLC = "Klikni na leather chestplate v inventáři, který chceš prodat";
        nameLL = "Klikni na leather tepláky v inventáři, které chceš prodat";
        nameLB = "Klikni na leather boty v inventáři, které chceš prodat";
        nameOak = "Klikni na oak saplingy v inventáři, které chceš prodat";
    }
}
