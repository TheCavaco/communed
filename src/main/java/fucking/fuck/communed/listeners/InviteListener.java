package fucking.fuck.communed.listeners;

import fucking.fuck.communed.database.PlayerDB;
import fucking.fuck.communed.exceptions.CannotRemoveLeaderException;
import fucking.fuck.communed.exceptions.CannotRemoveNonMemberException;
import fucking.fuck.communed.gameobjects.Commune;
import fucking.fuck.communed.helpers.ChatHelp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import fucking.fuck.communed.events.OnPlayerInvitedEvent;
import org.bukkit.persistence.PersistentDataContainer;

public class InviteListener implements Listener {

    @EventHandler
    public void onPlayerInvited(OnPlayerInvitedEvent event) throws InterruptedException, CannotRemoveLeaderException, CannotRemoveNonMemberException {
        Player player = Bukkit.getPlayer(event.getPlayerName());
        Player inviter = Bukkit.getPlayer(event.getInviterName());
        if (event.getCommuneName().equals(Commune.getNullUUID())){
            ChatHelp.sendBadMessage(inviter, "You are not in a commune.");
            return;
        }
        Commune commune = Commune.loadCommune(event.getCommuneName());


        if(player == null){
            ChatHelp.sendBadMessage(inviter, "Player " + event.getPlayerName() + " is not available for invites.");
            return;
        }

        PersistentDataContainer data = player.getPersistentDataContainer();

        // player has Commune
        if(PlayerDB.hasCommune(data)){
            ChatHelp.sendBadMessage(inviter, "Player " + player.getName() + " already is in a Commune.");
            return;
        }


        ChatHelp.sendSuccess(player, "You have been invited to the Commune named " + commune +
                "\nUse /com accept or /com deny to proceed.");

        if(!PlayerDB.isInvited(data)){
            PlayerDB.setInvited(data, commune.getName());
        } else {
            ChatHelp.sendBadMessage(inviter, "Player " + player.getName() + " has to accept other invites.");
            return;
        }

        long time = System.currentTimeMillis();
        long end = time+15000;


        while(System.currentTimeMillis() < end){
            data = player.getPersistentDataContainer();
            String result = PlayerDB.getInvited(data);
            if(result.equals("ac")){
                //player gets in the commune
                commune.addPlayer(player);
                PlayerDB.addCommune(player, commune.getId());
                if(!commune.saveData()){
                    ChatHelp.sendErrorMessage(player);
                    PlayerDB.removeCommune(player);
                    commune.removePlayer(player);
                    break;
                }
                ChatHelp.sendSuccess(player, "You are now a member of " + commune.getName() + ".");
                break;

            } else if (result.equals("de")) {
                ChatHelp.sendMessage(player, "Rejected the invite.");
                ChatHelp.sendBadMessage(inviter, player.getName() + " rejected the invite.");
                break;
            }
            Thread.sleep(100);
        }
        //player stops being invited to any commune
        PlayerDB.setInvited(data, "");





    }

}
