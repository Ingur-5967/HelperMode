package ru.solomka.config.handler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.OptionalCommand.Interface.ECommand;
import ru.solomka.OptionalCommand.Interface.Usage;
import ru.solomka.OptionalCommand.basic.BasicCommand;
import ru.solomka.OptionalCommand.basic.BasicCommandHandler;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.OptionalCommand.message.EText.send;
import static ru.solomka.config.ConfigManager.getString;
import static ru.solomka.config.ConfigManager.reload;

public class ReloadConfig extends BasicCommandHandler {

    protected static ReloadConfig instance;

    public ReloadConfig() {
        super(new Config());
        instance = this;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }

    private static class Config extends BasicCommand {

        public Config(String command, Usage<CommandSender, String[]> func) {
            super(command, func);
        }
        @Override
        public String getDescription() {
            return "reload config";
        }

        @Override
        public String getUsage() {
            return "helper-reload";
        }

        @Override
        public String getPermission() {
            return "HelperMode.config";
        }


        public Config() {
            super(null, null);

            func = (sender, args) -> {
                final List<ECommand> cmds = new ArrayList<>(instance.getRegisteredCommands());

                cmds.add(this);

                if(!(sender instanceof Player)) return;

                Player p = (Player) sender;

                if (sender.hasPermission(getPermission()) || sender.isOp()) {
                    reload(p, getString("ReloadConfig"));
                    return;
                }
                send(sender, translateAlternateColorCodes('&', getString("NotHavePermission")));
            };
        }
    }
}
