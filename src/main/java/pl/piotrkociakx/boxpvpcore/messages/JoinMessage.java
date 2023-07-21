package pl.piotrkociakx.boxpvpcore.messages;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.piotrkociakx.boxpvpcore.conifg.ConfigManager;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;

public class JoinMessage implements Listener {
    private String JoinMessage;
    private String newplayerjoinmessage;
    private boolean joinmessageEnabled;

    public JoinMessage(ConfigManager configManager) {
        JoinMessage = configManager.getConfig().getString("welcomemessages.joinmessage");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // wiadomosc dolaczania
        String formattedJoinMessage = JoinMessage.replace("{player}", event.getPlayer().getName());

        // PlaceholderAPI
        formattedJoinMessage = PlaceholderAPI.setPlaceholders(event.getPlayer(), formattedJoinMessage);
        event.setJoinMessage(null);
        Bukkit.broadcastMessage(ChatHelper.colored(formattedJoinMessage));
    }
}
