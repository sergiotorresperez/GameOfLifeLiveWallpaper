package com.garrapeta.gameoflive.pattern;

import com.garrapeta.gameoflive.GameOfLifeWorld;

public class BugThingPattern implements Pattern {

    @Override
    public void addPattern(GameOfLifeWorld world, int x, int y) {
        world.setAlive(x + 2 , y + 0 , true);
        world.setAlive(x + 3 , y + 0 , true);
        world.setAlive(x + 4 , y + 0 , true);
        world.setAlive(x + 8 , y + 0 , true);
        world.setAlive(x + 9 , y + 0 , true);
        world.setAlive(x + 10 , y + 0 , true);

        world.setAlive(x + 0 , y + 2 , true);
        world.setAlive(x + 5 , y + 2 , true);
        world.setAlive(x + 7 , y + 2 , true);
        world.setAlive(x + 12 , y + 2 , true);

        world.setAlive(x + 0 , y + 3 , true);
        world.setAlive(x + 5 , y + 3 , true);
        world.setAlive(x + 7 , y + 3 , true);
        world.setAlive(x + 12 , y + 3 , true);

        world.setAlive(x + 0 , y + 3 , true);
        world.setAlive(x + 7 , y + 3 , true);
        world.setAlive(x + 12 , y + 3 , true);

        world.setAlive(x + 2 , y + 5 , true);
        world.setAlive(x + 3 , y + 5 , true);
        world.setAlive(x + 4 , y + 5 , true);
        world.setAlive(x + 8 , y + 5 , true);
        world.setAlive(x + 9 , y + 5 , true);
        world.setAlive(x + 10 , y + 5 , true);
    }

    @Override
    public void addPatternCentered(GameOfLifeWorld world) {
        int x = (world.getCols() - 12) / 2;
        int y = (world.getRows() - 6) / 2;
        addPattern(world, x, y);
    }
}
