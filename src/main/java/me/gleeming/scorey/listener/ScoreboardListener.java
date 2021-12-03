package me.gleeming.scorey.listener;

import me.gleeming.scorey.Scorey;
import me.gleeming.scorey.player.ScoreyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e) { ScoreyPlayer.getPlayers().remove(e.getPlayer().getUniqueId()); }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) { Bukkit.getScheduler().runTaskLater(Scorey.getInstance().getPlugin(), () -> new ScoreyPlayer(e.getPlayer().getUniqueId()), 2); }
}
