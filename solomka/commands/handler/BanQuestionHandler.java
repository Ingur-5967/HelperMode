package ru.solomka.commands.handler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
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

public class BanQuestionHandler extends BasicCommandHandler {

    protected static BanQuestionHandler instance;

    public BanQuestionHandler() {
        super(new Ban());
        instance = this;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
    private static class Ban extends BasicCommand {
        public Ban(String command, Usage<CommandSender, String[]> func) {
            super(command, func);
        }

        private static int ban;

        @Override
        public String getDescription() {
            return "Ban question player";
        }

        @Override
        public String getUsage() {
            return "/ban-question";
        }

        @Override
        public String getPermission() {
            return "HelperMode.helper";
        }

        public Ban() {
            super(null, null);

            func = (sender, args) -> {
                final List<ECommand> cmds = new ArrayList<>(instance.getRegisteredCommands());

                cmds.add(this);

                if (!(sender instanceof Player)) return;

                Player p = (Player) sender;

                UUID id = p.getUniqueId();

                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < args.length; i++) sb.append(args[i]).append(' ');
                if (sb.length() < 2) {
                    buildString(getStringList("HelpListHelper"), p);
                    return;
                }

                try {
                    ban = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    send(sender, translateAlternateColorCodes('&', getString("ErrorNum")));
                    return;
                }

                if (ban > getInt("MaxTimeBanned")) {
                    send(sender, translateAlternateColorCodes('&', getString("ErrorTime")));
                    return;
                }


                Player extract = Bukkit.getPlayerExact(args[0]);

                if (extract == null) {
                    send(sender, translateAlternateColorCodes('&', getString("NoPlayer")));
                    return;
                }

                UUID id_extract = extract.getUniqueId();

                if (sender.hasPermission(getPermission()) || sender.isOp()) {
                    if (join.contains(id)) {
                        if (!haveBan.contains(id_extract)) {
                            if (map.contains(id_extract)) {
                                haveBan.add(id_extract);
                                new BukkitRunnable(){
                                    @Override
                                    public void run() {
                                        if(!haveBan.contains(id_extract)) return;
                                        send(sender, translateAlternateColorCodes('&', getString("Question.TimeBanLeft")));
                                        haveBan.remove(id_extract);
                                        cancel();
                                    }
                                }.runTaskLater(Main.getInstance(), ban * 20L);
                                send(sender, translateAlternateColorCodes('&', "&9&l[INFO] &fВы &cзабанили &fсистему подачи вопросов игроку &f" + extract.getName() + " на &6" + ban + "сек &fпо причине: &a" + sb));
                                send(extract, translateAlternateColorCodes('&', "&6&l[WARNING] &fВам был выдан &fбан на " + ban + "сек по причине: " + sb + "&8‖ &fХелпером: &9" + p.getName()));
                                return;
                            }
                            send(sender, translateAlternateColorCodes('&', getString("Question.NoQuestionPlayer")));
                            return;
                        }
                        send(sender, translateAlternateColorCodes('&', getString("Question.HaveBanAlready")));
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