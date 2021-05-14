package ru.solomka.OptionalCommand.basic;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.OptionalCommand.Interface.ECommand;
import ru.solomka.OptionalCommand.Interface.Usage;
import ru.solomka.OptionalCommand.enums.Type;

import java.io.IOException;

public abstract class BasicCommand implements ECommand {

        protected Type senderType	= Type.ALL;
        private final String commandLabel;

        protected Usage<CommandSender, String[]> func;

    public BasicCommand(String command, Usage<CommandSender, String[]> func) {
            commandLabel = command;
            this.func = func;
        }


    public String getHelp() { return ChatColor.GOLD + getUsage() + " - " + ChatColor.GREEN + getDescription(); }

        @Override
        public String getName() { return commandLabel; }

        @Override
        public Type getSenderType() { return senderType; }

    @Override
    public void use(CommandSender sender, String[] args) throws IOException {
        if (isValid(sender, senderType)) func.execute(sender, args);
    }

    public static boolean isValid(Object obj, Type senderType) {
        boolean isPlayer = obj instanceof Player;
        switch (senderType) {
            case ALL:			return true;
            case CONSOLE_ONLY:	if (isPlayer) return false;
            case PLAYER_ONLY:	if (isPlayer) return true;
            default:			return false;
        }
    }
}
