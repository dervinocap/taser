package me.dervinocap.taser.utils;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import me.dervinocap.taser.config.Config;
import me.dervinocap.taser.loader.CustomMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaserUtils {

    @Getter
    private final List<UUID> playerTaser = new ArrayList<>();
    @Getter
    private final List<UUID> delayedPlayers = new ArrayList<>();

    public void taserUse(Player player, Player clickedPlayer) {

        // Play Custom Sounds
        // Taken on the config.yml

        for (String sounds : Config.TASER_SOUND.getStringList()) {
            String[] soundValues = sounds.split(";");

            if (soundValues.length != 4) return;

            String soundName = soundValues[0];
            float volume = Float.parseFloat(soundValues[1]);
            float pitch = Float.parseFloat(soundValues[2]);
            int delay = Integer.parseInt(soundValues[3]);

            if (soundName.startsWith("<custom-sound>")) {

                Bukkit.getScheduler().runTaskLater(CustomMain.getInstance().getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        clickedPlayer.getWorld().playSound(clickedPlayer.getLocation(), soundName.replace("<custom-sound>", ""), volume, pitch);
                    }
                }, delay);

            } else {

                Sound sound = Sound.valueOf(soundName);

                Bukkit.getScheduler().runTaskLater(CustomMain.getInstance().getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        clickedPlayer.getWorld().playSound(clickedPlayer.getLocation(), sound, volume, pitch);
                    }
                }, delay);
            }
        }

        // Add Effects
        // Taken on the config.yml

        for (String effects : Config.TASER_EFFECTS.getStringList()) {
            String[] effectsValues = effects.split(";");

            if (effectsValues.length != 3) return;

            String effect = effectsValues[0];
            int duration = Integer.parseInt(effectsValues[1]);
            int amplifier = Integer.parseInt(effectsValues[2]);

            if (PotionEffectType.getByName(effect) == null) {
                Utils.printError();
                return;
            }

            clickedPlayer.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect), duration*20, amplifier));
        }

        // Make leave vehicle

        clickedPlayer.leaveVehicle();

        // Send Titles and Subtitle

        clickedPlayer.sendTitle(Config.TASER_TITLE.getFormattedString(), Config.TASER_SUBTITLE.getFormattedString());

        // Detracting the taser charge

        NBTItem nbtItem = new NBTItem(player.getInventory().getItemInMainHand(), true);

        nbtItem.setInteger("taser.ammo", nbtItem.getInteger("taser.ammo") - 1);
        nbtItem.applyNBT(player.getInventory().getItemInMainHand());

        // Change lore

        List<String> lore = new ArrayList<>();
        List<String> loreNew = new ArrayList<>();

        if (player.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
            for (String s : player.getInventory().getItemInMainHand().getItemMeta().getLore()) {
                for (String s1 : Config.TASER_LORE.getFormattedStringList()) {

                    if (!s.equalsIgnoreCase(s1.replace("%charge%", String.valueOf( nbtItem.getInteger("taser.ammo") + 1)))) {
                        lore.add(s);
                    }
                }
            }
        }

        for (String s : Config.TASER_LORE.getFormattedStringList()) {
            loreNew.add(s.replace("%charge%", nbtItem.getInteger("taser.ammo").toString()));
        }

        loreNew.addAll(lore);

        if (clickedPlayer.getInventory().getItemInMainHand().getType().equals(Material.SHIELD)) {
            clickedPlayer.getInventory().setHeldItemSlot(player.getInventory().getHeldItemSlot()+1);
        }

        // Apply lore and display name

        ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();

        meta.setDisplayName(Config.TASER_NAME.getFormattedString().replace("%charge%", nbtItem.getInteger("taser.ammo").toString()));
        meta.setLore(loreNew);

        player.getInventory().getItemInMainHand().setItemMeta(meta);

        // Add delay to player

        getDelayedPlayers().add(player.getUniqueId());

        // Add taser to clickedPlayer

        getPlayerTaser().add(clickedPlayer.getUniqueId());

        // Make player leave vehicle

        clickedPlayer.leaveVehicle();

        // Remove delay to player

        Bukkit.getScheduler().runTaskLater(CustomMain.getInstance().getPlugin(), new Runnable() {
            @Override
            public void run() {
                getDelayedPlayers().remove(player.getUniqueId());
            }
        }, Config.TASER_DELAY.getInt()*20);

        // Remove taser to clickedPlayer

        Bukkit.getScheduler().runTaskLater(CustomMain.getInstance().getPlugin(), new Runnable() {
            @Override
            public void run() {
                getPlayerTaser().remove(clickedPlayer.getUniqueId());
            }
        }, Config.TASER_DURATION.getInt()*20);
    }
}
