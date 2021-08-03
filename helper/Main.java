package ru.solomka.helper;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.solomka.helper.commands.*;
import ru.solomka.helper.config.ConfigManager;
import ru.solomka.helper.database.DatabaseHandler;
import ru.solomka.helper.events.JoinPlayer;

import java.sql.SQLException;

import static ru.solomka.helper.config.ConfigManager.createConfig;

public class Main extends JavaPlugin {

    @Getter private static Main instance;

    @Getter @Setter private static ConfigManager plugin;

    @SneakyThrows
    public void onEnable(){
        instance = this;
        getDataFolder().mkdir();
        getLogger().info("HelperMode enable!");
        createConfig();
        registrationAll();
        new DatabaseHandler().createTable();
    }

    public void registrationAll() throws SQLException, ClassNotFoundException {
        getCommand("ans").setExecutor(new AnswerCommand(this));
        getCommand("hlist").setExecutor(new BlacklistCommand(this));
        getCommand("achat").setExecutor(new HelperChatCommand(this));
        getCommand("hmute").setExecutor(new MuteQuestionCommand(this));
        getCommand("question").setExecutor(new QuestionCommand(this));
        getCommand("hrank").setExecutor(new RankCommand(this));
        getCommand("hinfo").setExecutor(new CheckInfoCommand(this));
        getCommand("hunmute").setExecutor(new UnmuteCommand(this));
        Bukkit.getPluginManager().registerEvents(new JoinPlayer(this), this);
    }
    public void onDisable(){
        getLogger().info("HelperMode disable!");
    }
}
