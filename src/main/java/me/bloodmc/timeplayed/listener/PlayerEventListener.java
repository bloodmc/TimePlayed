package me.bloodmc.timeplayed.listener;

import me.bloodmc.timeplayed.TimePlayedPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Instant;

public class PlayerEventListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        TimePlayedPlugin.playedMap.put(event.getPlayer().getUniqueId(), Instant.now());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        TimePlayedPlugin.playedMap.remove(event.getPlayer().getUniqueId());
    }
}
