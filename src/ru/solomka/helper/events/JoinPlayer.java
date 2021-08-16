package ru.solomka.helper.events;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.commands.util.methods.message.utils.SendStatus;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;
import java.util.HashSet;

public class JoinPlayer implements Listener {

    @Getter private final Main plugin;

    private final Users users = new Users(Main.getInstance());

    private final MessageHandler msg = new MessageHandler();
    private final SendStatus status = new SendStatus();

    public static final HashSet<Player> onlineHelpers = new HashSet<>();

    public JoinPlayer(Main plugin) throws SQLException, ClassNotFoundException {
        this.plugin = plugin;
    }

    @EventHandler
    public void join(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        new BukkitRunnable() {
            @SneakyThrows
            @Override
            public void run() {
                if(users.isHelperPlayer(p.getName())) {
                    onlineHelpers.add(p);
                    if(!status.isAllowedStatus(msg.pInfoJoinHelper(p.getPlayer()))) return;
                    return;
                }
                if(users.isHavePlayer(p.getName())) return;

                users.addUser(p.getName(),0,false,false,false,false,0,false);
            }
        }.runTaskLater(Main.getInstance(), 20L);
    }
}