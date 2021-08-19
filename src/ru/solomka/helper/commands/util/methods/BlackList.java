package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.config.ConfigManager;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;

import static org.bukkit.ChatColor.translateAlternateColorCodes;


public class BlackList {

    private final Users users = new Users(Main.getInstance());
    private final MessageHandler msg = new MessageHandler();
    private final ConfigManager config = new ConfigManager();

    public BlackList() throws SQLException, ClassNotFoundException {}

    public void list(Player p, Player sender, String blocking, String reason) throws SQLException {
        if(!sender.hasPermission("Helper.blacklist") || !sender.isOp()) {
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
                return;
            }
            default : sender.sendMessage(translateAlternateColorCodes('&', "&cInvalid argument ['" + blocking + "']!"));
        }
    }
}