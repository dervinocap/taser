package me.dervinocap.taser.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.dervinocap.taser.Taser;
import me.dervinocap.taser.events.TaserUseEvent;
import me.dervinocap.taser.loader.CustomMain;
import me.dervinocap.taser.utils.TaserUtils;
import me.dervinocap.taser.utils.Utils;
import me.dervinocap.taser.config.Config;
import me.dervinocap.taser.config.lang.Lang;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

public class TaserUse implements Listener {

    @EventHandler
    public void taserUse(PlayerInteractAtEntityEvent event) {

        TaserUtils taserUtils = CustomMain.getInstance().getTaserUtils();
        
        if (!(event.getRightClicked() instanceof Player clickedPlayer)) return;

        if (event.isCancelled()) return;

        if (!(event.getHand() == EquipmentSlot.HAND)) return;

        Player player = event.getPlayer();

        if (!Utils.isTaser(player.getInventory().getItemInMainHand())) return;

        if (player.getInventory().getItemInMainHand() == null && player.getInventory().getItemInMainHand().getType() == Material.AIR && player.getInventory().getItemInMainHand().getAmount() == 0) return;

        NBTItem taser = new NBTItem(player.getInventory().getItemInMainHand(), true);

        if (taser.getInteger("taser.ammo") < 1) {
            Utils.sendMessage(player, Lang.TASER_EMPTY.getFormattedString());
            return;
        }

        if (!(clickedPlayer.getLocation().distance(player.getLocation()) <= Config.TASER_MAX_DISTANCE.getDouble())) {
            Utils.sendMessage(player, Lang.TASER_MESSAGE_TOO_FAR.getFormattedString());
            return;
        }

        if (taserUtils.getDelayedPlayers().contains(player.getUniqueId())) {
            Utils.sendMessage(player, Lang.TASER_DELAY.getFormattedString());
            return;
        }

        if (taserUtils.getPlayerTaser().contains(player.getUniqueId()) || taserUtils.getPlayerTaser().contains(clickedPlayer.getUniqueId())) {
            Utils.sendMessage(player, Lang.TASER_CANT_USE.getFormattedString());
            return;
        }

        if (clickedPlayer.hasPermission("taser.bypass") || clickedPlayer.getGameMode().equals(GameMode.CREATIVE)) {
            Utils.sendMessage(player, Lang.TASER_PLAYER_EXEMPTED.getFormattedString().replace("%player%", clickedPlayer.getName()));
            return;
        }

        TaserUseEvent taserUseEvent = new TaserUseEvent(player, clickedPlayer);
        taserUseEvent.setPlayer(player);
        taserUseEvent.setClickedPlayer(clickedPlayer);

        if (taserUseEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        Taser.getInstance().getServer().getPluginManager().callEvent(taserUseEvent);

        if (Config.TASER_BACK.getBoolean()) {

            Location attackerLocation = player.getLocation();
            Location targetLocation = clickedPlayer.getLocation();

            Vector targetToAttacker = attackerLocation.toVector().subtract(targetLocation.toVector()).normalize();

            Vector targetFacing = targetLocation.getDirection().normalize();

            double angle = targetFacing.angle(targetToAttacker);

            if (angle > Math.PI / 2 && clickedPlayer.isBlocking()) {
                taserUtils.taserUse(player, clickedPlayer);
                return;
            }
        }

        if (clickedPlayer.isBlocking()) {
            Utils.sendMessage(player, Lang.TASER_PLAYER_BLOCKING.getFormattedString());
            return;
        }

        taserUtils.taserUse(player, clickedPlayer);

    }

}
