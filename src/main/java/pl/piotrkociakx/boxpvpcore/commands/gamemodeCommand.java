package pl.piotrkociakx.boxpvpcore.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;

public class gamemodeCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final FileConfiguration config;

    public gamemodeCommand(JavaPlugin plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.config = config;
        plugin.getCommand("gamemode").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda dostępna tylko dla graczy!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("boxpvpcore.gamemode")) {
            player.sendMessage(ChatHelper.colored("&cNie posiadasz wymaganych permisji!"));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatHelper.colored("&cUżycie: /gamemode <tryb>"));
            return true;
        }

        GameMode gameMode;
        switch (args[0].toLowerCase()) {
            case "0":
            case "survival":
                gameMode = GameMode.SURVIVAL;
                break;
            case "1":
            case "creative":
                gameMode = GameMode.CREATIVE;
                break;
            case "2":
            case "adventure":
                gameMode = GameMode.ADVENTURE;
                break;
            case "3":
            case "spectator":
                gameMode = GameMode.SPECTATOR;
                break;
            default:
                player.sendMessage(ChatHelper.colored(config.getString("gamemode.failed")));
                return true;
        }

        player.setGameMode(gameMode);
        player.sendMessage(ChatHelper.colored(config.getString("gamemode.success") + gameMode.toString()));
        return true;
    }
}

