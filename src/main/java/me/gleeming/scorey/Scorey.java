package me.gleeming.scorey;

import lombok.Getter;
import me.gleeming.scorey.board.ScoreyHelper;
import me.gleeming.scorey.listener.ScoreboardListener;
import me.gleeming.scorey.player.ScoreyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Main class
 *
 * @author Gleeming
 */
public class Scorey {
    @Getter private static Scorey instance;

    @Getter private final Plugin plugin;
    @Getter private final ScoreyHelper helper;
    public Scorey(Plugin plugin, ScoreyHelper helper, String defaultScoreboard) {
        instance = this;

        this.plugin = plugin;
        this.helper = helper;

        ScoreyPlayer.setDefaultScoreboard(defaultScoreboard);
        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(), plugin);

        new BukkitRunnable() {
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    ScoreyPlayer scoreyPlayer = ScoreyPlayer.getPlayers().get(player.getUniqueId());
                    if(scoreyPlayer == null) scoreyPlayer = new ScoreyPlayer(player.getUniqueId());

                    List<String> scoreboard = ScoreyPlayer.getScoreboard(player);
                    int i = 1;
                    for(String line : scoreboard) {
                        Team team = scoreyPlayer.getTeams().get(i);

                        if(team == null) team = scoreyPlayer.getBukkitScoreboard().registerNewTeam(scoreyPlayer.getRandomTeamName());
                        if(team.getEntries().size() == 0) team.addEntry(ChatColor.values()[new Random().nextInt(ChatColor.values().length)].toString() + ChatColor.values()[new Random().nextInt(ChatColor.values().length)].toString());

                        if(line.length() > 32) line = line.substring(0, 32);

                        if(line.length() > 16) {
                            team.setPrefix(ChatColor.translateAlternateColorCodes('&', line.substring(0, 16).endsWith("&") ? line.substring(0, 15) : line.substring(0, 16)));
                            team.setSuffix(ChatColor.translateAlternateColorCodes('&', ChatColor.getLastColors(team.getPrefix()) + (line.substring(0, 16).endsWith("&") ? line.substring(15) : line.substring(16))));
                        } else {
                            team.setPrefix(ChatColor.translateAlternateColorCodes('&', line));
                            team.setSuffix("");
                        }

                        scoreyPlayer.getTitleObjective().getScore(team.getEntries().stream().findFirst().get()).setScore(i);
                        scoreyPlayer.getTeams().put(i, team);

                        i++;
                    }

                    for(int x = i; x < 20; x++) {
                        Team team = scoreyPlayer.getTeams().get(x);
                        if(team != null) {
                            scoreyPlayer.getTeams().remove(x);
                            if(team.getEntries().size() > 0) {
                                scoreyPlayer.getBukkitScoreboard().resetScores(team.getEntries().stream().findFirst().get());
                                team.unregister();
                            }
                        }
                    }
                });
            }
        }.runTaskTimer(plugin, 0, 2);
    }
}
