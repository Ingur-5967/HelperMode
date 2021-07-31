package ru.solomka.helper.commands.util.methods;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.enums.RunnableType;
import ru.solomka.helper.config.ConfigManager;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;

import static org.bukkit.ChatColor.*;

public class Question {

    private final Users users = new Users(Main.getInstance());
    private final ConfigManager config = new ConfigManager();
    private final MessageHandler msg = new MessageHandler();

    public Question() throws SQLException, ClassNotFoundException {}

    public void sendQuestion(Player p, String message) throws SQLException, ClassNotFoundException {
        if(users.getHelpersOnline().size() == 0) {
            p.sendMessage(translateAlternateColorCodes('&', msg.helpersNull()));
            return;
        }

        if(users.isActiveQuestion(p.getName())) {
            p.sendMessage(translateAlternateColorCodes('&', msg.haveActiveQuestion()));
            return;
        }

        for(String name : users.getHelpersOnline()) {
            Bukkit.getPlayer(name).sendMessage(msg.sendQuestionFormat(message));
        }

        users.setActiveQuestion(p.getName(), true);
        new RunnableDB().start(p, config.getInt("Question.Cooldown"), RunnableType.COOLDOWN);
    }
}