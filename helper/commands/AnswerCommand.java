package ru.solomka.helper.commands;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.Answer;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class AnswerCommand implements CommandExecutor {

    @Getter private final Main plugin;

    public AnswerCommand(Main plugin) {
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

        if(args[0].isEmpty() || args[1].isEmpty()) {
            p.sendMessage(translateAlternateColorCodes('&', "&cВведите команду в формате /answer <player> <message>"));
            return true;
        }

        Player extract = Bukkit.getPlayer(args[0]);

        if(extract.isEmpty()) {
            p.sendMessage(translateAlternateColorCodes('&', "&cДанного игрока нет на сервере!"));
            return true;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) sb.append(args[i]).append(' ');
        new Answer().sendAnswer(p, extract, sb);
        return true;
    }
}