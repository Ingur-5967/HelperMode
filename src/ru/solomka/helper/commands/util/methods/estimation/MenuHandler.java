package ru.solomka.helper.commands.util.methods.estimation;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.solomka.helper.config.ConfigManager;

import java.util.HashSet;
import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class MenuHandler {

    @Getter public static Inventory inventory;
    @Getter public static Player helper;
    @Getter private Player user;
    @Getter private int slot;

    public static final HashSet<Player> menu = new HashSet<>();

    private static final ItemStack LIKE = new ItemStack(Material.WOOL, 1, (byte) 5);
    private static final ItemStack DISLIKE = new ItemStack(Material.WOOL, 1, (byte) 14);
    private static final ItemStack DEFAULT_ITEM = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);

    public MenuHandler(Inventory inventory, Player helper, Player user, int slot) {
        MenuHandler.inventory = inventory;
        MenuHandler.helper = helper;
        this.user = user;
        this.slot = slot;
    }

    public MenuHandler() {}

    public void createInventory(Player p) {
        if(inventory == null) inventory = Bukkit.createInventory(null, slot, "Оцените ответ хелпера");
        openInventory();
        menu.add(p);
    }

    public void openInventory() {
        inventory.setItem(12, setItemMeta(LIKE, "&a&lХОРОШИЙ ОТВЕТ", new ConfigManager().getStringList("")));
        inventory.setItem(14, setItemMeta(DISLIKE, "&c&lПЛОХОЙ ОТВЕТ", new ConfigManager().getStringList("")));
        inventory.setItem(13, DEFAULT_ITEM);
        setItemInv(DEFAULT_ITEM, 0,12);
        setItemInv(DEFAULT_ITEM, 15,27);
        user.openInventory(getInventory());
    }

    public static void setItemInv(ItemStack item, int startSlot, int finalSlot) {
        for (int i = startSlot; i < finalSlot; i++) {
            if (inventory.getItem(i) != null) {
                i++;
            } else {
                inventory.setItem(i, item);
            }
        }
    }

    public ItemStack setItemMeta(ItemStack item, String DisplayName, List<String> list) {
        ItemMeta meta = item.getItemMeta();
        list.replaceAll(s -> translateAlternateColorCodes('&', s));
        meta.setLore(list);
        meta.setDisplayName(translateAlternateColorCodes('&', DisplayName));
        item.setItemMeta(meta);
        return item;
    }
}