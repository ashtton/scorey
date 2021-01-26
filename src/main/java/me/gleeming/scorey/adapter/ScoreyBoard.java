package me.gleeming.scorey.adapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScoreyBoard {
    /**
     * Scoreboard name
     * Note: This is code based only
     */
    String name();
}
