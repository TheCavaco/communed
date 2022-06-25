package fucking.fuck.communed;


import fucking.fuck.communed.commands.CommuneCommand;
import fucking.fuck.communed.gameobjects.Commune;
import fucking.fuck.communed.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Communed extends JavaPlugin {


    private static Communed plugin_instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Commune.createFile("./plugins/PluginMetrics/communes");
        plugin_instance = this;
        getCommand("com").setExecutor(new CommuneCommand());
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }




    public static Communed getPluginInstance(){
        return plugin_instance;
    }


}
