package me.gleeming.scorey.adapter;

import org.bukkit.entity.Player;

import java.util.List;

public interface ScoreyAdapter {
    String getTitle(Player player);
    List<String> getBoard(Player player);
}
