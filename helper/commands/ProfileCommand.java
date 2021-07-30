package ru.solomka.helper.commands;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.inventory.Inventory;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
public class ProfileCommand {
/*
    @Getter private final Main plugin;

    public ProfileCommand(Main plugin) {
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
        new Inventory().createMenu(p,36, "Ваш профиль");
        return true;
    }
 */
}