package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.enums.RunnableType;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;

import static org.bukkit.ChatColor.*;

public class Mute {

    private final Users users = new Users(Main.getInstance());
    private final MessageHandler msg = new MessageHandler();

    public Mute() throws SQLException, ClassNotFoundException {}

    public void mutePlayer(Player player, Player sender, int time) throws SQLException, ClassNotFoundException {
        if(users.isMutedPlayer(player.getName())) {
            sender.sendMessage(translateAlternateColorCodes('&', msg.sHaveMute(player)));
            return;
        }
        users.setMutedPlayer(player.getName(), true);
        new RunnableDB().start(player, time, RunnableType.MUTE);
        sender.sendMessage(translateAlternateColorCodes('&', msg.sAddMute(player, time)));
    }
}