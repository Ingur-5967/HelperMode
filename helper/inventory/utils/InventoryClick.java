package ru.solomka.helper.inventory.utils;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import ru.solomka.helper.Main;

import static org.bukkit.ChatColor.*;

public class InventoryClick implements Listener {
/*
    @Getter private final Main plugin;

    public InventoryClick(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        Inventory click = e.getClickedInventory();
        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();

        if (p.getOpenInventory().getTitle().contains("Ваш профиль")) {
            if (click == null) return;
            if (slot <= 36) e.setCancelled(true);
            if (slot == 15) p.closeInventory();
        }
    }
 */
}
