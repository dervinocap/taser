package me.dervinocap.taser.commands.tasercommands;

import lombok.Getter;
import me.dervinocap.taser.commands.CMDExecutionValidator;
import me.dervinocap.taser.commands.SubCommand;
import me.dervinocap.taser.commands.tasercommands.subcommands.GetCommand;
import me.dervinocap.taser.commands.tasercommands.subcommands.GiveCommand;
import me.dervinocap.taser.commands.tasercommands.subcommands.ReloadCommand;
import me.dervinocap.taser.config.lang.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class TaserMainCommand implements CommandExecutor {

    @Getter
    public static final Map<String, SubCommand> subCommands = new HashMap<>();
    public void register(String subCommandName, SubCommand command) {
        subCommands.put(subCommandName, command);
    }

    public TaserMainCommand() {
        register("get", new GetCommand());
        register("give", new GiveCommand());
        register("reload", new ReloadCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String unknown_command = Lang.MESSAGE_UNKNOWN_COMMAND.getFormattedString();

        if (!sender.hasPermission("taser.help")) {
            sender.sendMessage(Lang.MESSAGE_NO_PERMS.getFormattedString());
            return false;
        }

        if (args.length == 0) {

            for (String s : Lang.MESSAGE_PLUGIN_HELP.getFormattedStringList()) {
                sender.sendMessage(s);
            }

            return false;
        }

        String argument = args[0].toLowerCase();
        SubCommand commandToExecute = subCommands.get(argument);

        if (subCommands.get(argument) == null) {
            sender.sendMessage(unknown_command);
            return false;
        }

        if (!CMDExecutionValidator.validate(commandToExecute, sender)) return false;

        commandToExecute.execute(sender, args);

        return true;
    }

}
