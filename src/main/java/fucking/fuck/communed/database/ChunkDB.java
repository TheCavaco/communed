package fucking.fuck.communed.database;

import fucking.fuck.communed.Communed;
import fucking.fuck.communed.datatypes.UUIDDataType;
import fucking.fuck.communed.gameobjects.Commune;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.UUID;

public class ChunkDB {


    public static boolean addClaimer(Chunk chunk, UUID id){
        PersistentDataContainer data = chunk.getPersistentDataContainer();

        if(data.has(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType())){
            UUID current = data.get(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType());
            if(current.equals(Commune.getNullUUID())){
                data.set(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType(), id);
                return true;
            } else {
                return false;
            }
        } else {
            data.set(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType(), id);
            return true;
        }
    }

    public static void removeClaimer(Chunk chunk){
        PersistentDataContainer data = chunk.getPersistentDataContainer();
        data.set(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType(), Commune.getNullUUID());
    }

    public static void changeClaimer(Chunk chunk, UUID id){
        PersistentDataContainer data = chunk.getPersistentDataContainer();
        data.set(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType(), id);
    }

    public static UUID getClaimer(Chunk chunk){
        PersistentDataContainer data = chunk.getPersistentDataContainer();
        if(!data.has(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType())) {
            return Commune.getNullUUID();
        }
        return data.get(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType());
    }

    public static boolean hasClaimer(Chunk chunk){
        PersistentDataContainer data = chunk.getPersistentDataContainer();
        if(!data.has(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType())){
            return false;
        }
        return !data.get(new NamespacedKey(Communed.getPluginInstance(), "commune"), new UUIDDataType()).equals(Commune.getNullUUID());
    }

}
