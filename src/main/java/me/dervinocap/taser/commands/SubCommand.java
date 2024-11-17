package me.dervinocap.taser.commands;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    public abstract boolean isPlayerOnly();
    public abstract String getPermission();

    public abstract void execute(CommandSender sender, String[] args);
}
