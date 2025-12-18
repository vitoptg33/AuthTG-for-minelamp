package org.ezhik.authTG.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BlockInventoryEvent implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            if (FreezerEvent.isFreeze((Player) event.getWhoClicked())) {
                event.setCancelled(true);
            }
        }
    }
}