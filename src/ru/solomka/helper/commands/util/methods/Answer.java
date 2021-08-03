package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.config.ConfigManager;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class Answer {

    private final Users users = new Users(Main.getInstance());
    private final MessageHandler msg = new MessageHandler();



    public Answer() throws SQLException, ClassNotFoundException {}

    public void sendAnswer(Player helper, Player user, StringBuilder sb) throws SQLException {
        if(!users.isHelperPlayer(helper.getName())) {
            helper.sendMessage(translateAlternateColorCodes('&', msg.sNotHelper()));
            return;
        }
        if(!users.isActiveQuestion(user.getName())) {
            helper.sendMessage(translateAlternateColorCodes('&', msg.sNotHaveQuestion(user)));
            return;
        }
        user.sendMessage(translateAlternateColorCodes('&', "&6[✎] &fОтвет: &c" + sb + "&8‖ &fХелпер:&9 " + helper.getName()));
        users.setGradesPlayer(helper.getName(), users.getValueGradesPlayer(helper.getName()) + 1);
    }
}
