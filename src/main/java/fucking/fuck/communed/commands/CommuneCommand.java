package fucking.fuck.communed.commands;


import fucking.fuck.communed.database.PlayerDB;
import fucking.fuck.communed.events.OnPlayerInvitedEvent;
import fucking.fuck.communed.gameobjects.Commune;
import fucking.fuck.communed.helpers.ChatHelp;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;


public class CommuneCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (!(sender instanceof Player)){
            // for debugging purposes
            if(args.length == 2){
                switch (args[0]){
                    case "deletecom":
                        if (PlayerDB.removeCommune(Bukkit.getPlayer(args[1]))){
                            sender.sendMessage("Faction removed.");
                        } else {
                            sender.sendMessage("Some kind of error.");
                        }
                        break;
                }
            }
            sender.sendMessage("Only players can run this command");
            return false;
        } else {
            // Player that executed the command create the commune
            Player player = (Player) sender;
            if (args.length == 0){
                showHelp(player, "");
            } else if (args.length == 1) {
                processOneArg(args, player);
            } else if (args.length > 1){
                processTwoArg(args, player);
            }
        }
        return true;
    }



    
    
    private void processOneArg(String[] args, Player player){
        switch (args[0]){
            case "c":
                showHelp(player, args[0]);
                break;
            case "i":
                showCommune(player);
                break;
            case "inv":
                //check permissions
                ChatHelp.sendHelp(player, "You need to specify a player name to invite\nUse /com inv <playername>");
                break;
            case "accept":
                PersistentDataContainer data = player.getPersistentDataContainer();
                if (PlayerDB.isInvited(data)){
                    PlayerDB.setInvited(data, "ac");
                } else {
                    ChatHelp.sendBadMessage(player, "You have no pending invites.");
                }
                break;
            case "deny":
                PersistentDataContainer data_deny = player.getPersistentDataContainer();
                if (PlayerDB.isInvited(data_deny)){
                    PlayerDB.setInvited(data_deny, "de");
                } else {
                    ChatHelp.sendBadMessage(player, "You have no pending invites.");
                }
                break;
            case "delete":
                //check permissions
                if(!PlayerDB.isCommuneLeader(player)){
                    ChatHelp.sendNoPermissionMessage(player);
                } else {
                    String commune = PlayerDB.getCommune(player);
                    if(!(Commune.deleteCommune(commune) && PlayerDB.removeCommune(player))){
                        ChatHelp.sendBadMessage(player, "Error occurred while running the command.");
                    } else {
                        ChatHelp.sendSuccess(player, "Commune deleted.");
                    }
                }
                break;
        }
    }

    private void processTwoArg(String[] args, Player player){
        int length = args.length;
        if ("c".equals(args[0])) {
            String facName = args[1];
            String desc = "";
            if(length > 3){
                desc = setUpDescription(2, args);
            }
            if(!isValidName(facName)){
                ChatHelp.sendBadMessage(player, "Commune " + facName + " was not created.\nName must be between 3-20 characters & Special characters are not allowed.");
                return;
            }
            readyToCreateCommune(player, facName, desc);
        } else if ("i".equals(args[0])) {
            showCommune(player, args[1]);
        } else if ("inv".equals(args[0])) {
            //check permissions
            invite(player, args);
        } else {
            ChatHelp.sendBadMessage(player, "Command /com " + args[0] + " is not valid.");
        }
    }



    private void invite(Player inviter, String[] args){
        int length = args.length;

        if(length != 2){
            ChatHelp.sendBadMessage(inviter, "Can only invite one player at a time.");
            return;
        }

        String communename = PlayerDB.getCommune(inviter);

        String playername = args[1];

        Bukkit.getPluginManager().callEvent(new OnPlayerInvitedEvent(playername, communename, inviter.getName()));

    }


    private void showCommune(Player player){
        String name = PlayerDB.getCommune(player);

        if(name.equals("")){
            ChatHelp.sendBadMessage(player, "You are not in a Commune.\nTo find other Communes type /com i <communename>");
            return;
        }

        Commune commune = Commune.loadCommune(name);
        if (commune == null){
            ChatHelp.sendBadMessage(player, "Couldn't find Commune named " + name);
            return;
        }
        ChatHelp.sendMessage(player, commune.toString());
    }

    private void showCommune(Player player, String name){
        Commune commune = Commune.loadCommune(name);
        if (commune == null){
            ChatHelp.sendBadMessage(player, "Couldn't find Commune named " + name);
            return;
        }
        ChatHelp.sendMessage(player, commune.toString());
    }

    private void showHelp(Player player, String command){
        ChatHelp.sendHelp(player, "This is the Communed command help, will be updated.");
        switch (command){
            case "c":
                ChatHelp.sendHelp(player, "This command is used for creating a commune use /com c <commune name>");
                break;
            case "":
                break;
        }
    }

    private void readyToCreateCommune(Player player, String facName, String description){
        Commune commune = new Commune(player, facName, description);
        if(PlayerDB.getCommune(player).compareTo("") != 0){
            ChatHelp.sendBadMessage(player, "You are already in a Commune.");
            return;
        }
        if (commune.saveData(facName)) {
            // Save in player the faction
            PlayerDB.addCommune(player, facName);
            ChatHelp.sendSuccess(player, "Commune " + facName + " created!");
        } else {
            ChatHelp.sendBadMessage(player, "Commune " + facName + " was not created.\nUnknown Error.");
        }
    }

    private String setUpDescription(int num, String[] args){
        StringBuffer buffer = new StringBuffer();

        for(int i = num; i < args.length; i++){
            buffer.append(args[i]);
        }

        return buffer.toString();
    }


    private boolean isValidName(String text){
        int length = text.length();

        if(length < 3 || length > 20){
            return false;
        }
        String notAllowed = ".,-´`~^+*ºª<>[](){}=/%";

        for(int i = 0; i < length; i++){
            char ch = text.charAt(i);
            if(notAllowed.contains(Character.toString(ch))){
                return false;
            }
        }
        return true;
    }
}
