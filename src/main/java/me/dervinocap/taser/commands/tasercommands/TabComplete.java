package me.dervinocap.taser.commands.tasercommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabComplete implements TabCompleter {

    public static List<String> subCommandsString = new ArrayList<>();
    public static List<String> names = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) {
            subCommandsString.addAll(TaserMainCommand.subCommands.keySet());
            return StringUtil.copyPartialMatches(args[0], subCommandsString, new ArrayList<>());
        } else if (args.length == 2 && args[0].equalsIgnoreCase("get")) {
            return StringUtil.copyPartialMatches(args[1], Arrays.asList("taser", "reload"), new ArrayList<>());
        } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            return StringUtil.copyPartialMatches(args[2], Arrays.asList("taser", "reload"), new ArrayList<>());
        } else {
            for (Player player1 : Bukkit.getOnlinePlayers()) names.add(player1.getName());
            return names;
        }
    }
}
