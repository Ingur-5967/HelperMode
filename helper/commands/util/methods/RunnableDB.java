package ru.solomka.helper.commands.util.methods;

import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.enums.RunnableType;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;

import static org.bukkit.ChatColor.*;

public class RunnableDB {

    private final Users users = new Users(Main.getInstance());

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
                        p.sendMessage(translateAlternateColorCodes('&', "&aВаш мут автоматически снят системой! Не нарушайте правила!"));
                    }
                }.runTaskLater(Main.getInstance(), time * 20L);
            }
            case COOLDOWN : {
                new BukkitRunnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        if(!users.isHaveCooldown(p.getName())) {
                            cancel();
                            return;
                        }
                        users.setCooldownPlayer(p.getName(), false);
                        users.setActiveQuestion(p.getName(), false);
                        p.sendMessage(translateAlternateColorCodes('&', "&aВы снова можете подавать вопросы!"));
                    }
                }.runTaskLater(Main.getInstance(), time * 20L);
            }
        }
    }
}