package me.gleeming.scorey.player;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.gleeming.scorey.Scorey;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Method;
import java.util.*;

public class ScoreyPlayer {
    @Getter private static final HashMap<UUID, ScoreyPlayer> players = new HashMap<>();
    @Getter @Setter private static String defaultScoreboard = "";

    @Getter private final UUID uuid;
    @Getter @Setter private String scoreboard;

    // Bukkit Implementations
    @Getter private final Scoreboard bukkitScoreboard;
    @Getter private final Objective titleObjective;
    @Getter private final HashMap<Integer, Team> teams = new HashMap<>();

    public ScoreyPlayer(UUID uuid) {
        this.uuid = uuid;
        this.scoreboard = defaultScoreboard;

        bukkitScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        titleObjective = bukkitScoreboard.registerNewObjective("scorey", "dummy");
        titleObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        titleObjective.setDisplayName(ChatColor.translateAlternateColorCodes('&', Scorey.getInstance().getHelper().getTitle()));

        Bukkit.getPlayer(uuid).setScoreboard(bukkitScoreboard);
        players.put(uuid, this);
    }

    /**
     * Gets a team name that hasn't been used yet
     */
    public String getRandomTeamName() {
        String attempt = UUID.randomUUID().toString().substring(0, 16);
        if(bukkitScoreboard.getTeam(attempt) == null) return attempt;
        return getRandomTeamName();
    }

    /**
     * Update a players scoreboard
     *
     * @param uuid Player Unique ID
     * @param scoreboard Scoreboard to update
     */
    public static void setScoreboard(UUID uuid, String scoreboard) {
        ScoreyPlayer scoreyPlayer = players.get(uuid);
        if(scoreyPlayer == null) scoreyPlayer = new ScoreyPlayer(uuid);

        scoreyPlayer.setScoreboard(scoreboard);
    }

    /**
     * Update a players scoreboard
     *
     * @param player Player to update
     * @param scoreboard Scoreboard to update
     */
    public static void setScoreboard(Player player, String scoreboard) {
        ScoreyPlayer scoreyPlayer = players.get(player.getUniqueId());
        if(scoreyPlayer == null) scoreyPlayer = new ScoreyPlayer(player.getUniqueId());

        scoreyPlayer.setScoreboard(scoreboard);
    }

    /**
     * Update a players scoreboard
     *
     * @param player Player to get scoreboard
     */
    @SneakyThrows @SuppressWarnings("unchecked")
    public static List<String> getScoreboard(Player player) {
        ScoreyPlayer scoreyPlayer = players.get(player.getUniqueId());
        if(scoreyPlayer == null) scoreyPlayer = new ScoreyPlayer(player.getUniqueId());

        Method method = Scorey.getInstance().getHelper().getBoards().get(scoreyPlayer.getScoreboard());
        List<String> lines = new ArrayList<>();
        if(method != null) lines = (List<String>) method.invoke(Scorey.getInstance().getHelper(), player);
        Collections.reverse(lines);

        return lines;
    }

    /**
     * Update every player's scoreboard to default
     */
    public static void updateAllDefault() { players.values().forEach(player -> player.setScoreboard(defaultScoreboard)); }
}
