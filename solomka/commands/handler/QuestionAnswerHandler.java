package ru.solomka.commands.handler;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.solomka.Main;
import ru.solomka.OptionalCommand.Interface.ECommand;
import ru.solomka.OptionalCommand.Interface.Usage;
import ru.solomka.OptionalCommand.basic.BasicCommand;
import ru.solomka.OptionalCommand.basic.BasicCommandHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getPlayerExact;
import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.Main.*;
import static ru.solomka.OptionalCommand.message.EText.send;
import static ru.solomka.commands.handler.HelperHandler.*;
import static ru.solomka.commands.util.methods.BuildString.buildString;
import static ru.solomka.config.ConfigManager.getString;
import static ru.solomka.config.ConfigManager.getStringList;

public class QuestionAnswerHandler extends BasicCommandHandler {

    protected static QuestionAnswerHandler instance;

    public QuestionAnswerHandler() {
        super(new Answer());
        instance = this;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }

    private static class Answer extends BasicCommand {

        public Answer(String command, Usage<CommandSender, String[]> func) {
            super(command, func);
        }

        @Override
        public String getDescription() 	{ return "Send your answer to player"; }

        @Override
        public String getUsage() 		{ return "/answer"; }

        @Override
        public String getPermission() 	{ return "HelperMode.helper"; }

        public Answer() {
            super(null, null);

            func = (sender, args) -> {
                final List<ECommand> cmds = new ArrayList<>(instance.getRegisteredCommands());

                cmds.add(this);

                if (!(sender instanceof Player)) return;

                Player p = (Player) sender;

                UUID id = p.getUniqueId();

                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < args.length; i++) sb.append(args[i]).append(' ');

                if(sb.length() < 1) {
                    buildString(getStringList("HelpListHelper"), p);
                    return;
                }

                Player player_extract = getPlayerExact(args[0]);

                if (player_extract == null) {
                    send(sender, translateAlternateColorCodes('&', getString("NoPlayer")));
                    return;
                }

                UUID id_extract = player_extract.getUniqueId();

                for (String s : getStringList("DenyWords")) {
                    if (sb.toString().toLowerCase().contains(s)) {
                        send(sender, translateAlternateColorCodes('&', getString("DenyMessage")));
                        return;
                    }
                }

                if (sender.hasPermission(getPermission()) || sender.isOp()) {
                    if (join.contains(id)) {
                        if (map.contains(id_extract)) {
                            send(player_extract, translateAlternateColorCodes('&', "&6[✎] &fОтвет: &c" + sb + "&8‖ &fХелпер:&9 " + sender.getName()));
                            send(player_extract, translateAlternateColorCodes('&', getString("Question.AlreadyQuestionNow")));
                            map.remove(id_extract);
                            haveQuestion.remove(id_extract);
                            return;
                        }
                        send(sender, ChatColor.translateAlternateColorCodes('&', getString("Question.NoQuestionPlayer")));
                        return;
                    }
                    send(sender, translateAlternateColorCodes('&', getString("Helpers.JoinHelperMode")));
                    return;
                }
                send(sender, translateAlternateColorCodes('&', getString("NotHavePermission")));
            };
        }
    }
}
