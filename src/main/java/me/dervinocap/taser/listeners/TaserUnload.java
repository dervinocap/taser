package me.dervinocap.taser.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.dervinocap.taser.config.Config;
import me.dervinocap.taser.config.lang.Lang;
import me.dervinocap.taser.utils.Items;
import me.dervinocap.taser.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TaserUnload implements Listener {

    @EventHandler
    public void onClick(PlayerDropItemEvent event) {

        Player player = event.getPlayer();

        if (event.isCancelled()) return;

        if (event.getItemDrop().getItemStack() == null || event.getItemDrop().getItemStack().getType() == Material.AIR || event.getItemDrop().getItemStack().getAmount() == 0)
            return;

        if (!Utils.isTaser(event.getItemDrop().getItemStack())) return;

        event.setCancelled(true);

        NBTItem nbtItem = new NBTItem(event.getItemDrop().getItemStack(), true);

        if (nbtItem.getInteger("taser.ammo") < 1) {
            Utils.sendMessage(player, Lang.TASER_EMPTY.getFormattedString());
            return;
        }

        if (Config.TASER_UNLOAD_SOUND.getString().startsWith("<custom-sound>")) {
            player.getWorld().playSound(player.getLocation(), Config.TASER_UNLOAD_SOUND.getString().replace("<custom-sound>", ""), 1, 1);
        } else {
            player.getWorld().playSound(player.getLocation(), Sound.valueOf(Config.TASER_UNLOAD_SOUND.getString()), 1, 1);
        }

        ItemStack ammoItem = Items.reloadItem();

        ammoItem.setAmount(nbtItem.getInteger("taser.ammo"));

        player.getInventory().addItem(ammoItem);

        nbtItem.setInteger("taser.ammo", 0);
        nbtItem.applyNBT(event.getItemDrop().getItemStack());

        // Change lore

        List<String> lore = new ArrayList<>();
        List<String> loreNew = new ArrayList<>();

        if (event.getItemDrop().getItemStack().getItemMeta().getLore() != null) {
            for (String s : event.getItemDrop().getItemStack().getItemMeta().getLore()) {
                for (String s1 : Config.TASER_LORE.getFormattedStringList()) {

                    if (!s.equalsIgnoreCase(s1.replace("%charge%", String.valueOf(nbtItem.getInteger("taser.ammo") + Config.TASER_MAX_AMMO.getInt())))) {
                        lore.add(s);
                    }
                }
            }
        }

        for (String s : Config.TASER_LORE.getFormattedStringList()) {
            loreNew.add(s.replace("%charge%", nbtItem.getInteger("taser.ammo").toString()));
        }

        loreNew.addAll(lore);

        // Apply lore and display name

        ItemMeta meta = event.getItemDrop().getItemStack().getItemMeta();

        meta.setDisplayName(Config.TASER_NAME.getFormattedString().replace("%charge%", nbtItem.getInteger("taser.ammo").toString()));
        meta.setLore(loreNew);

        event.getItemDrop().getItemStack().setItemMeta(meta);

        Utils.sendMessage(player, Lang.TASER_UNLOADED.getFormattedString());

    }
}
