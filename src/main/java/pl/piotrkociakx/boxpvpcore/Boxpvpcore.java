package pl.piotrkociakx.boxpvpcore;

import org.bukkit.plugin.java.JavaPlugin;
import pl.piotrkociakx.boxpvpcore.events.JoinMessage;
import pl.piotrkociakx.boxpvpcore.events.JoinTitle;
// import pl.piotrkociakx.boxpvpcore.commands.PomocCommand;
import pl.piotrkociakx.boxpvpcore.events.DeathMessages;
import pl.piotrkociakx.boxpvpcore.events.onPlayerQuit;
import pl.piotrkociakx.boxpvpcore.events.inventorydrop;
public final class Boxpvpcore extends JavaPlugin {
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        try {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inicjalizacja i przekazanie ConfigManager do onPlayerJoin
        configManager = new ConfigManager(this);
        getServer().getPluginManager().registerEvents(new JoinMessage(configManager), this);
        getServer().getPluginManager().registerEvents(new JoinTitle(configManager), this);
        getServer().getPluginManager().registerEvents(new DeathMessages(configManager), this);
        getServer().getPluginManager().registerEvents(new onPlayerQuit(configManager), this);
        getServer().getPluginManager().registerEvents(new inventorydrop(configManager), this);
        // new PomocCommand(this, getConfig());
    }
}
