package me.dervinocap.taser.commands.tasercommands.subcommands;

import me.dervinocap.taser.utils.Items;
import me.dervinocap.taser.commands.SubCommand;
import me.dervinocap.taser.config.lang.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetCommand extends SubCommand {

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "taser.get";
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage(Lang.MESSAGE_UNKNOWN_COMMAND.getFormattedString());
            return;
        }

        switch (args[1].toLowerCase()) {

            case "taser": {
                player.getInventory().addItem(Items.taserItem());
                player.sendMessage(Lang.MESSAGE_ITEM_RECEIVED.getFormattedString());
                return;
            }

            case "reload": {
                player.getInventory().addItem(Items.reloadItem());
                player.sendMessage(Lang.MESSAGE_ITEM_RECEIVED.getFormattedString());
                return;
            }

            default: {
                player.sendMessage(Lang.MESSAGE_UNKNOWN_COMMAND.getFormattedString());
            }

        }
    }
}
