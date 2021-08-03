package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.database.tables.Users;
import ru.solomka.helper.database.tables.utils.StatusType;

import java.sql.SQLException;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class Rank {

    private final Users users = new Users(Main.getInstance());
    private final MessageHandler msg = new MessageHandler();

    public Rank() throws SQLException, ClassNotFoundException {}

    public void rank(Player sender, Player user, int rank) throws SQLException {
        if(!users.getStatusPlayer(sender.getName()).contains(StatusType.HELPER_MAIN.getStatus()) || !sender.isOp()) {
            sender.sendMessage(translateAlternateColorCodes('&', msg.sNotHavePermission()));
            return;
        }

        if(!users.isHelperPlayer(user.getName()) && rank != 0) users.setHelperPlayer(user.getName(), true);

        switch(rank) {
            case 0 : {
                users.setStatusPlayer(StatusType.USER, user.getName());
                users.setGradesPlayer(user.getName(), 0);
                users.setWarns(user.getName(), 0);
                users.setHelperPlayer(user.getName(), false);
                sender.sendMessage(translateAlternateColorCodes('&', msg.sTakeOffHelper(user)));
                return;
            }
            case 1 : {
                users.setStatusPlayer(StatusType.HELPER_JUNIOR, user.getName());
                sender.sendMessage(translateAlternateColorCodes('&', msg.sRankHelper(users.getStatusPlayer(user.getName()), user)));
                return;
            }
            case 2 : {
                users.setStatusPlayer(StatusType.HELPER_MIDDLE, user.getName());
                sender.sendMessage(translateAlternateColorCodes('&', msg.sRankHelper(users.getStatusPlayer(user.getName()), user)));
                return;
            }
            case 3 : {
                users.setStatusPlayer(StatusType.HELPER_PROFESSIONAL, user.getName());
                sender.sendMessage(translateAlternateColorCodes('&', msg.sRankHelper(users.getStatusPlayer(user.getName()), user)));
                return;
            }
            case 4 : {
                users.setStatusPlayer(StatusType.HELPER_MAIN, user.getName());
                sender.sendMessage(translateAlternateColorCodes('&', msg.sRankHelper(users.getStatusPlayer(user.getName()), user)));
                return;
            }
            default : sender.sendMessage(translateAlternateColorCodes('&', msg.sEmptyRank(rank)));
        }
    }
}
