package me.dervinocap.taser.listeners.addons;

import com.shampaggon.crackshot.events.WeaponPrepareShootEvent;
import me.dervinocap.taser.listeners.TaserUse;
import me.dervinocap.taser.loader.CustomMain;
import me.dervinocap.taser.utils.TaserUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CrackShotAddon implements Listener {

    @EventHandler
    public void onShoot(WeaponPrepareShootEvent event) {

        TaserUtils taserUtils = CustomMain.getInstance().getTaserUtils();
        
        if (!taserUtils.getPlayerTaser().contains(event.getPlayer().getUniqueId())) return;

        event.setCancelled(true);

    }
}
