package fucking.fuck.communed.events;

import fucking.fuck.communed.gameobjects.Commune;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnPlayerSwitchChunk  extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final Commune commune;
    private boolean isCancelled;


    public OnPlayerSwitchChunk(Player player, Commune commune){
        this.player = player;
        this.commune = commune;
    }



    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getPlayerName() {
        return this.player;
    }

    public Commune getCommuneName() {
        return this.commune;
    }
}
