package fucking.fuck.communed.database;


import fucking.fuck.communed.Communed;
import fucking.fuck.communed.datatypes.UUIDDataType;
import fucking.fuck.communed.exceptions.NoRelationException;
import fucking.fuck.communed.exceptions.SamePlayerException;
import fucking.fuck.communed.gameobjects.Commune;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;


public class PlayerDB {


    public static void addCommune(Player player, UUID commune_id){
        PersistentDataContainer data = player.getPersistentDataContainer();

        data.set(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType(), commune_id);

    }

    public static boolean removeCommune(Player player){
        PersistentDataContainer data = player.getPersistentDataContainer();

        UUID id = Commune.getNullUUID();

        if(data.has(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType())){
            data.set(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType(), id);
            return true;
        }
        return false;
    }

    public static boolean removeOldCommune(Player player){
        PersistentDataContainer data = player.getPersistentDataContainer();

        UUID id = Commune.getNullUUID();

        if(data.has(new NamespacedKey(Communed.getPluginInstance(), "commune"), PersistentDataType.STRING)){
            data.remove(new NamespacedKey(Communed.getPluginInstance(), "commune"));
            data.set(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType(), id);
            return true;
        }
        return false;
    }





    public static UUID getCommune(Player player){
        PersistentDataContainer data = player.getPersistentDataContainer();

        if(!data.has(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType())){
            return Commune.getNullUUID();
        } else {
            return data.get(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType());
        }

    }

    public static UUID getCommune(PersistentDataContainer data){

        if(!data.has(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType())){
            return Commune.getNullUUID();
        } else {
            return data.get(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType());

        }

    }


    public static boolean hasCommune(Player player){
        PersistentDataContainer data = player.getPersistentDataContainer();

        if(!data.has(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType())){
            return false;
        } else if (data.get(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType()).equals(Commune.getNullUUID())){
            return false;
        } else {
            return true;
        }
    }

    public static boolean hasCommune(PersistentDataContainer data){

        if(!data.has(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType())){
            return false;
        } else if (data.get(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType()).equals(Commune.getNullUUID())){
            return false;
        } else {
            return true;
        }
    }

    public static boolean isCommuneLeader(Player player){
        PersistentDataContainer data = player.getPersistentDataContainer();
        UUID communeid = data.get(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType());

        Commune commune = Commune.loadCommune(communeid);

        return commune.getFounder().equals(player.getUniqueId());
    }


    public static boolean isInvited(Player player){
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Communed.getPluginInstance(), "invited");
        PersistentDataType type = PersistentDataType.STRING;

        return data.has(key, type) && data.get(key, type) != "";
    }

    public static boolean isInvited(PersistentDataContainer data){
        NamespacedKey key = new NamespacedKey(Communed.getPluginInstance(), "invited");
        PersistentDataType type = PersistentDataType.STRING;

        return data.has(key, type) && data.get(key, type) != "";
    }



    public static void setInvited(Player player, String communeName){
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Communed.getPluginInstance(), "invited");
        PersistentDataType type = PersistentDataType.STRING;

        data.set(key, type, communeName);
    }

    public static void setInvited(PersistentDataContainer data, String communeName){
        NamespacedKey key = new NamespacedKey(Communed.getPluginInstance(), "invited");
        PersistentDataType type = PersistentDataType.STRING;

        data.set(key, type, communeName);
    }


    public static String getInvited(Player player){
        PersistentDataContainer data = player.getPersistentDataContainer();

        return data.get(new NamespacedKey(Communed.getPluginInstance(), "invited"), PersistentDataType.STRING);
    }

    public static String getInvited(PersistentDataContainer data){
        return data.get(new NamespacedKey(Communed.getPluginInstance(), "invited"), PersistentDataType.STRING);
    }

    public static String getCommuneName(Player player){
        if(!hasCommune(player)){
            return "";
        }
        return Commune.loadCommune(getCommune(player)).getName();
    }


    public static void displayCommune(Player player, Commune commune){

        if(hasCommune(player)){
            UUID id = getCommune(player);
            if(commune.isEnemy(id)){
                player.sendTitle(ChatColor.RED + commune.getName(), commune.getDescription(), 10, 50, 20);
            } else if (commune.isAlly(id)){
                player.sendTitle(ChatColor.BLUE + commune.getName(), commune.getDescription(), 10, 50, 20);
            } else if (commune.getId().equals(id)){
                player.sendTitle(ChatColor.GREEN + commune.getName(), commune.getDescription(), 10, 50, 20);
            } else {
                player.sendTitle(commune.getName(), commune.getDescription(), 10, 50, 20);
            }
        } else {
            player.sendTitle(commune.getName(), commune.getDescription(), 10, 50, 20);
        }
    }

    //True is ally
    //false is enemy
    public static boolean areRelated(Player player, Player otherPlayer) throws NoRelationException, SamePlayerException {

        if(player.getName().equals(otherPlayer.getName())){
            throw new SamePlayerException("This is the same player");
        }

        if(!hasCommune(player) || !hasCommune(otherPlayer)){
            throw new NoRelationException("Has no commune");
        }

        Commune commune = Commune.loadCommune(getCommune(player));
        UUID other = getCommune(otherPlayer);
        if(commune.isAlly(other)){
            return true;
        } else if (commune.isEnemy(other)) {
            return false;
        } else {
            throw new NoRelationException("No relation between communes.");
        }


    }

}
