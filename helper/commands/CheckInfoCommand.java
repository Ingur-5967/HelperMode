package ru.solomka.helper.commands;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.Chat;
import ru.solomka.helper.commands.util.methods.Info;
import ru.solomka.helper.commands.util.methods.MessageHandler;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class CheckInfoCommand implements CommandExecutor {

    @Getter private final Main plugin;

    private final MessageHandler msg = new MessageHandler();

    public CheckInfoCommand(Main plugin) {
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

        Player extract = Bukkit.getPlayer(args[0]);

        if(extract.isEmpty()) {
            p.sendMessage(translateAlternateColorCodes('&', msg.playerIsNull(extract)));
            return true;
        }
        new Info().check(p, extract);
        return true;
    }
}