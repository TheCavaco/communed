package fucking.fuck.communed.threads;

import fucking.fuck.communed.database.PlayerDB;
import fucking.fuck.communed.exceptions.CannotRemoveLeaderException;
import fucking.fuck.communed.exceptions.CannotRemoveNonMemberException;
import fucking.fuck.communed.gameobjects.Commune;
import fucking.fuck.communed.helpers.ChatHelp;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;

public class InviteThread extends Thread{

    private Player player;
    private Commune commune;

    private Player inviter;

    public InviteThread(Player player, Commune commune, Player inviter){
        this.player = player;
        this.commune = commune;
        this.inviter = inviter;
    }

    @Override
    public void run(){
        long time = System.currentTimeMillis();
        long end = time+15000;

        while(System.currentTimeMillis() < end){
            PersistentDataContainer data = player.getPersistentDataContainer();
            String result = PlayerDB.getInvited(data);
            if(result.equals("ac")){
                //player gets in the commune
                commune.addPlayer(player);
                PlayerDB.addCommune(player, commune.getId());
                if(!commune.saveData()){
                    ChatHelp.sendErrorMessage(player);
                    PlayerDB.removeCommune(player);
                    try {
                        commune.removePlayer(player);
                    } catch (CannotRemoveLeaderException e) {
                        throw new RuntimeException(e);
                    } catch (CannotRemoveNonMemberException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                ChatHelp.sendSuccess(player, "You are now a member of " + commune.getName() + ".");
                break;

            } else if (result.equals("de")) {
                ChatHelp.sendMessage(player, "Rejected the invite.");
                ChatHelp.sendBadMessage(inviter, player.getName() + " rejected the invite.");
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        PersistentDataContainer data = player.getPersistentDataContainer();
        //player stops being invited to any commune
        PlayerDB.setInvited(data, "");
    }
}
