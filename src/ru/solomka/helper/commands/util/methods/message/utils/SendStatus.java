package ru.solomka.helper.commands.util.methods.message.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.solomka.helper.config.ConfigManager;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class SendStatus {

    private final ConfigManager config = new ConfigManager();

    public boolean isAllowedStatus(String message) {
        if(!config.getBoolean("Settings.Helper.SendStatusHelper")) return false;
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(translateAlternateColorCodes('&', message));
        }
        return false;
    }
}