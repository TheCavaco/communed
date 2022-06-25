package fucking.fuck.communed.gameobjects;

import fucking.fuck.communed.exceptions.CannotInviteMemberException;
import fucking.fuck.communed.exceptions.CannotRemoveLeaderException;
import fucking.fuck.communed.exceptions.CannotRemoveNonMemberException;
import fucking.fuck.communed.exceptions.NotAllowedException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;


import java.io.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Commune implements Serializable {
    private static transient final long serialVersionUID = -1681012206529286330L;

    private final UUID id = UUID.randomUUID();
    private UUID founder;
    private ArrayList<UUID> players;
    private String name;
    private String description;
    private ArrayList<Commune> allies;
    private ArrayList<Commune> enemies;


    public Commune(Player founder, String name, String description){
        UUID founder_uuid = founder.getUniqueId();
        this.founder = founder_uuid;
        this.name = name;
        this.description = description;
        players = new ArrayList<UUID>();
        addPlayer(founder_uuid);

    }

    private void addPlayer(UUID player){
        players.add(player);
    }

    public boolean addPlayer(Player player){
        UUID uuid = player.getUniqueId();
        if(!(players.contains(uuid))){
            players.add(player.getUniqueId());
            return true;
        } else {
            return false;
        }

    }

    public void invitePlayer(Player inviter, Player player) throws NotAllowedException, CannotInviteMemberException {
        if(founder.compareTo(inviter.getUniqueId()) != 0){
            throw new NotAllowedException("You are not your Commune leader.");
        }

        // TODO: Implement the exceptions and search if the invited player has a commune or not
        UUID uuid = player.getUniqueId();
        if(!(players.contains(uuid))){
            players.add(player.getUniqueId());
            return;
        } else {
            throw new CannotInviteMemberException(player.getName() + " is a member of the Commune.");
        }

    }

    public void removePlayer(Player player) throws CannotRemoveLeaderException, CannotRemoveNonMemberException {
        UUID uuid = player.getUniqueId();
        if(players.contains(uuid) && founder.compareTo(uuid) != 0){
            players.remove(player.getUniqueId());
            return;
        } else if (players.contains(uuid) && founder.compareTo(uuid) == 0) {
            throw new CannotRemoveLeaderException("Leader cannot be removed.");
        } else if (!players.contains(uuid)){
            throw new CannotRemoveNonMemberException("Cannot remove member outside the Commune.");
        }
    }

    public boolean delete(){
        return deleteCommune(this.getName());
    }


    public static boolean deleteCommune(String filePath){
        File dir = new File("./plugins/PluginMetrics/communes/" + filePath);
        if(!dir.exists()){
            Bukkit.broadcastMessage("First if case");
            return false;
        }

        for(File file: dir.listFiles()){
            if(!file.delete()){
                Bukkit.broadcastMessage("Second if case");
                return false;
            }
        }

        if(dir.delete()){
            return true;
        } else {
            Bukkit.broadcastMessage("Third if case");
            return false;
        }
    }


    public boolean saveData(String filePath) {
        // filepath format : Commune_facName.dat
        File dir = new File("./plugins/PluginMetrics/communes/" + filePath);

        if(!dir.exists()){
            dir.mkdir();
        }
        filePath = "./plugins/PluginMetrics/communes/" + filePath + "/" + filePath + ".data";

        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error saving the Commune");
            e.printStackTrace();
            return false;
        }
    }

    public static Commune loadCommune(String filePath){
        // filepath format : Commune_facName.data
        filePath = "./plugins/PluginMetrics/communes/" + filePath + "/" + filePath + ".data";
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            Commune com = (Commune) in.readObject();
            in.close();
            return com;
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Error retrieving the Commune");
            e.printStackTrace();
            return null;
        }
    }

    public static boolean createFile(String path){
        File file = new File(path);
        return !file.exists() && file.mkdirs();
    }

    public String getName(){
        return this.name;
    }

    public UUID getFounder(){
        return this.founder;
    }


    public String toString(){
        String separator = "==================================================\n";
        String info = ChatColor.YELLOW + "Faction name: " + ChatColor.GREEN + this.name + "\n" +
                ChatColor.YELLOW + "Description: " + ChatColor.GREEN + this.description + "\n" +
                ChatColor.YELLOW + "Founder" + ChatColor.GREEN + Bukkit.getOfflinePlayer(founder).getName();

        return separator + info + separator;
    }
}
