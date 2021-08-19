package ru.solomka.helper.commands.util.methods.runnable;

import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.enums.RunnableType;
import ru.solomka.helper.commands.util.methods.estimation.MenuHandler;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;

import static org.bukkit.ChatColor.*;
import static ru.solomka.helper.commands.util.methods.estimation.MenuHandler.*;

public class RunnableDB {

    private final Users users = new Users(Main.getInstance());

    private final MessageHandler msg = new MessageHandler();
    private final MenuHandler menus = new MenuHandler();

    public RunnableDB() throws SQLException, ClassNotFoundException {}

    public void start(Player p, int time, RunnableType runnable) {
        switch(runnable) {
            case MUTE : {
                new BukkitRunnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        if(!users.isMutedPlayer(p.getName())) {
                            cancel();
                            return;
                        }
                        users.setMutedPlayer(p.getName(), false);
                        p.sendMessage(translateAlternateColorCodes('&', msg.sAutoTakeOffMute()));
                    }
                }.runTaskLater(Main.getInstance(), time * 20L);
            }
            case COOLDOWN : {
                new BukkitRunnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        if(!users.isActiveQuestion(p.getName())) {
                            cancel();
                            return;
                        }
                        p.sendMessage(translateAlternateColorCodes('&', msg.pCooldownOver()));
                        users.setCooldownPlayer(p.getName(), false);
                        users.setActiveQuestion(p.getName(), false);
                    }
                }.runTaskLater(Main.getInstance(), time * 20L);
            }
        }
    }
}