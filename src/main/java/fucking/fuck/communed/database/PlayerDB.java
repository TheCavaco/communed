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

}
