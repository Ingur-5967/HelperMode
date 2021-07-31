package ru.solomka.helper.commands.util.methods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;

import static org.bukkit.ChatColor.*;

public class Chat {

    private final Users users = new Users(Main.getInstance());
    private final MessageHandler msg = new MessageHandler();

    public Chat() throws SQLException, ClassNotFoundException {}

    public void sendMessageChat(Player player, String message) throws SQLException {
        if(!users.isHelperPlayer(player.getName())) {
            player.sendMessage(translateAlternateColorCodes('&', msg.notHelper()));
            return;
        }

        if(!new CheckString().isValuableString(player, message)) return;

        for(String name : users.getHelpersOnline()) {
            Bukkit.getPlayer(name).sendMessage(translateAlternateColorCodes('&', users.getStatusPlayer(player.getName()) + player.getName() + ": " + message));
        }
    }
}