package ru.solomka.helper.commands;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.Chat;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.config.ConfigManager;

import java.sql.SQLException;

import static org.bukkit.ChatColor.*;

public class HelperChatCommand implements CommandExecutor {

    @Getter private final Main plugin;

    private final MessageHandler msg = new MessageHandler();
    private final Chat chat = new Chat();
    private final ConfigManager config = new ConfigManager();

    public HelperChatCommand(Main plugin) throws SQLException, ClassNotFoundException {
        this.plugin = plugin;
    }

    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(translateAlternateColorCodes('&', "&cYOU NOT PLAYER"));
            return true;
        }

        Player p = (Player) sender;

        StringBuilder sb = new StringBuilder();
        for (String arg : args) sb.append(arg).append(' ');

        if(sb.length() < config.getInt("Settings.HelperChat.MinSize")) {
            p.sendMessage(translateAlternateColorCodes('&', msg.sMessageIsNull()));
            return true;
        }
        chat.sendMessageChat(p, sb);
        return true;
    }
}