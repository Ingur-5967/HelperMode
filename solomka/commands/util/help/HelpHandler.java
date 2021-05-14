package ru.solomka.commands.util.help;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.OptionalCommand.Interface.ECommand;
import ru.solomka.OptionalCommand.Interface.Usage;
import ru.solomka.OptionalCommand.basic.BasicCommand;
import ru.solomka.OptionalCommand.basic.BasicCommandHandler;

import java.util.ArrayList;
import java.util.List;

import static ru.solomka.commands.util.methods.BuildString.buildString;
import static ru.solomka.config.ConfigManager.getStringList;

public class HelpHandler extends BasicCommandHandler {

    protected static HelpHandler instance;

    public HelpHandler() {
        super(new Help());
        instance = this;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }

    private static class Help extends BasicCommand {
        public Help(String command, Usage<CommandSender, String[]> func) {
            super(command, func);
        }

        @Override
        public String getDescription() {
            return "Help commands";
        }

        @Override
        public String getUsage() {
            return "/helpList";
        }

        @Override
        public String getPermission() {
            return "Helper.player";
        }

        public Help() {
            super(null, null);

            func = (sender, args) -> {
                final List<ECommand> cmds = new ArrayList<>(instance.getRegisteredCommands());
                cmds.add(this);

                if (!(sender instanceof Player)) return;

                Player p = (Player) sender;

                if (sender.hasPermission(getPermission()) || sender.isOp()) {
                    buildString(getStringList("HelpListHelper"), p);
                }
            };
        }
    }
}