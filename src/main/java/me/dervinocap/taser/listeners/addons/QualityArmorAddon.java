package me.dervinocap.taser.listeners.addons;

import me.dervinocap.taser.loader.CustomMain;
import me.dervinocap.taser.utils.TaserUtils;
import me.zombie_striker.qg.api.QAWeaponPrepareShootEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class QualityArmorAddon implements Listener {

    @EventHandler
    public void onShoot(QAWeaponPrepareShootEvent event) {

        TaserUtils taserUtils = CustomMain.getInstance().getTaserUtils();

        Player player = event.getPlayer();

        if (!taserUtils.getPlayerTaser().contains(player.getUniqueId())) return;

        event.setCancelled(true);

    }
}
