package ru.solomka.helper.events;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.commands.util.methods.message.utils.SendStatus;
import ru.solomka.helper.database.tables.Users;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ru.solomka.helper.events.JoinPlayer.onlineHelpers;

public class DisconnectPlayer implements Listener {

    @Getter private final Main plugin;

    private final Users users = new Users(Main.getInstance());

    private final SendStatus status = new SendStatus();
    private final MessageHandler msg = new MessageHandler();

    public DisconnectPlayer(Main plugin) throws SQLException, ClassNotFoundException {
        this.plugin = plugin;
    }

    @EventHandler
    public void kick(PlayerKickEvent e) {
        Player p = e.getPlayer();
        new BukkitRunnable() {
            @SneakyThrows
            @Override
            public void run() {
                if(!users.isHelperPlayer(p.getName())) return;
                onlineHelpers.remove(p.getPlayer());
                if(!status.isAllowedStatus(msg.pInfoExitHelper(p))) return;
            }
        }.runTaskLater(Main.getInstance(), 20L);
    }

    @EventHandler
    public void leave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        new BukkitRunnable() {
            @SneakyThrows
            @Override
            public void run() {
                if(!users.isHelperPlayer(p.getName()))return;
                onlineHelpers.remove(p.getPlayer());
                if(!status.isAllowedStatus(msg.pInfoExitHelper(p))) return;
            }
        }.runTaskLater(Main.getInstance(), 20L);
    }
}
