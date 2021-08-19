package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.message.MessageHandler;
import ru.solomka.helper.config.ConfigManager;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;

import static org.bukkit.ChatColor.*;

public class Warn {

    private final Users users = new Users(Main.getInstance());
    private final MessageHandler msg = new MessageHandler();

    private final ConfigManager config = new ConfigManager();

    public Warn() throws SQLException, ClassNotFoundException {}

    public void warnHelper(Player user, Player sender, int warns) throws SQLException {
        if(!sender.hasPermission("Helper.warn") || !sender.isOp()) {
            sender.sendMessage(translateAlternateColorCodes('&', msg.sNotHavePermission()));
            return;
        }

        if(!users.isHelperPlayer(user.getName())) {
            sender.sendMessage(translateAlternateColorCodes('&', "&cИгрок не является хелпером!"));
            return;
        }

        if(warns > config.getInt("Settings.Helper.MaxWarn")) {
            sender.sendMessage(translateAlternateColorCodes('&', "&cМаксимально значение варнов: " + config.getInt("Settings.Helper.MaxWarn")));
            return;
        }
        users.setWarns(user.getName(), warns);
        sender.sendMessage(translateAlternateColorCodes('&', msg.sWarn(user, warns)));
        user.sendMessage(translateAlternateColorCodes('&', msg.sInfoWarn(sender, warns, config.getInt("Settings.Helper.MaxWarn"))));
        if(users.getValueWarns(user.getName()) == config.getInt("Settings.Helper.MaxWarn")) {
            users.setHelperPlayer(user.getName(), false);
            users.setValueDisLikeGradesPlayer(user.getName(), 0);
            users.setValueLikeGradesPlayer(user.getName(), 0);
            users.setWarns(user.getName(), 0);
            user.sendMessage(translateAlternateColorCodes('&', "&cВы были сняты за большое количество предупреждений!"));
        }
    }
}