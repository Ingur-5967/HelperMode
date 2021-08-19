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
import ru.solomka.helper.commands.util.methods.enums.RunnableType;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.commands.util.methods.message.utils.SendStatus;
import ru.solomka.helper.commands.util.methods.runnable.RunnableDB;
import ru.solomka.helper.config.ConfigManager;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class JoinPlayer implements Listener {

    @Getter private final Main plugin;

    private final Users users = new Users(Main.getInstance());

    private final MessageHandler msg = new MessageHandler();
    private final SendStatus status = new SendStatus();
    private final ConfigManager config = new ConfigManager();

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Date date = new Date();


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

                if(users.isActiveQuestion(p.getName())) new RunnableDB().start(p, config.getInt("Settings.Question.AutoClosingQuestion"), RunnableType.COOLDOWN);

                if(users.isHelperPlayer(p.getName())) {
                    onlineHelpers.add(p);
                    users.setLastJoinPlayer(p.getName(), format.format(date));
                    if(!status.isAllowedStatus(msg.pInfoJoinHelper(p.getPlayer()))) return;
                    return;
                }

                if(users.isHavePlayer(p.getName())) return;

                users.addUser(p.getName(),false,false,false,false,0,false,
                        0,0, 0, format.format(date));

            }
        }.runTaskLater(Main.getInstance(), 20L);
    }
}