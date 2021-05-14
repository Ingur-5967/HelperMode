package ru.solomka.commands.util.methods;

import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.OptionalCommand.message.EText.send;

public class BuildString {

    public static void buildString(List<String> list, Player sender){
        StringBuilder sb = new StringBuilder();
        for (String string : list) {
            sb.append(string);
        }
        String result = sb.toString();
        send(sender, translateAlternateColorCodes('&', result));
    }
}
