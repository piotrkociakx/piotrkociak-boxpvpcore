package pl.piotrkociakx.boxpvpcore.helpers;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class ChatHelper {

    public static String colored(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    public static List<String> colored(final List<String> texts) {
        return texts.stream().map(ChatHelper::colored).collect(Collectors.toList());
    }
}
