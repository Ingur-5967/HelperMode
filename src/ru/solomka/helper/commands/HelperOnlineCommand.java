package ru.solomka.helper.commands;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.Online;

import static org.bukkit.ChatColor.*;

public class HelperOnlineCommand implements CommandExecutor {

    @Getter private final Main plugin;

    private final Online online = new Online();

    public HelperOnlineCommand(Main plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(translateAlternateColorCodes('&', "&cYOU NOT PLAYER"));
            return true;
        }
        Player p = (Player) sender;
        online.showOnlineHelpers(p);
        return true;
    }
}