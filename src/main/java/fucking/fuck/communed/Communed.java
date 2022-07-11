package fucking.fuck.communed;


import fucking.fuck.communed.commands.CommuneCommand;
import fucking.fuck.communed.gameobjects.Commune;
import fucking.fuck.communed.listeners.ChatListener;
import fucking.fuck.communed.listeners.CommuneListener;
import fucking.fuck.communed.listeners.InviteListener;
import fucking.fuck.communed.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Communed extends JavaPlugin {


    private static Communed plugin_instance;
    private ArrayList<Listener> listeners = new ArrayList<Listener>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Commune.createFile("./plugins/PluginMetrics/communes");
        plugin_instance = this;
        setUpCommands();
        setUpListeners();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        disableListeners();
    }



    private void setUpCommands(){
        getCommand("com").setExecutor(new CommuneCommand());
    }

    private void setUpListeners(){
        listeners.add(new PlayerListener());
        listeners.add(new InviteListener());
        listeners.add(new CommuneListener());
        listeners.add(new ChatListener());

        for(Listener listener: listeners){
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void disableListeners(){
        for(Listener listener: listeners){
            HandlerList.unregisterAll(listener);
        }
    }


    public static Communed getPluginInstance(){
        return plugin_instance;
    }


}
