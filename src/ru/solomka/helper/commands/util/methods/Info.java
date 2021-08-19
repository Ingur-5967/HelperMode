package ru.solomka.helper.commands.util.methods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.config.ConfigManager;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class Info {

    private final Users users = new Users(Main.getInstance());

    private final MessageHandler msg = new MessageHandler();
    private final ConfigManager config = new ConfigManager();
    private final BuildString build = new BuildString();


    public Info() throws SQLException, ClassNotFoundException {}

    public void showInformationHelper(Player helper, String name, String mode) throws SQLException {

        if(!users.isHelperPlayer(name)) {
            helper.sendMessage(translateAlternateColorCodes('&', "&cИгрок не является хелпером!"));
            return;
        }

        if(!users.isHelperPlayer(helper.getName())) {
            helper.sendMessage(translateAlternateColorCodes('&', msg.sNotHelper()));
            return;
        }

        switch (mode) {
            case "offline" : {
                if(!Bukkit.getOfflinePlayer(name).hasPlayedBefore()) {
                    helper.sendMessage(translateAlternateColorCodes('&', "&cДанного игрока не было ни разу на сервере!"));
                    return;
                }
                info(helper, Bukkit.getOfflinePlayer(name).getName());
                return;
            }
            case "online" : {
                Player helperOnline = Bukkit.getPlayerExact(name);
                if(Bukkit.getPlayerExact(name) == null) {
                    helper.sendMessage(translateAlternateColorCodes('&', msg.pIsNull(name)));
                    return;
                }
                info(helper, helperOnline.getName());
                return;
            }
            default : helper.sendMessage(translateAlternateColorCodes('&', "&cInvalid argument ['" + mode + "']"));
        }
    }

    private void info(Player p, String name) {

        List<String> information = config.getStringList("Message.Info");

        information.replaceAll(s -> translateAlternateColorCodes('&', s));

        information.replaceAll(question -> {
            try {
                return question.replace("%question_value%", String.valueOf(users.getValueAllGradesPlayer(name)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return question;
        });

        information.replaceAll(warns -> {
            try {
                return warns.replace("%warns_value%", String.valueOf(users.getValueWarns(name)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return warns;
        });

        information.replaceAll(like -> {
            try {
                return like.replace("%like_grades%", String.valueOf(users.getValueLikeGradesPlayer(name)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return like;
        });

        information.replaceAll(dislike -> {
            try {
                return dislike.replace("%dislike_grades%", String.valueOf(users.getValueDisLikeGradesPlayer(name)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return dislike;
        });

        information.replaceAll(lastJoin -> {
            try {
                return lastJoin.replace("%last_join%", users.getLastJoinPlayer(name));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return lastJoin;
        });
        build.buildList(information, p);
    }
}