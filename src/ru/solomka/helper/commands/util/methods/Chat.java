package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.config.ConfigManager;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.helper.events.JoinPlayer.onlineHelpers;

public class Chat {

    private final Users users = new Users(Main.getInstance());

    private final MessageHandler msg = new MessageHandler();
    private final ConfigManager config = new ConfigManager();

    public Chat() throws SQLException, ClassNotFoundException {}

    public void sendMessageChat(Player player, StringBuilder message) throws SQLException {
        if(!users.isHelperPlayer(player.getName())) {
            player.sendMessage(translateAlternateColorCodes('&', msg.sNotHavePermission()));
            return;
        }

        for(Player name : onlineHelpers) {
            name.sendMessage(translateAlternateColorCodes('&', msg.sFormatHelperChat(player.getName(), message.toString())));
        }
    }
}