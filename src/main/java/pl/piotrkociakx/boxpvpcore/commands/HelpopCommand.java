package pl.piotrkociakx.boxpvpcore.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;
import pl.piotrkociakx.boxpvpcore.helpers.WebhookHelper;

public class HelpopCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private WebhookHelper webhookHelper;
    private final FileConfiguration config;

    public HelpopCommand(JavaPlugin plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.config = config;
        plugin.getCommand("helpop").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda dostępna tylko dla graczy!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatHelper.colored(config.getString("helpop.message_invalid_usage")));
            return false;
        }

        String message = String.join(" ", args);
        sendHelpOpMessage(player, message);
        String successMessage = config.getString("helpop.message_successful_sended");
        successMessage = successMessage.replace("{player}", player.getName());
        successMessage = successMessage.replace("{message}", message);
        player.sendMessage(ChatHelper.colored(successMessage));
        return true;
    }

    private void sendHelpOpMessage(Player sender, String message) {
        String adminMessage = config.getString("helpop.message_admin");
        adminMessage = adminMessage.replace("{player}", sender.getName());
        adminMessage = adminMessage.replace("{message}", message);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("boxpvpcore.helpop.admin")) {
                // PlaceholderAPI
                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                    adminMessage = PlaceholderAPI.setPlaceholders(player, adminMessage);
                }
                player.sendMessage(ChatHelper.colored(adminMessage));
                if(config.getBoolean("helpop.title.enable")){
                    player.sendTitle(ChatHelper.colored(config.getString("helpop.title.title")), ChatHelper.colored(config.getString("helpop.title.subtitle")), 5, 35, 10);
                } else {
                    return;
                }
                if (config.getBoolean("helpop.sound.enable")) {
                    String soundName = config.getString("helpop.sound.soundName", "BLOCK_ANVIL_LAND");

                    try {
                        Sound sound = Sound.valueOf(soundName);
                        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
                    } catch (IllegalArgumentException e) {
                        Bukkit.getLogger().warning("Nieprawidłowa nazwa dźwięku w konfiguracji: " + soundName);
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                    }
                }

                if (config.getBoolean("helpop.webhook.enabled")) {
                    WebhookHelper webhookHelper = new WebhookHelper(config.getString("helpop.webhook.webhook-url"));
                    webhookHelper.sendEmbed(
                        config.getString("helpop.webhook.embed.title"),
                        config.getString("helpop.webhook.embed.description").replace("{player}", sender.getName()).replace("{message}", message),
                        config.getString("helpop.webhook.embed.color")
                    );
                } else {
                    return;
                }
            }
        }
    }
}

