package fucking.fuck.communed.listeners;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class CommuneListener implements Listener {

    @EventHandler
    public void onChunkSelected(ChunkLoadEvent event){
        // TODO: Claim chunk
    }
}
