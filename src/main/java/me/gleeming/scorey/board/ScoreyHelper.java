package me.gleeming.scorey.board;

import lombok.Getter;
import me.gleeming.scorey.adapter.ScoreyBoard;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Scorey helper class for creating scoreboard
 * types using the annotations provided
 */
public abstract class ScoreyHelper {
    @Getter private final String title;
    @Getter private final HashMap<String, Method> boards = new HashMap<>();
    public ScoreyHelper(String title) {
        this.title = title;

        // Finds all board annotations
        for(Method method : this.getClass().getMethods()) {
            ScoreyBoard scoreyBoard = method.getAnnotation(ScoreyBoard.class);
            if(scoreyBoard != null) {
                System.out.println("[Scorey] Registered board '" + scoreyBoard.name() + "' from helper.");
                boards.put(scoreyBoard.name(), method);
            }
        }
    }
}
