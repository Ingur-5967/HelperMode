package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.config.ConfigManager;
import ru.solomka.helper.database.tables.Users;
import ru.solomka.helper.database.tables.utils.StatusType;

import java.io.IOException;
import java.sql.SQLException;

import static org.bukkit.ChatColor.translateAlternateColorCodes;


public class BlackList {

    private final Users users = new Users(Main.getInstance());
    private final MessageHandler msg = new MessageHandler();
    private final ConfigManager config = new ConfigManager();

    public BlackList() throws SQLException, ClassNotFoundException {}

    public void list(Player p, Player sender, String blocking, String reason) throws SQLException {
        if(users.getStatusPlayer(sender.getName()).contains(StatusType.HELPER_MAIN.getStatus())) {
            sender.sendMessage(translateAlternateColorCodes('&', msg.sNotHavePermission()));
            return;
        }
        switch (blocking) {
            case "add" : {
                if(users.isBlockingPlayer(p.getName())) {
                    sender.sendMessage(translateAlternateColorCodes('&', msg.sInBlackList(p)));
                    return;
                }
                users.setBlacklistPlayer(p.getName(), true);
                sender.sendMessage(translateAlternateColorCodes('&', msg.sBlacklistAdd(p, reason)));
                p.sendMessage(translateAlternateColorCodes('&', msg.pInfBlacklistAdd(sender, reason)));
                return;
            }
            case "remove" : {
                if(!users.isBlockingPlayer(p.getName())) {
                    sender.sendMessage(translateAlternateColorCodes('&', msg.sNotInBlackList(p)));
                    return;
                }
                users.setBlacklistPlayer(p.getName(),false);
                sender.sendMessage(translateAlternateColorCodes('&', msg.sBlacklistRemove(p)));
                p.sendMessage(translateAlternateColorCodes('&', msg.pInfoBlacklistRemove(sender)));
            }
            default : new BuildString().buildList(config.getStringList("Message.Help.commands"), p);
        }
    }
}