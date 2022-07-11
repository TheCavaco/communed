package fucking.fuck.communed.listeners;

import fucking.fuck.communed.database.PlayerDB;
import fucking.fuck.communed.exceptions.CannotRemoveLeaderException;
import fucking.fuck.communed.exceptions.CannotRemoveNonMemberException;
import fucking.fuck.communed.gameobjects.Commune;
import fucking.fuck.communed.helpers.ChatHelp;
import fucking.fuck.communed.threads.InviteThread;
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

        InviteThread thread = new InviteThread(player, commune, inviter);
        thread.run();
    }

}
