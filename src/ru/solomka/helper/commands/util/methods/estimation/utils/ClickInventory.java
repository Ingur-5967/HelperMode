package ru.solomka.helper.commands.util.methods.estimation.utils;

import com.mojang.authlib.yggdrasil.response.User;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import ru.solomka.helper.Main;
import ru.solomka.helper.commands.util.methods.estimation.MenuHandler;
import ru.solomka.helper.database.tables.Users;

import java.sql.SQLException;

import static org.bukkit.ChatColor.*;
import static ru.solomka.helper.commands.util.methods.estimation.MenuHandler.*;

public class ClickInventory implements Listener {

    @Getter private final Main plugin;

    private final Users users = new Users(Main.getInstance());

    public ClickInventory(Main plugin) throws SQLException, ClassNotFoundException {
        this.plugin = plugin;
    }

    @EventHandler
    public void click(InventoryClickEvent e) throws SQLException {
        Inventory click = e.getClickedInventory();
        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();

        if (p.getOpenInventory().getTitle().contains("Оцените ответ хелпера")) {
            if (click == null) return;
            if (slot <= 27) e.setCancelled(true);
            switch (slot) {
                case 12 : {
                    users.setValueLikeGradesPlayer(p.getName(), users.getValueLikeGradesPlayer(getHelper().getName()) + 1);
                    getHelper().sendMessage(translateAlternateColorCodes('&', "&e&l[*] &fИгрок &6" + p.getName() + "&f поставил вам &aхороший&f отзыв!"));
                    p.closeInventory();
                    return;
                }

                case 14 : {
                    users.setValueDisLikeGradesPlayer(p.getName(), users.getValueDisLikeGradesPlayer(getHelper().getName()) + 1);
                    getHelper().sendMessage(translateAlternateColorCodes('&', "&e&l[*] &fИгрок &6" + p.getName() + "&f поставил вам &4плохой&f отзыв!"));
                    p.closeInventory();
                }
            }
        }
    }
}
