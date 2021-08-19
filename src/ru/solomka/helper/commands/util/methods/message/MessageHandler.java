package ru.solomka.helper.commands.util.methods.message;

import org.bukkit.entity.Player;
import ru.solomka.helper.config.ConfigManager;

public class MessageHandler {

    private final ConfigManager config = new ConfigManager();

    public String sNotHelper() {
        return config.getString("Message.Helper.NotHelper");
        }

    public String sNotHavePermission() {
        return config.getString("Message.NotHavePermission");
    }

    public String sInBlackList(Player p) {
        return config.getString("Message.BlackList.PlayerInBlackList").replace("%player%", p.getName());
    }

    public String sNotInBlackList(Player p) {
        return config.getString("Message.BlackList.PlayerNotBlackList").replace("%player%", p.getName());
    }

    public String sIsHaveMute(Player p) {
        return config.getString("Message.Helper.Mute.PlayerHaveMute").replace("%player%", p.getName());
    }

    public String sHaveMute() {
        return config.getString("Message.Question.Muted");
    }

    public String sAddMute(Player p, int time) {
        String mute = config.getString("Message.Helper.Mute.PlayerAddMute").replace("%player%", p.getName());
        return mute.replace("%time%", String.valueOf(time));
    }

    public String sHelpersNull() { return config.getString("Message.Helper.HelpersNull"); }

    public String sHaveActiveQuestion() {
        return config.getString("Message.Question.PlayerHaveQuestion"); }

    public String sSendQuestionFormat(String message, Player p) {
        String question =  config.getString("Message.Helper.Question.Format").replace("%question%", message);
        return question.replace("%player%", p.getName());
    }

    public String pSetHelper(String value) {
        return config.getString("Message.Helper.Rank.PlayerSetHelper").replace("%value%", value);
    }

    public String pInfBlacklistAdd(Player p, String reason) {
        String infoAdd = config.getString("Message.BlackList.InfoPlayerAddBlacklist").replace("%reason%", reason);
        return infoAdd.replace("%player%", p.getName());
    }

    public String pInfoBlacklistRemove(Player p) {
        return config.getString("Message.BlackList.InfoPlayerRemoveBlacklist").replace("%player%", p.getName());
    }

    public String sBlacklistAdd(Player p, String reason) {
        String add = config.getString("Message.BlackList.AddBlacklistPlayer").replace("%player%", p.getName());
        return add.replace("%reason%", reason);
    }

    public String sBlacklistRemove(Player p) {
        return config.getString("Message.BlackList.RemoveBlacklistPlayer").replace("%player%", p.getName());
    }

    public String sNotHaveQuestion(Player p) {
        return config.getString("Message.Question.PlayerNotHaveQuestion").replace("%player%", p.getName());
    }

    public String sNotMutedPlayer(Player p) {
        return config.getString("Message.Helper.Mute.PlayerNotMuted").replace("%player%", p.getName());
    }

    public String sUnMutePlayer(Player p) {
        return config.getString("Message.Helper.Mute.UnMutePlayer").replace("%player%", p.getName());
    }

    public String pUnMute(Player p) {
        return config.getString("Message.Helper.Mute.UnMute").replace("%player%", p.getName());
    }

    public String sAutoTakeOffMute() {
        return config.getString("Message.Helper.Mute.AutoTakeOffMute");
    }

    public String pIsNull(String p) {
        return config.getString("Message.PlayerIsEmpty").replace("%player%", p);
    }

    public String sWarn(Player p, int warnCount) {
        String warn = config.getString("Message.Helper.Warns.SetWarn").replace("%warn%", String.valueOf(warnCount));
        return warn.replace("%player%", p.getName());
    }

    public String pCooldownOver() {
        return config.getString("Message.Question.CooldownTimeOver");
    }

    public String pMute(Player p, int time, String reason) {
        String helper = config.getString("Message.Helper.Mute.PlayerMute").replace("%player%", p.getName());
        String hreason = helper.replace("%reason%", reason);
        return hreason.replace("%time%", String.valueOf(time));
    }

    public String sInfoWarn(Player p, int countWarn, int maxWarn) {
        String infoWarn = config.getString("Message.Helper.Warns.PlayerSetWarn").replace("%value_warn%", String.valueOf(countWarn));
        String warn = infoWarn.replace("%max_warn%", String.valueOf(maxWarn));
        return warn.replace("%player%", p.getName());
    }

    public String sMessageIsNull() {
        return config.getString("Message.Helper.MessageEmpty");
    }

    public String sQuestionNull() {
        return config.getString("Message.Question.QuestionNull");
    }

    public String sMinSizeMessage(int minSizeCount) {
        return config.getString("Message.Helper.ShortMessage").replace("%min_size%", String.valueOf(minSizeCount));
    }

    public String pInfoJoinHelper(Player p) {
        return config.getString("Message.Helper.Status.JoinHelper").replace("%player%", p.getName());
    }

    public String pInfoExitHelper(Player p) {
        return config.getString("Message.Helper.Status.LeaveHelper").replace("%player%", p.getName());
    }

    public String sFormatHelperChat(String name, String message) {
        String format = config.getString("Message.Helper.Chat.Format").replace("%sender_name%", name);
        return format.replace("%message%",message);
    }
}