package ru.solomka.helper.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.solomka.helper.config.ConfigManager;

import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class Inventory {

    private final ConfigManager config = new ConfigManager();

    public org.bukkit.inventory.Inventory profile;

    private final ItemStack defaultItem = new ItemStack(Material.STAINED_GLASS_PANE, (byte) 2);

    private final ItemStack showGrades = new ItemStack(Material.PAPER);

    private final ItemStack showGroup = new ItemStack(Material.EGG);

    public boolean isHaveMenu(org.bukkit.inventory.Inventory inventory) {
        return inventory != null;
    }

    public boolean getOpenMenu(Player p, String name) {
        return p.getOpenInventory().getTitle().equals(name);
    }

    public void createMenu(Player p, int slots, String name) {
        if(!isHaveMenu(profile)) {
            profile = Bukkit.createInventory(null, slots, name);
        }
/*
        setItemInventory(p, defaultItem, 0, 36, true,
                setItemMeta(defaultItem, " ", config.getStringList(" ")), profile);

        setItemInventory(p, showGrades,  16, 0, false,
                setItemMeta(showGrades, config.getString("Menu.ShowGradesItem.Title"),
                        config.getStringList("Menu.ShowGradesItem.Lore")), profile);

        setItemInventory(p, showGroup,  18, 0, false,
                setItemMeta(showGroup, config.getString("Menu.ShowGroupPlayerItem.Title"),
                        config.getStringList("Menu.ShowGroupPlayerItem.Lore")), profile);

        p.openInventory(profile);
 */
    }

    public void setItemInventory(Player p, ItemStack item,
                                 int startSlot, int finalSlot,
                                 boolean setMoreItem, ItemMeta meta,
                                 org.bukkit.inventory.Inventory inventory) {

        if(!isHaveMenu(inventory)) return;

        if (setMoreItem) {
            for (int i = startSlot; i < finalSlot; i++) {
                if (isEmptySlotInventory(p, true, inventory, i)) {
                    i++;
                    continue;
                }
                item.setItemMeta(meta);
                inventory.setItem(i, item);
            }
            return;
        }
        item.setItemMeta(meta);
        inventory.setItem(startSlot, item);
    }

    public boolean isEmptySlotInventory(Player p, boolean checkMenu,
                                        org.bukkit.inventory.Inventory inventory, int slot) {
        if (checkMenu) return inventory.getItem(slot) != null;
        return p.getInventory().getItem(slot) != null;
    }

    public ItemMeta setItemMeta(ItemStack item, String name, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        lore.replaceAll(s -> net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', s));
        meta.setLore(lore);
        meta.setDisplayName(translateAlternateColorCodes('&', name));
        return meta;
    }
}