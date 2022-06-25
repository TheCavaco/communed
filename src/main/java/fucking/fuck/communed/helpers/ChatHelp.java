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

}
