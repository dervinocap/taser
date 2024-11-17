package me.dervinocap.taser.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.dervinocap.taser.Taser;
import me.dervinocap.taser.config.Config;
import me.dervinocap.taser.config.lang.Lang;
import me.dervinocap.taser.utils.Items;
import me.dervinocap.taser.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TaserReload implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.isCancelled()) return;

        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR || player.getInventory().getItemInMainHand().getAmount() == 0) return;

        if (!Utils.isTaser(player.getInventory().getItemInMainHand())) return;

        if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

            NBTItem nbtItem = new NBTItem(player.getInventory().getItemInMainHand(), true);

            if (nbtItem.getInteger("taser.ammo") >= Config.TASER_MAX_AMMO.getInt()) {
                Utils.sendMessage(player, Lang.TASER_FULL.getFormattedString());
                return;
            }

            if (!(player.getInventory().containsAtLeast(Items.reloadItem(), 1))) return;

            if (Config.TASER_RELOAD_SOUND.getString().startsWith("<custom-sound>")) {
                player.getWorld().playSound(player.getLocation(), Config.TASER_RELOAD_SOUND.getString().replace("<custom-sound>", ""), 1, 1);
            } else {
                player.getWorld().playSound(player.getLocation(), Sound.valueOf(Config.TASER_RELOAD_SOUND.getString()), 1, 1);
            }
            player.getInventory().removeItem(Items.reloadItem());

            nbtItem.setInteger("taser.ammo", nbtItem.getInteger("taser.ammo") + 1);
            nbtItem.applyNBT(player.getInventory().getItemInMainHand());

            // Change lore

            List<String> lore = new ArrayList<>();
            List<String> loreNew = new ArrayList<>();

            if (player.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
                for (String s : player.getInventory().getItemInMainHand().getItemMeta().getLore()) {
                    for (String s1 : Config.TASER_LORE.getFormattedStringList()) {

                        if (!s.equalsIgnoreCase(s1.replace("%charge%", String.valueOf(nbtItem.getInteger("taser.ammo") - 1)))) {
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

            ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();

            meta.setDisplayName(Config.TASER_NAME.getFormattedString().replace("%charge%", nbtItem.getInteger("taser.ammo").toString()));
            meta.setLore(loreNew);

            player.getInventory().getItemInMainHand().setItemMeta(meta);

            Utils.sendMessage(player, Lang.TASER_RELOADED.getFormattedString());

            event.setCancelled(true);

        }

    }

}
