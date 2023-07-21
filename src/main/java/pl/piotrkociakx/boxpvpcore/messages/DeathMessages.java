package pl.piotrkociakx.boxpvpcore.messages;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.piotrkociakx.boxpvpcore.conifg.ConfigManager;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;

public class DeathMessages implements Listener {
    private String deathMessage;
    private String deathMessageByPlayer;

    public DeathMessages(ConfigManager configManager) {
        deathMessage = configManager.getConfig().getString("deathmessages.deathmessage");
        deathMessageByPlayer = configManager.getConfig().getString("deathmessages.deathbyplayermessage");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        String deathMessageFinal;
        if (killer != null) {
            String killerName = killer.getName();
            deathMessageFinal = deathMessageByPlayer.replace("{player}", player.getName()).replace("{killer}", killerName);
        } else {
            deathMessageFinal = deathMessage.replace("{player}", player.getName());
        }
        deathMessageFinal = PlaceholderAPI.setPlaceholders(player, deathMessageFinal);
        event.setDeathMessage(null);
        String formattedMessage = ChatHelper.colored(deathMessageFinal);
        Bukkit.broadcastMessage(formattedMessage);
    }
}
