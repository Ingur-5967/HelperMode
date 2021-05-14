package ru.solomka.commands.util.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.solomka.Main;

import java.util.HashSet;
import java.util.UUID;

import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.Main.*;
import static ru.solomka.Main.join;
import static ru.solomka.OptionalCommand.message.EText.send;
import static ru.solomka.config.ConfigManager.getString;
import static ru.solomka.config.ConfigManager.getStringList;

public class JoinChat implements Listener {

    private final Main plugin;

    public JoinChat(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void getMessage(AsyncPlayerChatEvent e) {

        String message = e.getMessage();

        Player p = e.getPlayer();

        UUID id = p.getUniqueId();

        if(!join.contains(id)) return;
        if(!chat.contains(id)) return;

        for (String s : getStringList("DenyWords")) {
            if (message.toLowerCase().contains(s)) {
                send(p, translateAlternateColorCodes('&', getString("DenyMessage")));
                e.setCancelled(true);
                return;
            }
        }

        e.setCancelled(true);

        for(UUID player_chat : join) {
            getPlayer(player_chat).sendMessage(translateAlternateColorCodes('&', getString("ChatPrefix") + " " + p.getName() + " &7→&f " + message));
        }
    }
    public Main getPlugin() {
        return plugin;
    }
}
