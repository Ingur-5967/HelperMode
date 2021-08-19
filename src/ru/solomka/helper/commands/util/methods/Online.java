package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.helper.events.JoinPlayer.onlineHelpers;

public class Online {

    private final MessageHandler msg = new MessageHandler();

    public void showOnlineHelpers(Player p) {
        if(onlineHelpers.size() == 0) {
            p.sendMessage(translateAlternateColorCodes('&', msg.sHelpersNull()));
            return;
        }
        p.sendMessage(translateAlternateColorCodes('&', "&aХелперов в сети:&c " + onlineHelpers.size()));
    }

}
