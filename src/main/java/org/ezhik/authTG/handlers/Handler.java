package org.ezhik.authTG.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Handler extends BukkitRunnable {
    private static Map<String, String> kickplayers = new HashMap<>();
    private static Map<String, String> minecrfatmsg = new HashMap<>();
    private static Map<String, String> dispatcCommand = new HashMap<>();
    private static Map<String, Location> locationMap = new HashMap<>();
    @Override
    public void run() {
        if (kickplayers.size() != 0) {
            Iterator<String> it = kickplayers.keySet().iterator();
            while (it.hasNext()) {
                String name = it.next();
                Player player = Bukkit.getPlayer(name);
                if(player == null) {
                    it.remove();
                } else {
                    player.kickPlayer(kickplayers.get(name));
                    it.remove();
                }
            }
        }
        if (minecrfatmsg.size() != 0) {
            Iterator<String> it = minecrfatmsg.keySet().iterator();
            while (it.hasNext()) {
                String name = it.next();
                Player player = Bukkit.getPlayer(name);
                if(player == null) {
                    it.remove();
                } else {
                    player.chat(minecrfatmsg.get(name));
                    it.remove();
                }
            }
        }
        if (dispatcCommand.size() != 0) {
            Iterator<String> it = dispatcCommand.keySet().iterator();
            while (it.hasNext()) {
                String name = it.next();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), dispatcCommand.get(name));
                it.remove();
            }
        }
        if (locationMap.size() != 0) {
            Iterator<String> it = locationMap.keySet().iterator();
            while (it.hasNext()) {
                String name = it.next();
                Player player = Bukkit.getPlayer(name);
                if(player == null) {
                    it.remove();
                } else {
                    player.teleport(locationMap.get(name));
                    it.remove();
                }
            }
        }
    }

    public static void kick(String name,String reason) {
        kickplayers.put(name, reason);
    }
    public static void sendMCmessage(String name,String message) {
        minecrfatmsg.put(name, message);
    }
    public static void dispatchCommand(String name, String command) {
        dispatcCommand.put(name, command);
    }
    public static void teleport(String name, Location location) {
        locationMap.put(name, location);
    }
}
