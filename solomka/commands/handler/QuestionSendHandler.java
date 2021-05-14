package ru.solomka.commands.handler;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
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

public class QuestionSendHandler extends BasicCommandHandler {

    protected static QuestionSendHandler instance;

    public QuestionSendHandler() {
        super(new Send());
        instance = this;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }


    private static class Send extends BasicCommand {

        public Send(String command, Usage<CommandSender, String[]> func) {
            super(command, func);
        }


        @Override
        public String getDescription() {
            return "Send your question";
        }

        @Override
        public String getUsage() {
            return "/question";
        }

        @Override
        public String getPermission() {
            return "HelperMode.player";
        }


        public Send() {
            super(null, null);

            func = (sender, args) -> {
                final List<ECommand> cmds = new ArrayList<>(instance.getRegisteredCommands());

                cmds.add(this);

                if(!(sender instanceof Player)) return;

                Player p = (Player) sender;

                UUID sender_uuid = p.getUniqueId();

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) sb.append(args[i]).append(' ');
                if(sb.length() < 0) {
                        buildString(getStringList("HelpListHelper"), p);
                        return;
                    }

                if(args.length > getInt("MaxSizeQuestion")) {
                    send(sender, translateAlternateColorCodes('&', getString("MaxSizeLimit")));
                    return;
                }

                if(sb.length() < getInt("MinSizeQuestion")) {
                    send(sender, translateAlternateColorCodes('&', getString("MinSize")));
                    return;
                }

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(!map.contains(sender_uuid)) return;
                        map.remove(sender_uuid);
                        haveQuestion.remove(sender_uuid);
                        send(sender, translateAlternateColorCodes('&', getString("MaxTimeLimit")));
                        cancel();
                    }
                }.runTaskLater(getInstance(), getInt("MaxTimeWaitAnswer") * 20L);

                for (String s : getStringList("DenyWords")) {
                    if (sb.toString().toLowerCase().contains(s)) {
                        send(sender, translateAlternateColorCodes('&', getString("DenyMessage")));
                        return;
                    }
                }

                if(args.length == 0) {
                    send(sender, translateAlternateColorCodes('&', getString("NullQuestion")));
                    return;
                }

                List<String> player = getStringList("BlackListPlayers");
                if(player.contains(p.getAddress().getHostString())) {
                    send(sender, translateAlternateColorCodes('&', "&c&l[ERROR] &fВы в черном списке и не имеете право &6подавать&f вопрос, если это &cошибка&f - обратитесь к администрации!"));
                    return;
                }

                if (sender.hasPermission(getPermission()) || sender.isOp()) {
                    map.add(sender_uuid);
                    if (map.contains(sender_uuid)) {
                        if (join.isEmpty()) {
                            send(sender, translateAlternateColorCodes('&', getString("Helpers.NoHelpers")));
                            return;
                        }

                        if (haveBan.contains(sender_uuid)) {
                            map.remove(sender_uuid);
                            send(sender, translateAlternateColorCodes('&', "&c&l[ERROR] &fВам &cвременном &fотобрали право подавать вопросы! Дождитесь окончания блокировки..."));
                            return;
                        }

                        if (!haveQuestion.contains(sender_uuid)) {
                            for (UUID uuid : join) {
                                Bukkit.getPlayer(uuid).sendMessage(translateAlternateColorCodes('&', "&6&l[НОВЫЙ ВОПРОС] &fВопрос: &c" + sb + "&8&l‖ &fИгрок: &9" + p.getName()));
                                haveQuestion.add(sender_uuid);
                            }
                            send(sender, translateAlternateColorCodes('&', getString("Answer.WaitAnswer")));
                            return;
                        }
                        send(sender, translateAlternateColorCodes('&', getString("Question.HaveQuestion")));
                        return;
                    }
                    return;
                }
                send(sender, translateAlternateColorCodes('&', getString("NotHavePermission")));
            };
        }
    }
}