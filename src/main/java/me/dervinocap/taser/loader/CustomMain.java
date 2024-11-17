package me.dervinocap.taser.loader;

import lombok.Getter;
import me.dervinocap.taser.Taser;
import me.dervinocap.taser.commands.tasercommands.TabComplete;
import me.dervinocap.taser.commands.tasercommands.TaserMainCommand;
import me.dervinocap.taser.config.lang.LangFile;
import me.dervinocap.taser.listeners.*;
import me.dervinocap.taser.listeners.addons.CrackShotAddon;
import me.dervinocap.taser.listeners.addons.QualityArmorAddon;
import me.dervinocap.taser.listeners.addons.VehiclePlusAddon;
import me.dervinocap.taser.listeners.addons.WeaponMechanicsAddon;
import me.dervinocap.taser.utils.TaserUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.bukkit.Bukkit.*;

public class CustomMain {

    @Getter
    private static final CustomMain instance = new CustomMain();

    @Getter
    private final String pluginName = "taser";
    @Getter
    private final String author = "DerviNoCap";
    @Getter
    private final String version = "1.0";
    @Getter
    private final Taser plugin = Taser.getInstance();

    @Getter
    private TaserUtils taserUtils = new TaserUtils();

    @Getter
    public static List<String> dependencies = new ArrayList<>();

    public void loadListeners() {

        if (getPlugin().getServer().getPluginManager().getPlugin("CrackShot") != null) {
            getLogger().info("CrackShot detected! Enabling listeners...");
            Bukkit.getPluginManager().registerEvents(new CrackShotAddon(), plugin);
        }

        if (getPlugin().getServer().getPluginManager().getPlugin("QualityArmory") != null) {
            getLogger().info("QualityArmory detected! Enabling listeners...");
            Bukkit.getPluginManager().registerEvents(new QualityArmorAddon(), plugin);
        }

        if (getPlugin().getServer().getPluginManager().getPlugin("VehiclesPlusPro") != null) {
            getLogger().info("VehiclesPlusPro detected! Enabling listeners...");
            Bukkit.getPluginManager().registerEvents(new VehiclePlusAddon(), plugin);
        }

        if (getServer().getPluginManager().getPlugin("WeaponMechanics") != null) {
            getLogger().info("WeaponMechanics detected! Enabling listeners...");
            Bukkit.getPluginManager().registerEvents(new WeaponMechanicsAddon(), plugin);
        }

        Bukkit.getPluginManager().registerEvents(new Credits(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new TaserUse(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new TaserReload(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new TaserUnload(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new TaserInteraction(), getPlugin());
    }

    public void loadCommands() {
        getPluginCommand("taser").setExecutor(new TaserMainCommand());
    }

    public void loadTabCompleters() {
        getPluginCommand("taser").setTabCompleter(new TabComplete());
    }

    public void loadConfig() {
        getPlugin().saveDefaultConfig();
        LangFile.setupData();
    }

    public void setupDependencies() {
        //dependencies.add("");
    }

    public AtomicBoolean checkIfHasLoadedDependencies() {

        AtomicBoolean b = new AtomicBoolean(false);

        dependencies.forEach(s -> {

            if (getPlugin().getServer().getPluginManager().getPlugin(s) == null) {
                b.set(false);
            } else {
                b.set(true);
            }

        });

        return b;
    }

    public void printEnableMessage() {

        Bukkit.getServer().getConsoleSender().sendMessage("");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "――――――――――――――――――――――――――――――――――――――――――――――――――――――");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "      __                      ");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "     / /_____ _________  _____");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "    / __/ __ `/ ___/ _ \\/ ___/");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "   / /_/ /_/ (__  )  __/ /    ");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "   \\__/\\__,_/____/\\___/_/     ");
        Bukkit.getServer().getConsoleSender().sendMessage("");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "   Author: " + ChatColor.YELLOW + author + ChatColor.GRAY + ".");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "   Version: " + ChatColor.YELLOW + version + ChatColor.GRAY + ".");
        Bukkit.getServer().getConsoleSender().sendMessage("");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "   Server Type: " + ChatColor.YELLOW + Bukkit.getServer().getName() + " " + Bukkit.getServer().getVersion());
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "   Running on " + ChatColor.YELLOW + "Java " + System.getProperty("java.version"));
        Bukkit.getServer().getConsoleSender().sendMessage("");


        if (!checkIfHasLoadedDependencies().get() && !dependencies.isEmpty()) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "   Dependencies: " + ChatColor.RED + "Missing" + ChatColor.GRAY + "!");
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "   Be sure to install all the Dependencies before enabling the plugin!");
            Bukkit.getServer().getConsoleSender().sendMessage("");
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "――――――――――――――――――――――――――――――――――――――――――――――――――――――");
            return;
        }

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "――――――――――――――――――――――――――――――――――――――――――――――――――――――");
    }

    public void enablePlugin() {

        setupDependencies();
        printEnableMessage();

        if (!checkIfHasLoadedDependencies().get() && !dependencies.isEmpty()) return;

        loadConfig();
        loadListeners();
        loadCommands();
        loadTabCompleters();

    }

    public void disablePlugin() {
    }

}
