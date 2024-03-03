package me.aricius.kouzelnik;

import net.md_5.bungee.api.ChatColor;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

public class reference {
    public static String CHATPREFIX;
    public static String CHATPREFIX2;
    public static String IVNNAME;
    public static String INVNAME2;
    public static String nameBow;
    public static String nameLH;
    public static String nameLC;
    public static String nameLL;
    public static String nameLB;
    public static String nameOak;
    public static String nameEye;
    public static String obchodnikInvFull;

    public reference() {
    }

    static {
        CHATPREFIX = ChatColor.RED+"Kouzelník: ";
        CHATPREFIX2 = ChatColor.RED+"Obchodník: ";
        IVNNAME = ChatColor.DARK_PURPLE + "Kouzelník";
        INVNAME2 = ChatColor.DARK_PURPLE + "Obchodník";
        nameBow = "Klikni na luk v inventáři";
        nameLH = "Klikni na leather helmu v inventáři";
        nameLC = "Klikni na leather chestplate v inventáři";
        nameLL = "Klikni na leather tepláky v inventáři";
        nameLB = "Klikni na leather boty v inventáři";
        nameOak = "Klikni na oak saplingy v inventáři";
        nameEye = "Klikni na spider oči v inventáři";
        obchodnikInvFull = CHATPREFIX2+ChatColor.LIGHT_PURPLE+"Nemáš dostatek místa.";
    }
}

