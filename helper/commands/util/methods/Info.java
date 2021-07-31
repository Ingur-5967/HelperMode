package ru.solomka.helper.commands.util.methods;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.database.tables.Users;
import ru.solomka.helper.database.tables.utils.StatusType;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class Info {

    @Getter private final List<String> list = new LinkedList<>();

    private final Users users = new Users(Main.getInstance());
    private final MessageHandler msg = new MessageHandler();

    public Info() throws SQLException, ClassNotFoundException {}

    public void check(Player sender, Player user) throws SQLException {
        if(!users.getStatusPlayer(sender.getName()).contains(StatusType.HELPER_MAIN.toString()) || !sender.isOp()) {
            sender.sendMessage(translateAlternateColorCodes('&', msg.playerNotHelper(user)));
            return;
        }
        sender.sendMessage(showInfo(user));
    }

    private String showInfo(Player p) throws SQLException {

        List<String> info = new LinkedList<>();

        info.add("&6Информация о хелпере &7" + p.getName());
        info.add(" ");
        info.add("&e&l[>>] &fОбщее кол-во ответов: " + users.getValueGradesPlayer(p.getName()));
        info.add("&e&l[>>] &fАктивных &cварнов&f: " + users.getValueWarns(p.getName()));
        info.add("&e&l[>>] &fТекущий ранг хелпера: " + users.getStatusPlayer(p.getName()));

        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str).append("\n");
        }
        return translateAlternateColorCodes('&', sb.toString());
    }
}