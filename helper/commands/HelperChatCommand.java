package ru.solomka.helper.commands;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.Chat;
import ru.solomka.helper.commands.util.methods.MessageHandler;
import ru.solomka.helper.commands.util.methods.Mute;

import static org.bukkit.ChatColor.*;

public class HelperChatCommand implements CommandExecutor {

    @Getter private final Main plugin;

    private final MessageHandler msg = new MessageHandler();

    public HelperChatCommand(Main plugin) {
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

        if(args[0].isEmpty() ) {
            p.sendMessage(translateAlternateColorCodes('&', msg.messageIsNull()));
            return true;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) sb.append(args[i]).append(' ');
        new Chat().sendMessageChat(p, sb.toString());
        return true;
    }
}
