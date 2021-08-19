package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.enums.RunnableType;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.commands.util.methods.runnable.RunnableDB;
import ru.solomka.helper.config.ConfigManager;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.helper.events.JoinPlayer.*;

public class Question {

    private final Users users = new Users(Main.getInstance());

    private final ConfigManager config = new ConfigManager();
    private final MessageHandler msg = new MessageHandler();
    private final RunnableDB runnable = new RunnableDB();

    public Question() throws SQLException, ClassNotFoundException {}

    public void sendQuestion(Player p, StringBuilder message) throws SQLException {

        if(users.isActiveQuestion(p.getName())) {
            p.sendMessage(translateAlternateColorCodes('&', msg.sHaveActiveQuestion()));
            return;
        }

        if(message.toString().length() < config.getInt("Settings.Question.MinSizeMessage")) {
            p.sendMessage(translateAlternateColorCodes('&', msg.sMinSizeMessage(config.getInt("Settings.Question.MinSizeMessage"))));
            return;
        }

        if(users.isMutedPlayer(p.getName())) {
            p.sendMessage(translateAlternateColorCodes('&', msg.sHaveMute()));
        }

        if(onlineHelpers.size() == 0) {
            p.sendMessage(translateAlternateColorCodes('&', msg.sHelpersNull()));
            return;
        }

        for(Player name : onlineHelpers) {
            name.sendMessage(translateAlternateColorCodes('&', msg.sSendQuestionFormat(message.toString(), p)));
        }
        users.setActiveQuestion(p.getName(), true);
        runnable.start(p, config.getInt("Settings.Question.AutoClosingQuestion"), RunnableType.COOLDOWN);
    }
}