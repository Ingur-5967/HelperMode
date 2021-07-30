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
import ru.solomka.helper.inventory.utils.InventoryClick;

import java.sql.SQLException;

import static ru.solomka.helper.config.ConfigManager.createConfig;

public class Main extends JavaPlugin {

    // TODO Сделать личный кабинет с информацией, команду для повышения хелперов

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
        getCommand("answer").setExecutor(new AnswerCommand(this));
        getCommand("blacklist").setExecutor(new BlacklistCommand(this));
        getCommand("a").setExecutor(new HelperChatCommand(this));
        getCommand("mute-q").setExecutor(new MuteQuestionCommand(this));
        getCommand("question").setExecutor(new QuestionCommand(this));
        getCommand("up-h").setExecutor(new HelperUpCommand(this));
        // getCommand("hstat").setExecutor(new ProfileCommand(this));
        Bukkit.getPluginManager().registerEvents(new JoinPlayer(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(this), this);
    }
    public void onDisable(){
        getLogger().info("HelperMode disable!");
    }
}
