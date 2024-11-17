package me.dervinocap.taser.config.lang;

import me.dervinocap.taser.utils.Utils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public enum Lang {

    TASER_EMPTY("taser.taser-empty"),
    TASER_FULL("taser.taser-full"),
    TASER_MESSAGE_TOO_FAR("taser.too-far"),
    TASER_DELAY("taser.delay"),
    TASER_CANT_USE("taser.cant-use"),
    TASER_PLAYER_EXEMPTED("taser.player-exempted"),
    TASER_PLAYER_BLOCKING("taser.player-blocking"),
    TASER_INTERACTION_BLOCKED("taser.interaction-blocked"),
    TASER_RELOADED("taser.reload-succesfully"),
    TASER_UNLOADED("taser.unload-succesfully"),

    MESSAGE_UNKNOWN_COMMAND("messages.unknown-command"),
    MESSAGE_ITEM_RECEIVED("messages.item-received"),
    MESSAGE_PLAYER_OFFLINE("messages.player-offline"),
    MESSAGE_ITEM_GIVEN("messages.item-given"),
    MESSAGE_PLUGIN_RELOAD("messages.plugin-reload"),
    MESSAGE_PLUGIN_HELP("messages.plugin-help"),
    MESSAGE_NO_PERMS("messages.no-perms");

    private final String path;

    private Lang(String path) {
        this.path = path;
    }

    public String getFormattedString() {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LangFile.getData().getString(this.path)));
    }

    public String getString() {
        return LangFile.getData().getString(this.path);
    }

    public int getInt() {
        return LangFile.getData().getInt(this.path);
    }

    public List<String> getStringList() {
        return LangFile.getData().getStringList(this.path);
    }

    public List<String> getFormattedStringList() {

        List<String> formattedList = new ArrayList<>();

        LangFile.getData().getStringList(this.path).forEach(s -> {
            formattedList.add(Utils.hex(s));
        });

        return formattedList;
    }

    public static String getFormattedString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
