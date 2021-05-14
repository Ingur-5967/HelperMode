
package ru.solomka.config;

import org.bukkit.entity.Player;
import ru.solomka.Main;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.OptionalCommand.message.EText.send;

public class ConfigManager {

    private static final Main plugin = Main.getInstance();

    public static void createConfig() {
        if (new File(plugin.getDataFolder(), "config.yml").exists())
            return;
        plugin.getLogger().info("Creating new configuration file...");
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveDefaultConfig();
        plugin.getLogger().info("Configuration file success created!");
    }

    public static void reload(Player p, String string) {
        plugin.reloadConfig();
        send(p, translateAlternateColorCodes('&', string));
    }

    public static int getInt(String string) {
        return plugin.getConfig().getInt(string);
    }

    public static String getString(String string) {
        return plugin.getConfig().getString(string);
    }

    public static List<String> getStringList(String string) {
        return plugin.getConfig().getStringList(string);
    }

    public static void set(String str, Object obj) {
        plugin.getConfig().set(str, obj);
    }

    public static void save() throws IOException {
        plugin.getConfig().save(new File(plugin.getDataFolder(), "config.yml"));
    }
}

