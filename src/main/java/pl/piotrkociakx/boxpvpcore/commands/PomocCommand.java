package pl.piotrkociakx.boxpvpcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;

import java.util.List;

public class PomocCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final FileConfiguration config;

    public PomocCommand(JavaPlugin plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.config = config;
        plugin.getCommand("pomoc").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda dostępna tylko dla graczy!");
            return true;
        }

        Player player = (Player) sender;

        if (config.contains("HelpCommand.lines")) {
            List<String> helpLines = config.getStringList("HelpCommand.lines");
            for (String line : helpLines) {
                player.sendMessage(ChatHelper.colored(line));
            }
        } else {
            player.sendMessage("Brak konfiguracji dla komendy pomocy!");
        }

        return true;
    }
}
