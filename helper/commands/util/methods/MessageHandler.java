package ru.solomka.helper.commands.util.methods;

import org.bukkit.entity.Player;
import ru.solomka.helper.config.ConfigManager;

public class MessageHandler {

    private final ConfigManager config = new ConfigManager();

    public String notHelper() {
        return config.getString("Message.Helper.NotHelper");
        }

    public String notHavePermission() {
        return config.getString("Message.NotHavePermission");
    }

    public String playerInBlackList(Player p) {
        return config.getString("Message.BlackList.PlayerInBlackList").replace("%player%", p.getName());
    }

    public String playerNotBlackList(Player p) {
        return config.getString("Message.BlackList.PlayerNotBlackList").replace("%player%", p.getName());
    }

    public String haveMute(Player p) {
        return config.getString("Message.PlayerHaveMute").replace("%player%", p.getName());
    }

    public String addMute(Player p) {
        return config.getString("Message.PlayerAddMute").replace("%player%", p.getName()); }

    public String helpersNull() {
        return config.getString("Message.Helper.HelpersNull"); }

    public String haveActiveQuestion() {
        return config.getString("Message.HelpersNull"); }

    public String sendQuestionFormat(String message) {
        return config.getString("Message.Question.Format").replace("%question%", message); }

    public String rankHelper(String status) {
        return config.getString("Message.Helper.SetRank").replace("%status%", status);
    }

    public String emptyRank(int rank) {
        return config.getString("Message.Helper.RankEmpty").replace("%rank%", String.valueOf(rank));
    }

    public String takeOffHelperPerm(Player p) {
        return config.getString("Message.Helper.TakeOffPermHelper").replace("%player%", p.getName());
    }

    public String playerInfoBlacklistAdd(String reason) {
        return config.getString("Message.BlackList.InfoPlayerAdd").replace("%reason%", reason);
    }

    public String playerInfoBlacklistRemove(Player p) {
        return config.getString("Message.BlackList.InfoPlayerRemove").replace("%player%", p.getName());
    }

    public String blacklistAdd(Player p) {
        return config.getString("Message.BlackList.AddBlackList").replace("%player%", p.getName());
    }

    public String blacklistRemove(Player p) {
        return config.getString("Message.BlackList.RemoveBlackList").replace("%player%", p.getName());
    }

    public String playerNotHaveQuestion(Player p) {
        return config.getString("Message.Question.NotHaveQuestion").replace("%player%", p.getName());
    }

    public String autoTakeOffMute() {
        return config.getString("Message.AutoTakeOffMute");
    }
}
