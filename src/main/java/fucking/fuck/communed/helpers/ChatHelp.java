package fucking.fuck.communed.helpers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatHelp {

    public static void sendBadMessage(Player player, String text){
        player.sendMessage(ChatColor.RED + text);
    }

    public static void sendHelp(Player player, String text){
        player.sendMessage(ChatColor.YELLOW + text);
    }

    public static void sendSuccess(Player player, String text){
        player.sendMessage(ChatColor.GREEN + text);
    }

    public static void sendMessage(Player player , String text) { player.sendMessage(text); }

    public static void sendNoPermissionMessage(Player player) { sendBadMessage(player, "You do not have permission to use this command."); }

    public static void sendErrorMessage(Player player) { sendBadMessage(player, "Unknown error occurred."); }


    public static void sendNormalMessage(Player player, String communeName, String playerName, String message){
        player.sendMessage(ChatColor.WHITE + "<" + ChatColor.YELLOW + communeName + ChatColor.WHITE + ">" + playerName + ": " + message);
    }

    public static void sendAllyMessage(Player player, String communeName, String playerName, String message, String rolename){
        player.sendMessage(ChatColor.WHITE + "<" + ChatColor.DARK_BLUE + rolename + ">" + "<" + ChatColor.BLUE + communeName + ChatColor.WHITE + ">" + playerName + ": " + message);
    }

    public static void sendEnemyMessage(Player player, String communeName, String playerName, String message){
        player.sendMessage(ChatColor.WHITE + "<" + ChatColor.RED + communeName + ChatColor.WHITE + ">" + playerName + ": " + message);
    }

    public static void sendCommuneMessage(Player player, String communeName, String playerName, String message, String rolename){
        player.sendMessage(ChatColor.WHITE + "<" + ChatColor.GREEN + rolename + ">" + "<" + ChatColor.GREEN + communeName + ChatColor.WHITE + ">" + playerName + ": " + message);
    }
}
