package com.garrapeta.gameoflive.pattern;


import com.garrapeta.gameoflive.GameOfLifeWorld;

public interface Pattern {

    public void addPattern(GameOfLifeWorld world, int x, int y);

    public void addPatternCentered(GameOfLifeWorld world);
}
