package ru.solomka.helper.commands.util.methods;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.estimation.MenuHandler;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.config.ConfigManager;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;
import java.util.Objects;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.helper.events.JoinPlayer.onlineHelpers;

public class Answer {

    @Getter @Setter private Inventory inventory;

    private final Users users = new Users(Main.getInstance());

    private final MessageHandler msg = new MessageHandler();

    public Answer() throws SQLException, ClassNotFoundException {}

    public void sendAnswer(Player helper, Player user, StringBuilder message) throws SQLException {
        if(!users.isHelperPlayer(helper.getName())) {
            helper.sendMessage(translateAlternateColorCodes('&', msg.sNotHelper()));
            return;
        }

        if(!users.isActiveQuestion(user.getName())) {
            helper.sendMessage(translateAlternateColorCodes('&', msg.sNotHaveQuestion(user)));
            return;
        }

        if(Objects.equals(user.getName(), helper.getName())) {
            helper.sendMessage(translateAlternateColorCodes('&', "&cНельзя отвечать на свои вопросы!"));
        }

        for(Player name : onlineHelpers) {
            name.sendMessage(translateAlternateColorCodes('&', "&a&l[ВАЖНО] &fНа вопрос игрока &6" + user.getName() + "&f ответил &e" + helper.getName() + " &7(ВОПРОС ЗАКРЫТ)"));
        }

        user.sendMessage(translateAlternateColorCodes('&', "&6&l[ОТВЕТ] &fОтвет: &e" + message.toString() + "&8&l|| &fХелпер: &n" + helper.getName() + "&f"));
        users.setActiveQuestion(user.getName(),false);
        users.setValueAllGradesPlayer(user.getName(),users.getValueAllGradesPlayer(helper.getName()) + 1);

        MenuHandler menu = new MenuHandler(inventory, helper, user,27);
        menu.createInventory(user);
    }
}