package fucking.fuck.communed.listeners;

import fucking.fuck.communed.database.PlayerDB;
import fucking.fuck.communed.exceptions.NoRelationException;
import fucking.fuck.communed.exceptions.SamePlayerException;
import fucking.fuck.communed.gameobjects.Commune;
import fucking.fuck.communed.helpers.ChatHelp;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        //TODO: something I guess
        Player sender = event.getPlayer();
        Set<Player> recipients = event.getRecipients();


        String communeName = PlayerDB.getCommuneName(sender);
        String senderName = sender.getName();
        String message = event.getMessage();


        //FUCk same player as sender

        for(Player receiver: recipients){
            try{
                if(PlayerDB.areRelated(sender, receiver)){
                    ChatHelp.sendAllyMessage(receiver, communeName, senderName, message, "Role");
                } else {
                    ChatHelp.sendEnemyMessage(receiver, communeName, senderName, message);
                }
            } catch (NoRelationException e) {
                ChatHelp.sendNormalMessage(receiver, communeName, senderName, message);
            } catch (SamePlayerException e){
                ChatHelp.sendCommuneMessage(receiver, communeName, senderName, message, "Role");
            }
        }

        event.setCancelled(true);



    }
}
