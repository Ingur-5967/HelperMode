package ru.solomka.commands.handler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.Main;
import ru.solomka.OptionalCommand.Interface.ECommand;
import ru.solomka.OptionalCommand.Interface.Usage;
import ru.solomka.OptionalCommand.basic.BasicCommand;
import ru.solomka.OptionalCommand.basic.BasicCommandHandler;

import java.util.*;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.Main.join;
import static ru.solomka.OptionalCommand.message.EText.send;
import static ru.solomka.config.ConfigManager.getString;
import static ru.solomka.config.ConfigManager.getStringList;

public class HelperHandler extends BasicCommandHandler {

    public HelperHandler() {
        super(new Join());
        instance = this;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }

    protected static HelperHandler instance;

    private static class Join extends BasicCommand {

        public Join(String command, Usage<CommandSender, String[]> func) {
            super(command, func);
        }

        @Override
        public String getDescription() {
            return "join/quit in helper mode";
        }

        @Override
        public String getUsage() {
            return "/helper";
        }

        @Override
        public String getPermission() {
            return "HelperMode.helper";
        }

        public Join() {
            super(null, null);

            func = (sender, args) -> {
                final List<ECommand> cmds = new ArrayList<>(instance.getRegisteredCommands());

                cmds.add(this);

                if (!(sender instanceof Player)) return;

                Player p = (Player) sender;

                UUID id = p.getUniqueId();

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) sb.append(args[i]).append(' ');

                List<String> helper = getStringList("BlackListHelpers");
                if(helper.contains(((Player) sender).getAddress().getHostString())) {
                    send(sender, translateAlternateColorCodes('&', "&c&l[ERROR] &fВы в черном списке и не имеете право входить в систему, если это ошибка обратитесь к администрации!"));
                    return;
                }

                if (sender.hasPermission(getPermission()) || sender.isOp()) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (!join.contains(id)) {
                            join.add(id);
                            pl.sendMessage(translateAlternateColorCodes('&', "&a[⚒] &f" + sender.getName() + " &fготов отвечать на ваши &cвопросы&f!"));
                            break;
                        }
                        join.remove(id);
                        send(pl, translateAlternateColorCodes('&', "&c[✗] &f" + sender.getName() + " вышел из режима &5хелпера&f!"));
                    }
                    return;
                }
                send(sender, translateAlternateColorCodes('&', getString("NotHavePermission")));
            };
        }
    }
}
