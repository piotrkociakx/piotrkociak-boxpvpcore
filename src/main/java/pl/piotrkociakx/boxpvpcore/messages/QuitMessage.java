package pl.piotrkociakx.boxpvpcore.messages;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.piotrkociakx.boxpvpcore.conifg.ConfigManager;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;

public class QuitMessage implements Listener {
    private String QuitMessage;

    public QuitMessage(ConfigManager configManager) {
        QuitMessage = configManager.getConfig().getString("welcomemessages.quitmessage");
    }

    @EventHandler
    public void onPlayerQuit(PlayerJoinEvent event) {
        String formattedQuitMessage = QuitMessage.replace("{player}", event.getPlayer().getName());
        formattedQuitMessage = PlaceholderAPI.setPlaceholders(event.getPlayer(), formattedQuitMessage);

        Bukkit.broadcastMessage(ChatHelper.colored(formattedQuitMessage));
    }
}
