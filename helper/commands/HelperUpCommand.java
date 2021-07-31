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
import ru.solomka.helper.commands.util.methods.Rank;
import ru.solomka.helper.database.tables.Users;
import ru.solomka.helper.database.tables.utils.StatusType;

import java.sql.SQLException;

import static org.bukkit.ChatColor.*;

public class HelperUpCommand implements CommandExecutor {

   @Getter private final Main plugin;

    public HelperUpCommand(Main plugin) {
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
            sender.sendMessage(translateAlternateColorCodes('&', "&cВведите команду в формате /up-h <name> <rank (1,2,3,4)>"));
            return true;
        }

        Player extract = Bukkit.getPlayer(args[0]);

        if(extract.isEmpty()) {
            sender.sendMessage(translateAlternateColorCodes('&', "&cИгрок '" + args[0] + "' оффлайн!"));
            return true;
        }

        int rank;

        try {
            rank = Integer.parseInt(args[1]);
        } catch(NumberFormatException e) {
            sender.sendMessage(translateAlternateColorCodes('&', "&cНекорректное число '" + args[1] + "'"));
            return true;
        }
        new Rank().set(p, extract, rank);
        return true;
    }
}
