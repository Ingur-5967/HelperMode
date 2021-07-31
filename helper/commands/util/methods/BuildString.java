package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class BuildString {
    public void buildList(List<String> list, Player sender){
        StringBuilder sb = new StringBuilder();
        for (String string : list) {
            sb.append(string).append("\n");
        }
        String result = sb.toString();
        sender.sendMessage(translateAlternateColorCodes('&', result));
    }
}
