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
import ru.solomka.helper.commands.util.methods.Chat;
import ru.solomka.helper.commands.util.methods.Info;
import ru.solomka.helper.commands.util.methods.MessageHandler;
import ru.solomka.helper.config.ConfigManager;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class CheckInfoCommand implements CommandExecutor {

    @Getter private final Main plugin;

    private final MessageHandler msg = new MessageHandler();
    private final ConfigManager config = new ConfigManager();

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

        if(args[0].isEmpty()) {
            new BuildString().buildList(config.getStringList("Message.Help.commands"), p);
            return true;
        }

        if(extract.isEmpty()) {
            p.sendMessage(translateAlternateColorCodes('&', msg.pIsNull(extract)));
            return true;
        }

        new Info().check(p, extract);
        return true;
    }
}