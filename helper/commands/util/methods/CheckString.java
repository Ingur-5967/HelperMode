package ru.solomka.helper.commands.util.methods;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.solomka.helper.config.ConfigManager;

import java.util.List;

public class CheckString {

    private final List<String> list = new ConfigManager().getStringList("Settings.Words.DenyWords");

    public boolean isValuableString(Player p, String message) {
        for(String str : list) {
            if(message.equalsIgnoreCase(str)) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cВ вашем сообщение присутствует запрещенное слово ['" + str + "']"));
                return false;
            }
            return true;
        }
        return false;
    }
}
