package fucking.fuck.communed.gameobjects;

import fucking.fuck.communed.exceptions.CannotInviteMemberException;
import fucking.fuck.communed.exceptions.CannotRemoveLeaderException;
import fucking.fuck.communed.exceptions.CannotRemoveNonMemberException;
import fucking.fuck.communed.exceptions.NotAllowedException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;


import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Commune implements Serializable {
    private static transient final long serialVersionUID = -1681012206529286330L;

    private final UUID id = UUID.randomUUID();
    private UUID founder;
    private ArrayList<UUID> players;
    private String name;
    private String description;
    private ArrayList<UUID> allies;
    private ArrayList<UUID> enemies;
    private double xp;
    private int level;
    private Set<Chunk> territory;


    public Commune(Player founder, String name, String description){
        UUID founder_uuid = founder.getUniqueId();
        this.founder = founder_uuid;
        this.name = name;
        this.description = description;
        players = new ArrayList<UUID>();
        addPlayer(founder_uuid);
        allies = new ArrayList<>();
        enemies = new ArrayList<>();
        level = 1;
        xp = 0;
        territory = new HashSet<Chunk>();

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
        return deleteCommune(this.id);
    }

    public static Commune getCommuneByName(String name){
        File dir = new File("./plugins/PluginMetrics/communes");

        for(File file: dir.listFiles()){
            String uid = file.getName().split(".")[0];
            Commune commune = loadCommune(UUID.fromString(uid));
            if(commune.getName().equals(name)){
                return commune;
            }
        }
        return null;
    }

    public static boolean deleteCommune(UUID idOfCommune){
        String filePath = idOfCommune.toString();
        File dir = new File("./plugins/PluginMetrics/communes/" + filePath);
        if(!dir.exists()){
            return false;
        }

        for(File file: dir.listFiles()){
            if(!file.delete()){
                return false;
            }
        }
        return dir.delete();
    }


    public boolean saveData() {
        String filePath = id.toString();
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

    public static Commune loadCommune(UUID idOfCommune){
        String filePath = idOfCommune.toString();
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

    public static UUID getNullUUID(){
        return UUID.fromString("17549af4-f5bc-11ec-b939-0242ac120002");
    }

    public static boolean createFile(String path){
        File file = new File(path);
        return !file.exists() && file.mkdirs();
    }

    public void setDescription(String description){
        this.description = description;
    }
    public void setName(String name){

        this.name = name;
    }

    public UUID getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public UUID getFounder(){
        return this.founder;
    }

    public String getDescription() { return this.description; }

    public boolean addAlly(UUID ally){
        if(!allies.contains(ally) && !enemies.contains(ally)){
            return allies.add(ally);
        }
        return false;

    }

    public boolean removeAlly(UUID ally){
        return allies.remove(ally);
    }

    public boolean isAlly(UUID ally) { return allies.contains(ally); }

    public boolean addEnemy(UUID enemy){
        if(!enemies.contains(enemy) && !allies.contains(enemy)){
            Commune enemycom = loadCommune(enemy);
            enemycom.addUnaskedEnemy(this.id);
            return enemies.add(enemy);
        }
        return false;
    }

    private boolean addUnaskedEnemy(UUID id){
        return enemies.add(id);
    }

    private void changeDisplays(String name){
        for(UUID id: players){
            Player player = Bukkit.getOfflinePlayer(id).getPlayer();
            String normal_name = player.getName();
            player.setDisplayName("<" + ChatColor.GREEN + name + ">" + "");
        }
    }

    public boolean removeEnemy(UUID enemy){
        return enemies.remove(enemy);
    }

    public boolean isEnemy(UUID enemy){
        return enemies.contains(enemy);
    }

    public boolean addChunk(Chunk chunk){
        return territory.add(chunk);
    }

    public boolean removeChunk(Chunk chunk){
        return territory.remove(chunk);
    }

    public boolean hasChunk(Chunk chunk){
        return territory.contains(chunk);
    }

    public void giveXP(double xp){
        double value = this.xp + xp;

        if(value >= 100){
            this.xp = value - 100;
            this.level += 1;
        } else {
            this.xp = value;
        }
    }

    public String toString(){
        String separator = "==================================================\n";
        String info = ChatColor.YELLOW + "Commune name: " + ChatColor.GREEN + this.name + "\n" +
                ChatColor.YELLOW + "Description: " + ChatColor.GREEN + this.description + "\n" +
                ChatColor.YELLOW + "Founder: " + ChatColor.GREEN + Bukkit.getOfflinePlayer(founder).getName() + "\n" +
                ChatColor.YELLOW + "Level: " + ChatColor.GREEN + level + "\n" +
                ChatColor.YELLOW + "Xp:" + ChatColor.GREEN + xpToString() + "\n";

        return separator + info + separator;
    }

    private String xpToString(){
        int newval = ((int)xp) / 2;
        StringBuilder buffer = new StringBuilder();
        for(double i = 0; i < newval; i++){
            buffer.append("=");
        }
        return buffer.toString();
    }
}
