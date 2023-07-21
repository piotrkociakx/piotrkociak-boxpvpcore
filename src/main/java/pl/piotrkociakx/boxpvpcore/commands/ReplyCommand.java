package pl.piotrkociakx.boxpvpcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;

import java.util.Map;
import java.util.UUID;

public class ReplyCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final FileConfiguration config;
    private final Map<UUID, UUID> lastMessageFrom;
    private String playerNotFoundMessage;

    public ReplyCommand(JavaPlugin plugin, FileConfiguration config, Map<UUID, UUID> lastMessageFrom) {
        this.plugin = plugin;
        this.config = config;
        this.lastMessageFrom = lastMessageFrom;

        // Load configurable message from the configuration
        this.playerNotFoundMessage = ChatHelper.colored(config.getString("msgcommnad.ReplyPlayerNotFound"));

        plugin.getCommand("reply").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatHelper.colored("&cKomenda dostępna tylko dla graczy!"));
            return true;
        }

        Player senderPlayer = (Player) sender;

        // Checking if the sender received any messages previously
        UUID lastMessageUUID = lastMessageFrom.get(senderPlayer.getUniqueId());
        if (lastMessageUUID == null) {
            senderPlayer.sendMessage(playerNotFoundMessage);
            return true;
        }

        Player targetPlayer = plugin.getServer().getPlayer(lastMessageUUID);
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            senderPlayer.sendMessage(playerNotFoundMessage);
            return true;
        }

        String message = String.join(" ", args);
        boolean coloredMessageAllowed = senderPlayer.hasPermission("boxpvpcore.msg.colored");

        // Sending the reply message with color support if allowed
        String senderName = senderPlayer.getName();
        String formattedMessage = coloredMessageAllowed ? ChatHelper.colored(message) : ChatHelper.colored(message);
        targetPlayer.sendMessage(ChatHelper.colored("&7[&aOd " + senderName + "&7] " + formattedMessage));

        // Confirmation message for sender
        senderPlayer.sendMessage(ChatHelper.colored("&7[&aDo " + targetPlayer.getName() + "&7] " + formattedMessage));
        return true;
    }
}
