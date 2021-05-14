package ru.solomka.commands.handler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.OptionalCommand.Interface.ECommand;
import ru.solomka.OptionalCommand.Interface.Usage;
import ru.solomka.OptionalCommand.basic.BasicCommand;
import ru.solomka.OptionalCommand.basic.BasicCommandHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.Main.*;
import static ru.solomka.OptionalCommand.message.EText.send;
import static ru.solomka.commands.util.methods.BuildString.buildString;
import static ru.solomka.config.ConfigManager.*;
import static ru.solomka.config.ConfigManager.getString;
import static ru.solomka.config.ConfigManager.getStringList;

public class BlackListHelperHandler extends BasicCommandHandler {

    protected static BlackListHelperHandler instance;

    private static final List<String> helper = getStringList("BlackListHelpers");


    public BlackListHelperHandler() {
        super(new BlackListHelper());
        instance = this;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
    private static class BlackListHelper extends BasicCommand {
        public BlackListHelper(String command, Usage<CommandSender, String[]> func) {
            super(command, func);
        }

        @Override
        public String getDescription() {
            return "add blacklist helpers";
        }

        @Override
        public String getUsage() {
            return "/blacklist-helper";
        }

        @Override
        public String getPermission() {
            return "HelperMode.admin";
        }

        public BlackListHelper() {
            super(null, null);

            func = (sender, args) -> {
                final List<ECommand> cmds = new ArrayList<>(instance.getRegisteredCommands());

                cmds.add(this);

                if (!(sender instanceof Player)) return;

                Player p = (Player) sender;

                Player extract = Bukkit.getPlayerExact(args[0]);

                if (extract == null) {
                    send(sender, translateAlternateColorCodes('&', getString("NoPlayer")));
                    return;
                }

                UUID extract_id = extract.getUniqueId();

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) sb.append(args[i]).append(' ');
                if (sb.length() < 0) {
                    buildString(getStringList("HelpListHelper"), p);
                    return;
                }

                if (sender.hasPermission(getPermission()) || sender.isOp()) {

                    if(!extract.hasPermission("HelperMode.helper")) {
                        send(sender, translateAlternateColorCodes('&', "&c&l[ERROR] &fИгрок не является хелпером"));
                        return;
                    }

                    join.remove(extract_id);

                    if (helper.contains(extract.getAddress().getHostString())) {
                        blackList(extract, "&a&l[SUCCESS] &fВы удалили игрока " + extract.getName() + " из черного списка хелперов!");
                        return;
                    }
                    blackList(extract, "&a&l[SUCCESS] &fВы добавили игрока " + extract.getName() + " в черный список хелперов!");

                }
                send(sender, translateAlternateColorCodes('&', getString("NotHavePermission")));
            };
        }
    }

    private static void blackList(Player extract, String message) throws IOException {
        if(BlackListHelperHandler.helper.contains(extract.getAddress().getHostString())) {
            helper.remove(extract.getAddress().getHostString());
            set("BlackListHelpers", helper);
            save();
            reload(extract, translateAlternateColorCodes('&', "&a&l[SUCCESS] &fВы удалили игрока " + extract.getName() + " из черного списка хелперов!"));
            return;
        }
        BlackListHelperHandler.helper.add(extract.getAddress().getHostString());
        set("BlackListHelpers", helper);
        save();
        reload(extract, translateAlternateColorCodes('&', message));
    }
}
