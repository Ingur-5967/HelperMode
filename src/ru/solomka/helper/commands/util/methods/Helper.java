package ru.solomka.helper.commands.util.methods;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.database.tables.Users;
import ru.solomka.helper.events.JoinPlayer;

import java.sql.SQLException;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.helper.events.JoinPlayer.*;

public class Helper {

    private final Users users = new Users(Main.getInstance());
    private final MessageHandler msg = new MessageHandler();

    public Helper() throws SQLException, ClassNotFoundException {}

    public void rank(CommandSender sender, Player p, String mode) throws SQLException {
        if (!(sender instanceof ConsoleCommandSender)) {
            sender.sendMessage(translateAlternateColorCodes('&', "&cДанную команду необходимо прописывать в консоли!"));
            return;
        }

        if(users.isBlockingPlayer(p.getName())) {
            sender.sendMessage(translateAlternateColorCodes('&', "&cThe user is included in the black list of helpers!!"));
            return;
        }

        if(mode.equals("take") && !users.isHelperPlayer(p.getName())) {
            sender.sendMessage(ChatColor.RED + "User is not helper!");
            return;
        }

        if(mode.equals("give") && users.isHelperPlayer(p.getName())) {
            sender.sendMessage(ChatColor.RED + "User is already helper!");
            return;
        }

        switch (mode) {
            case "give" : {
                users.setHelperPlayer(p.getName(), true);
                onlineHelpers.add(p);
                p.sendMessage(translateAlternateColorCodes('&', msg.pSetHelper(translateAlternateColorCodes('&', "выдали"))));
                return;
            }
            case "take" : {
                users.setHelperPlayer(p.getName(), false);
                onlineHelpers.remove(p);
                p.sendMessage(translateAlternateColorCodes('&', msg.pSetHelper(translateAlternateColorCodes('&', "забрали"))));
                return;
            }
            default : sender.sendMessage(translateAlternateColorCodes('&', "&cInvalid argument ['" + mode + "']!"));
        }
    }
}