package me.dervinocap.taser.config;

import me.dervinocap.taser.utils.Utils;
import me.dervinocap.taser.loader.CustomMain;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public enum Config {

    RELOAD_ITEM(".items.reload.item"),
    RELOAD_NAME(".items.reload.name"),
    RELOAD_LORE(".items.reload.lore"),
    RELOAD_MODEL(".items.reload.model"),

    TASER_ITEM(".items.taser.item"),
    TASER_NAME(".items.taser.name"),
    TASER_LORE(".items.taser.lore"),
    TASER_MODEL(".items.taser.model"),

    TASER_MAX_AMMO(".taser.max-ammo"),
    TASER_BACK(".taser.back-taser"),
    TASER_SOUND(".taser.sound"),
    TASER_RELOAD_SOUND(".taser.reload-sound"),
    TASER_UNLOAD_SOUND(".taser.unload-sound"),
    TASER_EFFECTS(".taser.effects"),
    TASER_TITLE(".taser.title"),
    TASER_SUBTITLE(".taser.subtitle"),
    TASER_DURATION(".taser.duration"),
    TASER_DELAY(".taser.delay"),
    TASER_MAX_DISTANCE(".taser.max-distance");

    private final String path;

    private Config(String path) {
        this.path = path;
    }

    public boolean getBoolean() {
        return CustomMain.getInstance().getPlugin().getConfig().getBoolean(this.path);
    }

    public String getFormattedString() {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(CustomMain.getInstance().getPlugin().getConfig().getString(this.path)));
    }

    public Material getMaterial() {
        return Material.getMaterial(Objects.requireNonNull(CustomMain.getInstance().getPlugin().getConfig().getString(this.path)));
    }

    public String getString() {
        return CustomMain.getInstance().getPlugin().getConfig().getString(this.path);
    }

    public int getInt() {
        return CustomMain.getInstance().getPlugin().getConfig().getInt(this.path);
    }

    public double getDouble() {
        return CustomMain.getInstance().getPlugin().getConfig().getDouble(this.path);
    }

    public List<String> getStringList() {
        return CustomMain.getInstance().getPlugin().getConfig().getStringList(this.path);
    }

    public List<String> getFormattedStringList() {

        List<String> formattedList = new ArrayList<>();

        CustomMain.getInstance().getPlugin().getConfig().getStringList(this.path).forEach(s -> {
            formattedList.add(Utils.hex(s));
        });

        return formattedList;
    }

    public static String getFormattedString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
