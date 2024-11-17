package me.dervinocap.taser.utils;

import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.utils.UUIDUtil;
import me.dervinocap.taser.config.Config;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Items {

    public static ItemStack taserItem() {

        ItemStack item = new ItemStack(Config.TASER_ITEM.getMaterial());
        NBTItem nbti = new NBTItem(item, true);
        ItemMeta meta = nbti.getItem().getItemMeta();

        List<String> lore = new ArrayList<>();

        Config.TASER_LORE.getFormattedStringList().forEach(s -> {
            lore.add(s.replace("%charge%", Config.TASER_MAX_AMMO.getString()));
        });

        meta.setDisplayName(Config.TASER_NAME.getFormattedString().replace("%charge%", Config.TASER_MAX_AMMO.getString()));
        meta.setLore(lore);
        meta.setCustomModelData(Config.TASER_MODEL.getInt());

        nbti.getItem().setItemMeta(meta);
        nbti.setInteger("taser.ammo", Config.TASER_MAX_AMMO.getInt());
        nbti.setString("taser.id", UUID.randomUUID().toString());

        return nbti.getItem();
    }

    public static ItemStack reloadItem() {

        ItemStack item = new ItemStack(Config.RELOAD_ITEM.getMaterial());
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Config.RELOAD_NAME.getFormattedString());
        meta.setLore(Config.RELOAD_LORE.getFormattedStringList());
        meta.setCustomModelData(Config.RELOAD_MODEL.getInt());

        item.setItemMeta(meta);

        return item;
    }

}
