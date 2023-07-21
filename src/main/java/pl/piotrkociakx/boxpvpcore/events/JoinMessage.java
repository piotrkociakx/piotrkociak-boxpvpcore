package pl.piotrkociakx.boxpvpcore.events;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.piotrkociakx.boxpvpcore.ConfigManager;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;
import org.bukkit.entity.Player;

public class JoinMessage implements Listener {
    private String JoinMessage;
    private String newplayerjoinmessage;
    private boolean joinmessageEnabled;

    public JoinMessage(ConfigManager configManager) {
        JoinMessage = configManager.getConfig().getString("messagees.welcomes.joinmessage");
        joinmessageEnabled = configManager.getBoolean("settings.enabled.messages.welcomes");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (joinmessageEnabled) {
            // wiadomosc dolaczania
            String formattedJoinMessage = JoinMessage.replace("{nickname}", event.getPlayer().getName());

            // PlaceholderAPI
            formattedJoinMessage = PlaceholderAPI.setPlaceholders(event.getPlayer(), formattedJoinMessage);

            Bukkit.broadcastMessage(ChatHelper.colored(formattedJoinMessage));
        } else {
            return;
        }
    }
}
