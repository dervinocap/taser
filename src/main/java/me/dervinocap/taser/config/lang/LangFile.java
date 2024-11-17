package me.dervinocap.taser.config.lang;

import com.google.common.base.Charsets;
import lombok.Getter;
import me.dervinocap.taser.Taser;
import me.dervinocap.taser.loader.CustomMain;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class LangFile {

    private static File dataFile;

    @Getter
    public static YamlConfiguration data;

    private static Taser plugin = CustomMain.getInstance().getPlugin();

    public static void setupData() {

        dataFile = new File(plugin.getDataFolder(), "lang.yml");

        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            CustomMain.getInstance().getPlugin().saveResource("lang.yml", false);
        }

        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    @Nullable
    public static InputStream getResource(String filename) {
        try {
            URL url = ClassLoader.getSystemResource(filename);
            if (url == null) {
                return null;
            } else {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
        } catch (IOException var4) {
            return null;
        }
    }

    public static void reloadLang() {
        FileConfiguration newConfig = LangFile.getData();
        InputStream defConfigStream = getResource("lang.yml");
        if (defConfigStream != null) {
            newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        }
    }
}
