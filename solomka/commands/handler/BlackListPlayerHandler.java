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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.Main.*;
import static ru.solomka.OptionalCommand.message.EText.send;
import static ru.solomka.commands.util.methods.BuildString.buildString;
import static ru.solomka.config.ConfigManager.*;

public class BlackListPlayerHandler extends BasicCommandHandler {

    protected static BlackListPlayerHandler instance;

    private static final List<String> player = getStringList("BlackListPlayers");

    public BlackListPlayerHandler() {
        super(new BlackListPlayer());
        instance = this;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
    private static class BlackListPlayer extends BasicCommand {
        public BlackListPlayer(String command, Usage<CommandSender, String[]> func) {
            super(command, func);
        }

        public BlackListPlayer() {
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

                    map.remove(extract_id);
                    haveQuestion.remove(extract_id);

                      if (player.contains(extract.getAddress().getHostString())) {
                            player.remove(extract.getAddress().getHostString());
                            set("BlackListPlayers", player);
                            save();
                            reload(p, translateAlternateColorCodes('&', "&a&l[SUCCESS] &fВы удалили игрока " + extract.getName() + " из черного списка игроков!"));
                            return;
                        }
                        player.add(extract.getAddress().getHostString());
                        set("BlackListPlayers", player);
                        save();
                        reload(p, translateAlternateColorCodes('&', "&a&l[SUCCESS] &fВы добавили игрока " + extract.getName() + " в черный список игроков!"));
                        return;
                }
                send(sender, translateAlternateColorCodes('&', getString("NotHavePermission")));
            };
        }

        @Override
        public String getDescription() {
            return "add blacklist players";
        }

        @Override
        public String getUsage() {
            return "/blacklist-player";
        }

        @Override
        public String getPermission() {
            return "HelperMode.admin";
        }
    }
}
