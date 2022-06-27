package fucking.fuck.communed.events;

import org.bukkit.event.Event;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class OnPlayerInvitedEvent extends Event implements Cancellable{
    private static final HandlerList HANDLERS = new HandlerList();
    private final String playerName;
    private final UUID communeId;
    private final String inviterName;
    private boolean isCancelled;


    public OnPlayerInvitedEvent(String playerName, UUID communeId, String inviterName){
        this.playerName = playerName;
        this.communeId = communeId;
        this.inviterName = inviterName;
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

    public String getPlayerName() {
        return this.playerName;
    }

    public UUID getCommuneName() {
        return this.communeId;
    }

    public String getInviterName() { return this.inviterName; }
}
