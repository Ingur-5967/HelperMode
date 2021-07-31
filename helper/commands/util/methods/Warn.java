package ru.solomka.helper.commands.util.methods;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.config.ConfigManager;
import ru.solomka.helper.database.tables.Users;
import ru.solomka.helper.database.tables.utils.StatusType;

import java.sql.SQLException;

import static org.bukkit.ChatColor.*;

public class Warn {

    private final Users users = new Users(Main.getInstance());
    private final MessageHandler msg = new MessageHandler();

    private final ConfigManager config = new ConfigManager();

    public Warn() throws SQLException, ClassNotFoundException {}

    public void warnHelper(Player user, Player sender, int warns) throws SQLException {
        if(!users.getStatusPlayer(sender.getName()).contains(StatusType.HELPER_MAIN.getStatus()) || !sender.isOp()) {
            sender.sendMessage(translateAlternateColorCodes('&', msg.notHavePermission()));
            return;
        }
        users.setWarns(user.getName(), warns);
        sender.sendMessage(translateAlternateColorCodes('&', msg.playerWarn(user)));
        user.sendMessage(translateAlternateColorCodes('&', msg.infoWarn(warns)));
        if(users.getValueWarns(user.getName()) == config.getInt("Settings.Helper.MaxWarn")) {
            users.setStatusPlayer(StatusType.USER, user.getName());
            users.setHelperPlayer(user.getName(), false);
            users.setGradesPlayer(user.getName(), 0);
            users.setWarns(user.getName(), 0);
            user.sendMessage(translateAlternateColorCodes('&', msg.takeOffHelperPerm()));
        }
    }
}