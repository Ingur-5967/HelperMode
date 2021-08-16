package ru.solomka.helper.commands;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.BuildString;
import ru.solomka.helper.commands.util.methods.Helper;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.config.ConfigManager;

import java.sql.SQLException;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class HelperCommand implements CommandExecutor {

   @Getter private final Main plugin;

   private final MessageHandler msg = new MessageHandler();
   private final ConfigManager config = new ConfigManager();
   private final Helper helper = new Helper();

    public HelperCommand(Main plugin) throws SQLException, ClassNotFoundException {
        this.plugin = plugin;
    }

    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) sb.append(i).append(' ');

        if(sb.length() < 1) {
            new BuildString().buildList(config.getStringList("Message.Help.commands"), sender);
            return true;
        }

        Player extract = Bukkit.getPlayerExact(args[0]);

        if(extract == null) {
            sender.sendMessage(translateAlternateColorCodes('&', msg.pIsNull(args[0])));
            return true;
        }
        helper.rank(sender, extract, args[1]);
        return true;
    }
}