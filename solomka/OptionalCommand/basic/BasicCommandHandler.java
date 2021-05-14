package ru.solomka.OptionalCommand.basic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.solomka.OptionalCommand.Interface.CommandHandler;
import ru.solomka.OptionalCommand.Interface.ECommand;
import ru.solomka.OptionalCommand.message.EText;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class BasicCommandHandler implements CommandHandler, TabCompleter {

        protected Map<String, ECommand> commands = new HashMap<>();

        public Collection<ECommand> getRegisteredCommands() { return commands.values(); }

        private final ECommand defCommand;

        public BasicCommandHandler(ECommand def) { defCommand = def; }

        @Override
        public ECommand getDefaultCommand() { return defCommand; }

        @Override
        public void registerCommand(ECommand command) { commands.put(command.getName(), command); }

        public void registerHandler(JavaPlugin plugin, String key) {
            plugin.getCommand(key).setExecutor(this);
        }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            ECommand cmd;

            if (args.length == 0 || commands.get(args[0]) == null) cmd = defCommand;
            else {
                cmd = commands.get(args[0]);
                args = EText.trimArgs(args);
            }

            if (cmd.getPermission() == null || sender.hasPermission(cmd.getPermission())) {
                try {
                    cmd.use(sender, args);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
                sender.sendMessage("Неизвестная команда!");
            return true;
        }

}
