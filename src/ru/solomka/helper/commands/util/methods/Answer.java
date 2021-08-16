package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.commands.util.methods.message.utils.CheckString;
import ru.solomka.helper.config.ConfigManager;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;
import java.util.Objects;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.helper.events.JoinPlayer.onlineHelpers;

public class Answer {

    private final Users users = new Users(Main.getInstance());

    private final MessageHandler msg = new MessageHandler();
    private final CheckString check = new CheckString();
    private final ConfigManager config = new ConfigManager();

    public Answer() throws SQLException, ClassNotFoundException {}

    public void sendAnswer(Player helper, Player user, String message) throws SQLException {
        if(!users.isHelperPlayer(helper.getName())) {
            helper.sendMessage(translateAlternateColorCodes('&', msg.sNotHelper()));
            return;
        }
        if(!users.isActiveQuestion(user.getName())) {
            helper.sendMessage(translateAlternateColorCodes('&', msg.sNotHaveQuestion(user)));
            return;
        }

        if(check.isValuableString(helper, message, config.getStringList("Settings.Global.DenyWords"))) return;

        if(Objects.equals(user.getName(), helper.getName())) {
            helper.sendMessage(translateAlternateColorCodes('&', "&cНельзя отвечать на свои вопросы!"));
        }

        for(Player name : onlineHelpers) {
            name.sendMessage(translateAlternateColorCodes('&', "&a&l[РЕПУТАЦИЯ] &fНа вопрос игрока &6" + user.getName() + "&f ответил &e" + helper.getName() + "&f и получил &c+1 &fк репутации "));
        }
        user.sendMessage(translateAlternateColorCodes('&', "&6&l[ОТВЕТ] &fОтвет: &e" + message + "&8&l|| &fХелпер: &n" + helper.getName() + "&f"));
        users.setGradesPlayer(helper.getName(), users.getValueGradesPlayer(helper.getName()) + 1);
        users.setActiveQuestion(user.getName(),false);
    }
}