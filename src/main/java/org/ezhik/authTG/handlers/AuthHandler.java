package org.ezhik.authTG.handlers;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.ezhik.authTG.AuthTG;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthHandler extends BukkitRunnable {
    private static Map<UUID, Integer> timeoutMap = new HashMap<>();
    private static Map<UUID, BossBar> bossBarMap = new HashMap<>();
    private static Map<UUID, Integer> initialTimeoutMap = new HashMap<>();
    @Override
    public void run() {
        if (!timeoutMap.isEmpty()) {
            for (Map.Entry<UUID, Integer> entry : timeoutMap.entrySet()) {
                Player player = Bukkit.getPlayer(entry.getKey());
                if (player != null) {
                    if (entry.getValue() > 0) {
                        entry.setValue(entry.getValue() - 1);
                        BossBar bar = bossBarMap.get(entry.getKey());
                        if (bar != null) {
                            int remaining = entry.getValue();
                            int initial = initialTimeoutMap.get(entry.getKey());
                            bar.setProgress((double) remaining / initial);
                            String message = AuthTG.getMessage("bossbar.title", "MC");
                            if (message == null) message = "&cAuthentication Time Left: {SECONDS} seconds";
                            String title = message.replace("{SECONDS}", String.valueOf(remaining));
                            bar.setTitle(title);
                        }
                    } else {
                        player.kickPlayer(AuthTG.getMessage("kicktimeout", "MC"));
                        removeTimeout(entry.getKey());
                    }
                } else {
                    removeTimeout(entry.getKey());
                }
            }
        }
    }

    public static void setTimeout(UUID uuid, int time) {
        timeoutMap.put(uuid, time);
        initialTimeoutMap.put(uuid, time);
        if (AuthTG.bossBarEnabled) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                BarColor color = BarColor.valueOf(AuthTG.bossBarColor.toUpperCase());
                String message = AuthTG.getMessage("bossbar.title", "MC");
                if (message == null) message = "&cAuthentication Time Left: {SECONDS} seconds";
                String title = message.replace("{SECONDS}", String.valueOf(time));
                BossBar bar = Bukkit.createBossBar(title, color, BarStyle.SOLID);
                bar.addPlayer(player);
                bossBarMap.put(uuid, bar);
            }
        }
    }

    public static void removeTimeout(UUID uuid) {
        if (timeoutMap.containsKey(uuid)) {
            timeoutMap.remove(uuid);
            initialTimeoutMap.remove(uuid);
            BossBar bar = bossBarMap.remove(uuid);
            if (bar != null) {
                bar.removeAll();
            }
        }
    }
}
