package me.dervinocap.taser.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class TaserUseEvent extends Event implements Cancellable {

    private Player player;
    private Player clickedPlayer;

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public TaserUseEvent(Player player, Player clickedPlayer){
        this.player = player;
        this.clickedPlayer = clickedPlayer;
        this.isCancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}