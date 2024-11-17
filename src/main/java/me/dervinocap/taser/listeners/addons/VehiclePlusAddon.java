package me.dervinocap.taser.listeners.addons;

import me.dervinocap.taser.loader.CustomMain;
import me.dervinocap.taser.utils.TaserUtils;
import me.legofreak107.vehiclesplus.vehicles.api.events.VehicleEnterEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VehiclePlusAddon implements Listener {

    @EventHandler
    public void vehicleEnter(VehicleEnterEvent event) {

        TaserUtils taserUtils = CustomMain.getInstance().getTaserUtils();

        Player player = event.getDriver();

        if (!taserUtils.getPlayerTaser().contains(player.getUniqueId())) return;

        event.setCancelled(true);

    }

}
