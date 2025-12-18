package org.ezhik.authTG.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.ezhik.authTG.AuthTG;

import java.util.HashMap;
import java.util.Map;

public class FreezerEvent implements Listener {

    private static Map<String, Location> freezeplayer = new HashMap<String, Location>();
    public static Map<String, Location> beforeFreeze = new HashMap<String, Location>();
    public static void freezeplayer(Player player, Location location) {
        freezeplayer.put(player.getName(), location);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0, false, false));
    }
    public static void unfreezeplayer(String name) {
        freezeplayer.remove(name);
        Player player = Bukkit.getPlayer(name);
        if (player != null) {
            Bukkit.getScheduler().runTask(AuthTG.getInstance(), () -> {
                player.removePotionEffect(PotionEffectType.BLINDNESS);
            });
        }
    }
    public static boolean isFreeze(Player player) {
        return freezeplayer.containsKey(player.getName());
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (freezeplayer.containsKey(event.getPlayer().getName())) {
            event.getPlayer().teleport(freezeplayer.get(event.getPlayer().getName()));
        }
    }
}