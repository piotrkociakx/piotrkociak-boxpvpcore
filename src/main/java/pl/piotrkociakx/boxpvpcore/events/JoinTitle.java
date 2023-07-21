package pl.piotrkociakx.boxpvpcore.events;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.piotrkociakx.boxpvpcore.ConfigManager;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;
import org.bukkit.entity.Player;

public class JoinTitle implements Listener {
    private String JoinTitleText;
    private String JoinSubtitleText;
    private boolean jointitleEnabled;

    public JoinTitle(ConfigManager configManager) {
        JoinTitleText = configManager.getConfig().getString("jointitle.titletext", "&5Witaj Na twojeip.pl!");
        JoinSubtitleText = configManager.getConfig().getString("jointitle.subtitle", "&6/pomoc");
        jointitleEnabled = configManager.getBoolean("settings.enabled.jointitle.enable");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (jointitleEnabled) {
            // title dla gracza na wejscie
            Player player = event.getPlayer();
            String titleText = ChatHelper.colored(JoinTitleText.replace("{nickname}", player.getName()));
            String subtitleText = ChatHelper.colored(JoinSubtitleText.replace("{nickname}", player.getName()));

            // PlaceholderAPI
            titleText = PlaceholderAPI.setPlaceholders(player, titleText);
            subtitleText = PlaceholderAPI.setPlaceholders(player, subtitleText);

            player.sendTitle(titleText, subtitleText, 10, 70, 20);
        } else {
            return;
        }
    }
}
