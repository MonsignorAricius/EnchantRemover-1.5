package me.aricius.enchantremover;

import net.md_5.bungee.api.ChatColor;

public class reference {
    public static String CHATPREFIX;
    public static String IVNNAME;

    public reference() {
    }

    static {
        CHATPREFIX = ChatColor.RED + "[" + ChatColor.RED + "Kouzelník" + ChatColor.RED + "] ";
        IVNNAME = ChatColor.DARK_PURPLE + "Kouzelník";
    }
}
