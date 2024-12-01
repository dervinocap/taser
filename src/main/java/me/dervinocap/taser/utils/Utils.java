package me.dervinocap.taser.utils;

import de.tr7zw.changeme.nbtapi.NBTItem;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static String hex(String message) {
        Pattern pattern = Pattern.compile("(#[a-fA-F0-9]{6})");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message).replace('&', '§');
    }

    public static void printError() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "――――――――――――――――――――――――――――――――――――――――――――――――――――――");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "   taser" + ChatColor.GRAY + " has detected a configuration " + ChatColor.RED + "Error!");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "――――――――――――――――――――――――――――――――――――――――――――――――――――――");
    }

    public static boolean isTaser(ItemStack item) {

        if (item.getAmount() == 0 || item.getType().equals(Material.AIR) || item == null) return false;

        NBTItem nbti = new NBTItem(item);
        if (nbti.getKeys().contains("taser.ammo")) {
            return true;
        }
        return false;
    }

    public static boolean isActionbar(String string) {

        if (string.startsWith("<action-bar>")) {
            return true;
        }
        return false;
    }

    public static void sendMessage(Player player , String string) {

        if (isActionbar(string)) {
            sendActionBar(player, string.replace("<action-bar>", ""));
        } else {
            player.sendMessage(string);
        }

    }

}
