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


        String name = player.getName();
        if(false){
            System.out.println("Player " + name + " couldn't be added to the database");
            return;
        }
        System.out.println("Player " + name + " was added to the database");
    }
}
