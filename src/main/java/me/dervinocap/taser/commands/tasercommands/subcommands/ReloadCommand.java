package me.dervinocap.taser.commands.tasercommands.subcommands;

import me.dervinocap.taser.commands.SubCommand;
import me.dervinocap.taser.config.lang.Lang;
import me.dervinocap.taser.config.lang.LangFile;
import me.dervinocap.taser.loader.CustomMain;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "taser.reload";
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(Lang.MESSAGE_UNKNOWN_COMMAND.getFormattedString());
            return;
        }

        long start = System.currentTimeMillis();

        player.sendMessage(Lang.MESSAGE_PLUGIN_RELOAD.getFormattedString().replace("%ms%", String.valueOf(System.currentTimeMillis() - start)));

        CustomMain.getInstance().getPlugin().reloadConfig();
        LangFile.reloadLang();

    }
}
