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
import ru.solomka.helper.commands.util.methods.Info;
import ru.solomka.helper.commands.util.methods.Mute;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.config.ConfigManager;

import java.sql.SQLException;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class InfoCommand implements CommandExecutor {

    @Getter private final Main plugin;

    private final Info info = new Info();
    private final ConfigManager config = new ConfigManager();

    public InfoCommand(Main plugin) throws SQLException, ClassNotFoundException {
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
        for (int i = 1; i < args.length; i++) sb.append(i).append(' ');

        if(sb.length() < 1) {
            new BuildString().buildList(config.getStringList("Message.Help.commands"), p);
            return true;
        }
        info.showInformationHelper(p, args[0], args[1]);
        return true;
    }
}