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
import ru.solomka.helper.commands.util.methods.MessageHandler;
import ru.solomka.helper.commands.util.methods.Warn;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class WarnCommand implements CommandExecutor {

    @Getter private final Main plugin;

    private final MessageHandler msg = new MessageHandler();

    public WarnCommand(Main plugin) {
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
            p.sendMessage(translateAlternateColorCodes('&', msg.format("/hwarn <player> <value_warn>")));
            return true;
        }

        Player extract = Bukkit.getPlayer(args[0]);

        if(extract.isEmpty()) {
            p.sendMessage(translateAlternateColorCodes('&', msg.playerIsNull(extract)));
            return true;
        }
        int warnCount;
        try {
            warnCount = Integer.parseInt(args[1]);
        } catch(NumberFormatException e) {
            p.sendMessage(translateAlternateColorCodes('&', msg.invalidFormat(args[1])));
            return true;
        }
        new Warn().warnHelper(extract, p, warnCount);
        return true;
    }
}