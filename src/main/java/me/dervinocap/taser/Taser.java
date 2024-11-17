package me.dervinocap.taser;

import lombok.Getter;
import me.dervinocap.taser.loader.CustomMain;
import org.bukkit.plugin.java.JavaPlugin;

public final class Taser extends JavaPlugin {

    @Getter
    public static Taser instance;

    @Override
    public void onEnable() {

        instance = this;
        CustomMain.getInstance().enablePlugin();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
