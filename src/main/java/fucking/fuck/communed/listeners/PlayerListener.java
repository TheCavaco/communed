package fucking.fuck.communed.listeners;


import fucking.fuck.communed.database.PlayerDB;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {





    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if(player.hasPlayedBefore()){
            return;
        }

        PlayerDB.addCommune(player, "");
    }
}
