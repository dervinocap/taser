package me.dervinocap.taser.listeners;

import me.dervinocap.taser.Taser;
import me.dervinocap.taser.utils.Utils;
import me.dervinocap.taser.loader.CustomMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Credits implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(Taser.getInstance(), new Runnable() {
            @Override
            public void run() {

                if (player.hasPermission("taser.credits") || player.hasPermission("taser.*") || player.isOp()) {
                    player.sendMessage("");
                    player.sendMessage(Utils.hex("&3| &bHello &3" + player.getName()));
                    player.sendMessage(Utils.hex("&3| &bThis Server is running &3" + CustomMain.getInstance().getPluginName() + " &bv&3" + CustomMain.getInstance().getVersion() + " &bby &3" + CustomMain.getInstance().getAuthor()));
                    player.sendMessage("");
                }

            }
        },3*20);

    }
}
