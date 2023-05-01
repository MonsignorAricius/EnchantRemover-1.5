package me.aricius.enchantremover;

import net.md_5.bungee.api.ChatColor;

public class reference {
    public static String CHATPREFIX;
    public static String IVNNAME;

    public reference() {
    }

    static {
        CHATPREFIX = ChatColor.RED + "§8[" + ChatColor.of("#FB608A")+"§lKouzelník" + "§8] ";
        IVNNAME = ChatColor.of("#FB608A")+"§lKouzelník";
    }
}
