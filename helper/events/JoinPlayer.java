package ru.solomka.helper.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.solomka.helper.Main;
import ru.solomka.helper.database.tables.Users;
import ru.solomka.helper.database.tables.utils.StatusType;

import java.sql.SQLException;

public class JoinPlayer implements Listener {

    @Getter private final Main plugin;

    private final Users users = new Users(Main.getInstance());

    public JoinPlayer(Main plugin) throws SQLException, ClassNotFoundException {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void join(PlayerJoinEvent e) throws SQLException {
        Player p = e.getPlayer();

        if(users.isHavePlayer(p.getName())) return;
        users.addUser(p.getName(), StatusType.USER.getStatus(), " ", 0, false, false, false, false, false);
    }
}
