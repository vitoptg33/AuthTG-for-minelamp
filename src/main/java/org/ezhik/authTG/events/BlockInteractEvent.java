package org.ezhik.authTG.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockInteractEvent implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (FreezerEvent.isFreeze(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}