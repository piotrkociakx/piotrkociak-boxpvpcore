package pl.piotrkociakx.boxpvpcore;

import org.bukkit.plugin.java.JavaPlugin;
import pl.piotrkociakx.boxpvpcore.conifg.ConfigManager;
import pl.piotrkociakx.boxpvpcore.messages.*;
import pl.piotrkociakx.boxpvpcore.commands.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public final class Boxpvpcore extends JavaPlugin {
    private ConfigManager configManager;
    private Map<UUID, UUID> lastMessageFrom;

    @Override
    public void onEnable() {
        try {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

        lastMessageFrom = new HashMap<>();
        configManager = new ConfigManager(this);

        // funkcje wiadomosci
        if(getConfig().getBoolean("welcomemessages.enable-join-messages")) {
            getLogger().info("[+] Pomyslnie wczytano: wiadomosci powitalne");
            getServer().getPluginManager().registerEvents(new JoinMessage(configManager), this);
        } else {
            getLogger().info("[-] Nie mozna zarejstrowac: wiadomosci powitalnych (mozliwe ze one sa wylaczone w konfiguracji)");
            return;
        } // wiadomosci powitalne
        if(getConfig().getBoolean("jointitle.enabled")) {
            getLogger().info("[+] Pomyslnie wczytano: tytul wejsciowy");
            getServer().getPluginManager().registerEvents(new JoinTitle(configManager), this);
        } else {
            getLogger().info("[-] Nie mozna zarejstrowac: tytulu wejsciowego (mozliwe ze jest on wylaczony w konfiguracji)");
            return;
        } // tytul po wejsciu
        if(getConfig().getBoolean("deathmessages.enabled")) {
            getLogger().info("[+] Pomyslnie wczytano: wiadomosci o smierci");
            getServer().getPluginManager().registerEvents(new DeathMessages(configManager), this);
        } else {
            getLogger().info("[-] Nie mozna zarejstrowac: wiadomosci smierci (mozliwe ze sa one wylaczone w konfiguracji)");
            return;
        } // wiadomosci smierci
        if(getConfig().getBoolean("welcomemessages.enable-quit-messages")) {
            getLogger().info("[+] Pomyslnie wczytano: wiadomosci pozegnalne");
            getServer().getPluginManager().registerEvents(new QuitMessage(configManager), this);
        } else {
            getLogger().info("[-] Nie mozna zarejstrowac: wiadomosci pozegnalnych (mozliwe ze sa one wylaczone w konfiguracji)");
            return;
        } // wiadomosci wyjsica graczy

        // komendy
        if(getConfig().getBoolean("HelpCommand.enabled")) {
            getLogger().info("[+] Pomyslnie wczytano komende: pomoc");
            new PomocCommand(this, getConfig());
        } else {
            getLogger().info("[-] Nie mozna zarejstrowac komendy: pomoc (mozliwe ze jest ona wylaczona w konfiguracji)");
            return;
        } // komenda /pomoc
        if(getConfig().getBoolean("msgcommnad.enabled")){
            new MsgCommand(this, getConfig());
            new ReplyCommand(this, getConfig(), lastMessageFrom);
            getLogger().info("[+] Pomyslnie wczytano komendy: msg, reply");
        } else {
            getLogger().info("[-] Nie mozna zarejstrowac komend: msg, reply (mozliwe ze one sa wylaczone w konfiguracji)");
            return;
        } // komendy /msg oraz /reply
        if(getConfig().getBoolean("vanish.enabled")) {
            new VanishCommand(this, getConfig());
            getLogger().info("[+] Pomyslnie wczytano komende: vanish");
        } else {
            getLogger().info("[-] Nie mozna zarejstrowac komendy: vanish (mozliwe ze jest ona wylaczona w konfiguracji)");
            return;
        } // komenda /vanish
        if(getConfig().getBoolean("helpop.enabled")) {
            new HelpopCommand(this, getConfig());
            getLogger().info("[+] Pomyslnie wczytano komende: helpop");
        } else {
            getLogger().info("[-] Nie mozna zarejstrowac komendy: helpop (mozliwe ze jest ona wylaczona w konfiguracji)");
            return;
        } // komenda /helpop
        if(getConfig().getBoolean("flycommand.enabled")) {
            new FlyCommand(this, getConfig());
            getLogger().info("[+] Pomyslnie wczytano komende: fly");
        } else {
            getLogger().info("[-] Nie mozna zarejstrowac komendy: fly (mozliwe ze jest ona wylaczona w konfiguracji)");
            return;
        } // komenda /fly
        if(getConfig().getBoolean("gamemodecommand.enabled")) {
            getLogger().info("[+] Pomyslnie wczytano komende: gamemode");
            new GamemodeCommand(this, getConfig());
        } else {
            getLogger().info("[-] Nie mozna zarejstrowac komendy: gamemode (mozliwe ze jest ona wylaczona w konfiguracji)");
            return;
        } // komenda /gamemode
    }
}
