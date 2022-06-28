package fucking.fuck.communed.listeners;


import fucking.fuck.communed.database.PlayerDB;
import fucking.fuck.communed.gameobjects.Commune;
import fucking.fuck.communed.helpers.ChatHelp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){

        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();
            if(PlayerDB.getCommune(damager).equals(PlayerDB.getCommune(damaged))){
                ChatHelp.sendBadMessage(damager, "This player is in your commune.");
                event.setCancelled(true);
            }
        }
    }





    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();


        if(player.hasPlayedBefore()){
            return;
        }

        PlayerDB.addCommune(player, Commune.getNullUUID());
    }
}
