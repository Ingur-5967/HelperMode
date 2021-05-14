package ru.solomka;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.solomka.commands.handler.*;
import ru.solomka.commands.util.events.JoinChat;
import ru.solomka.commands.util.help.HelpHandler;
import ru.solomka.config.ConfigManager;
import ru.solomka.config.handler.ReloadConfig;

import java.util.HashSet;
import java.util.UUID;

import static ru.solomka.config.ConfigManager.createConfig;

public class Main extends JavaPlugin {

    private static Main instance;
    @Getter @Setter private static ConfigManager plugin;

    public static HashSet<UUID> join = new HashSet<>();
    public static HashSet<UUID> map = new HashSet<>();
    public static HashSet<UUID> chat = new HashSet<>();
    public static HashSet<UUID> haveQuestion = new HashSet<>();
    public static HashSet<UUID> haveBan = new HashSet<>();

    public static Main getInstance() {
        return instance;
    }

    public void onEnable(){
        instance = this;
        Bukkit.getPluginManager().registerEvents(new JoinChat(this), this);
        getLogger().info("HelperMode enable!");
        createConfig();
        registerCommand();
    }

    public void registerCommand() {
        new QuestionAnswerHandler().registerHandler(this, "answer");
        new QuestionSendHandler().registerHandler(this, "question");
        new HelperHandler().registerHandler(this, "helper");
        new BlackListHelperHandler().registerHandler(this, "blacklist-helper");
        new BlackListPlayerHandler().registerHandler(this, "blacklist-player");
        new HelpHandler().registerHandler(this, "helpList");
        new ChatHandler().registerHandler(this, "helper-chat");
        new BanQuestionHandler().registerHandler(this, "ban-question");
        new ReloadConfig().registerHandler(this, "helper-reload");
    }

    public void onDisable(){
        getLogger().info("HelperMode disable!");
    }

}
