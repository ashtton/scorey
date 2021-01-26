package me.gleeming.scorey.listener;

import me.gleeming.scorey.player.ScoreyPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e) { ScoreyPlayer.getPlayers().remove(e.getPlayer().getUniqueId()); }
}
