package pl.piotrkociakx.boxpvpcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;

public class FlyCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final FileConfiguration config;

    public FlyCommand(JavaPlugin plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.config = config;
        plugin.getCommand("fly").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatHelper.colored("&cTa komenda może być wykonana tylko przez gracza!"));
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("boxpvpcore.fly")) {
            player.sendMessage(ChatHelper.colored("&cNie posiadasz wymaganych permisji!"));
            return false;
        }

        boolean allowFlying = player.getAllowFlight();
        player.setAllowFlight(!allowFlying);

        if (!allowFlying) {
            player.sendMessage(ChatHelper.colored(config.getString("flycommand.enable-message")));
        } else {
            player.sendMessage(ChatHelper.colored(config.getString("flycommand.disable-message")));
        }

        return true;
    }
}
