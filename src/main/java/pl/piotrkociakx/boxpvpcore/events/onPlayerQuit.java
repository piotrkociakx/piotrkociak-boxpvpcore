package pl.piotrkociakx.boxpvpcore.events;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.piotrkociakx.boxpvpcore.ConfigManager;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;

public class onPlayerQuit implements Listener {
    private String QuitMessage;
    private boolean QuitmessageEnabled;

    public onPlayerQuit(ConfigManager configManager) {
        QuitMessage = configManager.getConfig().getString("messagees.welcomes.exitmessage");
        QuitmessageEnabled = configManager.getBoolean("settings.enabled.messages.quits");
    }

    @EventHandler
    public void onPlayerQuit(PlayerJoinEvent event) {
        if (QuitmessageEnabled) {
            String formattedQuitMessage = QuitMessage.replace("{nickname}", event.getPlayer().getName());
            formattedQuitMessage = PlaceholderAPI.setPlaceholders(event.getPlayer(), formattedQuitMessage);

            Bukkit.broadcastMessage(ChatHelper.colored(formattedQuitMessage));
        } else {
            return;
        }
    }
}
