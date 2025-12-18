package org.ezhik.authTG.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BlockDamageEvent implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player victim = (Player) event.getEntity();
            if (FreezerEvent.isFreeze(victim)) {
                event.setCancelled(true);
            }
        }
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if (FreezerEvent.isFreeze(damager)) {
                event.setCancelled(true);
            }
        }
    }
}
