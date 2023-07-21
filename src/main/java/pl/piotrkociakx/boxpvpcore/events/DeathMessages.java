package pl.piotrkociakx.boxpvpcore.events;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.piotrkociakx.boxpvpcore.ConfigManager;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;

public class DeathMessages implements Listener {
    private String deathMessage;
    private boolean deathMessageEnabled;
    private String deathMessageByPlayer;

    public DeathMessages(ConfigManager configManager) {
        deathMessage = configManager.getConfig().getString("messagees.death.deathmessage");
        deathMessageByPlayer = configManager.getConfig().getString("messagees.death.deathbyplayermessage");
        deathMessageEnabled = configManager.getConfig().getBoolean("settings.enabled.messages.death");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (deathMessageEnabled) {
            Player player = event.getEntity();
            Player killer = player.getKiller();

            String deathMessageFinal;
            if (killer != null) {
                String killerName = killer.getName();
                deathMessageFinal = deathMessageByPlayer.replace("{nickname}", player.getName()).replace("{killer}", killerName);
            } else {
                deathMessageFinal = deathMessage.replace("{nickname}", player.getName());
            }

            // Replace placeholders using PlaceholderAPI
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                deathMessageFinal = PlaceholderAPI.setPlaceholders(player, deathMessageFinal);
            }

            event.setDeathMessage(null); // Disable default Minecraft death message

            String formattedMessage = ChatHelper.colored(deathMessageFinal);
            Bukkit.broadcastMessage(formattedMessage); // Display death message
        }
    }
}
