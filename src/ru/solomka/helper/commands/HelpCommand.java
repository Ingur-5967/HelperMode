package ru.solomka.helper.commands;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.BuildString;
import ru.solomka.helper.config.ConfigManager;

public class HelpCommand implements CommandExecutor {

   @Getter private final Main plugin;

   private final BuildString build = new BuildString();
   private final ConfigManager config = new ConfigManager();

    public HelpCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        build.buildList(config.getStringList("Message.Help.commands"), sender);
        return true;
    }
}