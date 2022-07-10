package fucking.fuck.communed.listeners;

import fucking.fuck.communed.database.ChunkDB;
import fucking.fuck.communed.database.PlayerDB;
import fucking.fuck.communed.gameobjects.Commune;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class CommuneListener implements Listener {


    public void onMovingChunks(PlayerMoveEvent event){
        Chunk first = event.getFrom().getChunk();
        Chunk last = event.getTo().getChunk();
        UUID claimer_first = ChunkDB.getClaimer(first);
        UUID claimer_last = ChunkDB.getClaimer(last);

        if(!claimer_first.equals(claimer_last) && !claimer_last.equals(Commune.getNullUUID())){
            PlayerDB.displayCommune(event.getPlayer(), Commune.loadCommune(claimer_last));
        } else if (claimer_last.equals(Commune.getNullUUID())){
            //TODO: wilderness needs a screen
            Player player = event.getPlayer();
            player.sendTitle(ChatColor.RED + "FREE GROUND", "No mans land", 10, 50, 20);
        }
    }
}
