package me.dervinocap.taser.listeners;

import me.deecaad.weaponmechanics.events.PlayerJumpEvent;
import me.dervinocap.taser.config.lang.Lang;
import me.dervinocap.taser.loader.CustomMain;
import me.dervinocap.taser.utils.TaserUtils;
import me.dervinocap.taser.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class TaserInteraction implements Listener {

    @EventHandler
    public void onJump(PlayerMoveEvent event) {

        TaserUtils taserUtils = CustomMain.getInstance().getTaserUtils();

        Player player = event.getPlayer();

        if (!taserUtils.getPlayerTaser().contains(player.getUniqueId())) return;

        event.setCancelled(true);
        Utils.sendMessage(player, Lang.TASER_INTERACTION_BLOCKED.getFormattedString());

    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {

        TaserUtils taserUtils = CustomMain.getInstance().getTaserUtils();

        Player player = event.getPlayer();

        if (!taserUtils.getPlayerTaser().contains(player.getUniqueId())) return;

        Utils.sendMessage(player, Lang.TASER_INTERACTION_BLOCKED.getFormattedString());
        event.setCancelled(true);

    }

    @EventHandler
    public void itemSwitch(PlayerItemHeldEvent event) {

        TaserUtils taserUtils = CustomMain.getInstance().getTaserUtils();

        Player player = event.getPlayer();

        if (!taserUtils.getPlayerTaser().contains(player.getUniqueId())) return;

        Utils.sendMessage(player, Lang.TASER_INTERACTION_BLOCKED.getFormattedString());
        event.setCancelled(true);

    }

}
