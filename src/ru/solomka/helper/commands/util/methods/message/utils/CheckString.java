package ru.solomka.helper.commands.util.methods.message.utils;

import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class CheckString {

    public boolean isValuableString(Player p, String message, List<String> list) {
        for (String s : list) {
            if (message.toLowerCase().contains(s)) {
                p.sendMessage(translateAlternateColorCodes('&', "&cВ вашем сообщение содержится запрещеное слово ['" + message + "']"));
                return false;
            }
            return true;
        }
        return false;
    }
}

