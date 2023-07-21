package pl.piotrkociakx.boxpvpcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;

import java.util.HashSet;
import java.util.Set;

public class VanishCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final FileConfiguration config;
    private final Set<Player> vanishedPlayers;

    public VanishCommand(JavaPlugin plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.config = config;
        this.vanishedPlayers = new HashSet<>();
        plugin.getCommand("vanish").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatHelper.colored("&cKomenda dostępna tylko dla graczy!"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("boxpvpcore.vanish")) {
            player.sendMessage(ChatHelper.colored("&cNie posiadasz wymaganych permisji do użycia tej komendy!"));
            return true;
        }

        if (vanishedPlayers.contains(player)) {
            vanishedPlayers.remove(player);
            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                onlinePlayer.showPlayer(plugin, player);
            }
            player.sendMessage(ChatHelper.colored(config.getString("vanish.disabled")));
        } else {
            vanishedPlayers.add(player);
            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission("boxpvpcore.vanish")) {
                    onlinePlayer.hidePlayer(plugin, player);
                }
            }
            player.sendMessage(ChatHelper.colored(config.getString("vanish.enabled")));
        }

        return true;
    }
}
