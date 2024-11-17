package me.dervinocap.taser.commands;

import me.dervinocap.taser.config.lang.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDExecutionValidator {

    public static boolean validate(SubCommand command, CommandSender sender) {

        if (command.isPlayerOnly() && !(sender instanceof Player)) {
            sender.sendMessage("§cOnly player can write this command!");
            return false;
        }

        if (command.getPermission() != null && !sender.hasPermission(command.getPermission()) && !(sender.hasPermission("*") || !sender.isOp() || sender.hasPermission("taser.*"))) {
            sender.sendMessage(Lang.MESSAGE_NO_PERMS.getFormattedString());
            return false;
        }

        return true;
    }

}
