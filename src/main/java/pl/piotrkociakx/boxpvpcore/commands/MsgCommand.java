package pl.piotrkociakx.boxpvpcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.piotrkociakx.boxpvpcore.helpers.ChatHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MsgCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final FileConfiguration config;
    private final Map<UUID, UUID> lastMessageFrom = new HashMap<>();
    private String playerNotFoundMessage;

    public MsgCommand(JavaPlugin plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.config = config;

        this.playerNotFoundMessage = ChatHelper.colored(config.getString("msgcommnad.playerNotFoundMessage", "&cNie można znaleźć gracza o nazwie {player}"));

        plugin.getCommand("msg").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatHelper.colored("&cKomenda dostępna tylko dla graczy!"));
            return true;
        }

        Player senderPlayer = (Player) sender;

        if (args.length < 2) {
            senderPlayer.sendMessage(ChatHelper.colored("&cUżycie: /msg <gracz> <wiadomosc>"));
            return true;
        }

        Player targetPlayer = plugin.getServer().getPlayer(args[0]);
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            senderPlayer.sendMessage(playerNotFoundMessage.replace("{player}", args[0]));
            return true;
        }

        UUID targetUUID = targetPlayer.getUniqueId();
        String message = String.join(" ", args).substring(args[0].length() + 1);

        boolean coloredMessageAllowed = senderPlayer.hasPermission("boxpvpcore.msg.colored");

        String senderName = senderPlayer.getName();
        String formattedMessage = coloredMessageAllowed ? ChatHelper.colored(message) : ChatHelper.colored(message);
        targetPlayer.sendMessage(ChatHelper.colored("&7[&aOd " + senderName + "&7] " + formattedMessage));

        lastMessageFrom.put(targetUUID, senderPlayer.getUniqueId());

        senderPlayer.sendMessage(ChatHelper.colored("&7[&aDo " + targetPlayer.getName() + "&7] " + formattedMessage));
        return true;
    }
}
