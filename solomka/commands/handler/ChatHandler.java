package ru.solomka.commands.handler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.OptionalCommand.Interface.ECommand;
import ru.solomka.OptionalCommand.Interface.Usage;
import ru.solomka.OptionalCommand.basic.BasicCommand;
import ru.solomka.OptionalCommand.basic.BasicCommandHandler;
import org.bukkit.inventory.meta.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.Main.chat;
import static ru.solomka.Main.join;
import static ru.solomka.OptionalCommand.message.EText.send;
import static ru.solomka.config.ConfigManager.getString;

public class ChatHandler extends BasicCommandHandler {

    protected static ChatHandler instance;

    public ChatHandler() {
        super(new Chat());
        instance = this;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
    private static class Chat extends BasicCommand {

        public Chat(String command, Usage<CommandSender, String[]> func) {
            super(command, func);
        }

        @Override
        public String getDescription() {
            return "Join helper chat";
        }

        @Override
        public String getUsage() {
            return "/helper-chat";
        }

        @Override
        public String getPermission() {
            return "HelperMode.helper";
        }

        public Chat() {
            super(null, null);

            func = (sender, args) -> {
                final List<ECommand> cmds = new ArrayList<>(instance.getRegisteredCommands());

                cmds.add(this);

                if (!(sender instanceof Player)) return;

                Player p = (Player) sender;

                UUID id = p.getUniqueId();

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) sb.append(args[i]).append(' ');

                if (sender.hasPermission(getPermission()) || sender.isOp()) {
                    if (join.contains(id)) {
                        if (chat.contains(id)) {
                            chat.remove(id);
                            send(sender, translateAlternateColorCodes('&', "&a&l[SUCCESS] &fВы успешно вышли из чата хелперов!"));
                            return;
                        }
                        chat.add(id);
                        send(sender, translateAlternateColorCodes('&', "&a&l[SUCCESS] &fВы успешно вошли в чат хелперов! &9(Пишите в чат)"));
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