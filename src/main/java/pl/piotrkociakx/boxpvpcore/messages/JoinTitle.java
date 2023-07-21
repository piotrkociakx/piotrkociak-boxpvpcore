package pl.piotrkociakx.boxpvpcore.messages;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.piotrkociakx.boxpvpcore.conifg.ConfigManager;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;
import org.bukkit.entity.Player;

public class JoinTitle implements Listener {
    private String JoinTitleText;
    private String JoinSubtitleText;

    public JoinTitle(ConfigManager configManager) {
        JoinTitleText = configManager.getConfig().getString("jointitle.title");
        JoinSubtitleText = configManager.getConfig().getString("jointitle.subtitle");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
            Player player = event.getPlayer();
            String titleText = ChatHelper.colored(JoinTitleText.replace("{player}", player.getName()));
            String subtitleText = ChatHelper.colored(JoinSubtitleText.replace("{player}", player.getName()));
            titleText = PlaceholderAPI.setPlaceholders(player, titleText);
            subtitleText = PlaceholderAPI.setPlaceholders(player, subtitleText);

            player.sendTitle(titleText, subtitleText, 10, 70, 20);
    }
}
