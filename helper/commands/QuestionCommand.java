package ru.solomka.helper.commands;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.MessageHandler;
import ru.solomka.helper.commands.util.methods.Question;
import ru.solomka.helper.config.ConfigManager;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class QuestionCommand implements CommandExecutor {

    @Getter private final Main plugin;

    private final ConfigManager config = new ConfigManager();
    private final MessageHandler msg = new MessageHandler();

    public QuestionCommand(Main plugin) {
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

        if(args[0].isEmpty()) {
            p.sendMessage(translateAlternateColorCodes('&', "&cВведите команду в формате /question <message>"));
            return true;
        }

        if(args[0].length() <= config.getInt("Question.MinSize")) {
            p.sendMessage(translateAlternateColorCodes('&', msg.minSizeMessage(config.getInt("Setting.Question.MinSize"))));
            return true;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) sb.append(args[i]).append(' ');
        new Question().sendQuestion(p, sb.toString());
        return true;
    }
}
