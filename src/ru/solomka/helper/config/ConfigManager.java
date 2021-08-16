
package ru.solomka.helper.config;

import ru.solomka.helper.Main;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    public void reload() { plugin.reloadConfig(); }

    public int getInt(String string) {
        return plugin.getConfig().getInt(string);
    }

    public String getString(String string) {
        return plugin.getConfig().getString(string);
    }

    public List<String> getStringList(String string) {
        return plugin.getConfig().getStringList(string);
    }

    public void set(String str, Object obj) {
        plugin.getConfig().set(str, obj);
    }

    public void save() throws IOException {
        plugin.getConfig().save(new File(plugin.getDataFolder(), "config.yml"));
    }

    public boolean getBoolean(String s) {
        return plugin.getConfig().getBoolean(s);
    }
}

