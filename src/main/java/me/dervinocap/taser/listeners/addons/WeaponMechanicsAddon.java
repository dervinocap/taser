package me.dervinocap.taser.listeners.addons;

import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponPreShootEvent;
import me.dervinocap.taser.loader.CustomMain;
import me.dervinocap.taser.utils.TaserUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WeaponMechanicsAddon implements Listener {

    @EventHandler
    public void onShoot(WeaponPreShootEvent event) {

        TaserUtils taserUtils = CustomMain.getInstance().getTaserUtils();
        
        if (!taserUtils.getPlayerTaser().contains(event.getShooter().getUniqueId())) return;

        event.setCancelled(true);

    }
}
