package ru.solomka.helper.commands;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.BlackList;
import ru.solomka.helper.commands.util.methods.BuildString;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.config.ConfigManager;

import java.sql.SQLException;

import static org.bukkit.ChatColor.*;

public class BlacklistCommand implements CommandExecutor {

    @Getter private final Main plugin;

   private final MessageHandler msg = new MessageHandler();
   private final ConfigManager config = new ConfigManager();
   private final BlackList blackList = new BlackList();

    public BlacklistCommand(Main plugin) throws SQLException, ClassNotFoundException {
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
        for(int i = 2; i < args.length; i++) sb.append(args[i]).append(' ');

        if(sb.length() < 2) {
            new BuildString().buildList(config.getStringList("Message.Help.commands"), p);
            return true;
        }

        Player extract = Bukkit.getPlayerExact(args[0]);

        if(extract == null) {
            sender.sendMessage(translateAlternateColorCodes('&', msg.pIsNull(args[0])));
            return true;
        }
        blackList.list(extract, p, args[1], sb.toString());
        return true;
    }
}