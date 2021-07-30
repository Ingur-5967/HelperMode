package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.database.tables.Users;
import ru.solomka.helper.database.tables.utils.StatusType;

import java.sql.SQLException;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class Rank {

    private final Users users = new Users(Main.getInstance());

    public Rank() throws SQLException, ClassNotFoundException {}

    public void set(Player sender, Player user, int rank) throws SQLException {
        if(users.getStatusPlayer(sender.getName()).contains(StatusType.HELPER_MAIN.getStatus()) && !sender.isOp()) {
            sender.sendMessage(translateAlternateColorCodes('&', "&cУ вас недостаточно прав!"));
            return;
        }

        switch(rank) {
            case 1 : {
                users.setStatusPlayer(StatusType.HELPER_JUNIOR, user.getName());
                sender.sendMessage(translateAlternateColorCodes('&', "&aВы успешно выдали игроку &6" + user.getName() + "&a ранг: " + users.getStatusPlayer(user.getName())));
                return;
            }
            case 2 : {
                users.setStatusPlayer(StatusType.HELPER_MIDDLE, user.getName());
                sender.sendMessage(translateAlternateColorCodes('&', "&aВы успешно выдали игроку &6" + user.getName() + "&a ранг: " + users.getStatusPlayer(user.getName())));
                return;
            }
            case 3 : {
                users.setStatusPlayer(StatusType.HELPER_PROFESSIONAL, user.getName());
                sender.sendMessage(translateAlternateColorCodes('&', "&aВы успешно выдали игроку &6" + user.getName() + "&a ранг: " + users.getStatusPlayer(user.getName())));
                return;
            }
            case 4 : {
                users.setStatusPlayer(StatusType.HELPER_MAIN, user.getName());
                sender.sendMessage(translateAlternateColorCodes('&', "&aВы успешно выдали игроку &6" + user.getName() + "&a ранг: " + users.getStatusPlayer(user.getName())));
                user.sendMessage(translateAlternateColorCodes('&', "&aГлавный хелпер &c" + sender.getName() + "&c выдал вам ранг " + users.getStatusPlayer(user.getName())));
                return;
            }
            default : sender.sendMessage(translateAlternateColorCodes('&', "&cРанг под номером '" + rank + "' не существует!"));
        }
    }
}
