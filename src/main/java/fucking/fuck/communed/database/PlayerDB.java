package fucking.fuck.communed.database;


import fucking.fuck.communed.Communed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class PlayerDB {


    public static void addCommune(Player player, String commune_name){
        PersistentDataContainer data = player.getPersistentDataContainer();

        data.set(new NamespacedKey(Communed.getPluginInstance(), "commune"), PersistentDataType.STRING, commune_name);

    }

    public static boolean removeCommune(Player player){
        PersistentDataContainer data = player.getPersistentDataContainer();

        String current = getCommune(data);

        if(current.compareTo("") == 0){
            return false;
        } else {
            data.set(new NamespacedKey(Communed.getPluginInstance(), "commune"), PersistentDataType.STRING, "");
            return true;
        }
    }




    public static String getCommune(Player player){
        PersistentDataContainer data = player.getPersistentDataContainer();

        if(!data.has(new NamespacedKey(Communed.getPluginInstance(), "commune"), PersistentDataType.STRING)){
            return "";
        } else {
            return data.get(new NamespacedKey(Communed.getPluginInstance(), "commune"), PersistentDataType.STRING);
        }

    }

    public static String getCommune(PersistentDataContainer data){

        if(!data.has(new NamespacedKey(Communed.getPluginInstance(), "commune"), PersistentDataType.STRING)){
            return "";
        } else {
            return data.get(new NamespacedKey(Communed.getPluginInstance(), "commune"), PersistentDataType.STRING);

        }

    }


    public static boolean hasCommune(Player player){
        PersistentDataContainer data = player.getPersistentDataContainer();

        if(!data.has(new NamespacedKey(Communed.getPluginInstance(), "commune"), PersistentDataType.STRING)){
            return false;
        } else if (data.get(new NamespacedKey(Communed.getPluginInstance(), "commune"), PersistentDataType.STRING) == ""){
            return false;
        } else {
            return true;
        }
    }

    public static boolean hasCommune(PersistentDataContainer data){

        if(!data.has(new NamespacedKey(Communed.getPluginInstance(), "commune"), PersistentDataType.STRING)){
            return false;
        } else if (data.get(new NamespacedKey(Communed.getPluginInstance(), "commune"), PersistentDataType.STRING) == ""){
            return false;
        } else {
            return true;
        }
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

}
