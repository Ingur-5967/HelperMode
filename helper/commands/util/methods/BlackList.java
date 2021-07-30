package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.database.tables.Users;

import java.io.IOException;
import java.sql.SQLException;

import static org.bukkit.ChatColor.translateAlternateColorCodes;


public class BlackList {

    private final Users db = new Users(Main.getInstance());

    public BlackList() throws SQLException, ClassNotFoundException {}

    public void list(Player p, Player sender, String blocking, String reason) throws IOException, SQLException {
        switch (blocking) {
            case "add" : {
                if(db.isBlockingPlayer(p.getName())) {
                    sender.sendMessage(translateAlternateColorCodes('&', "&cИгрок уже в черном списке!"));
                    return;
                }
                db.setBlacklistPlayer(p.getName(), true);
                p.sendMessage(translateAlternateColorCodes('&', "&cВас поместили в черный список по причине: &f" + reason));
                return;
            }
            case "remove" : {
                if(!db.isBlockingPlayer(p.getName())) {
                    sender.sendMessage(translateAlternateColorCodes('&', "&cИгрок не находится в черном списке!"));
                    return;
                }
                db.setBlacklistPlayer(p.getName(),false);
            }
            default : sender.sendMessage(translateAlternateColorCodes('&', "&cВведенный некоректные аргументы /blacklist <player> <add/remove>!"));
        }
    }
}

