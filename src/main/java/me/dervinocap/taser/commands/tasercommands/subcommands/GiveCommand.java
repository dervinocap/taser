package me.dervinocap.taser.commands.tasercommands.subcommands;

import me.dervinocap.taser.utils.Items;
import me.dervinocap.taser.commands.SubCommand;
import me.dervinocap.taser.config.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommand extends SubCommand {

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "taser.give";
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length < 3) {
            player.sendMessage(Lang.MESSAGE_UNKNOWN_COMMAND.getFormattedString());
            return;
        }

        Player target = Bukkit.getPlayerExact(args[1]);

        if (target == null) {
            player.sendMessage(Lang.MESSAGE_PLAYER_OFFLINE.getFormattedString());
            return;
        }

        switch (args[2]) {

            case "taser": {
                target.getInventory().addItem(Items.taserItem());
                target.sendMessage(Lang.MESSAGE_ITEM_RECEIVED.getFormattedString());
                player.sendMessage(Lang.MESSAGE_ITEM_GIVEN.getFormattedString().replace("%player%", target.getName()));
                return;
            }

            case "reload": {
                target.getInventory().addItem(Items.reloadItem());
                target.sendMessage(Lang.MESSAGE_ITEM_RECEIVED.getFormattedString());
                player.sendMessage(Lang.MESSAGE_ITEM_GIVEN.getFormattedString().replace("%player%", target.getName()));
                return;
            }

            default: {
                player.sendMessage(Lang.MESSAGE_UNKNOWN_COMMAND.getFormattedString());
            }

        }
    }
}
