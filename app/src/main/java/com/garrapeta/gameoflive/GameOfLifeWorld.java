package com.garrapeta.gameoflive;


import com.garrapeta.gameoflive.pattern.BugThingPattern;

import java.util.Arrays;

public class GameOfLifeWorld {


    private boolean[][] matrix;
    private boolean[][] auxMatrix;
    private int cols;
    private int rows;

    public GameOfLifeWorld() {
        // TODO: fix this
        matrix = new boolean[1][1];
        auxMatrix = new boolean[1][1];
    }

    public void createMatrix(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;

        matrix = new boolean[cols][rows];
        auxMatrix = new boolean[cols][rows];
        init();
    }

    private void init() {
        new BugThingPattern().addPatternCentered(this);
    }

    public int getCols() {
        return matrix.length;
    }

    public int getRows() {
        return matrix[0].length;
    }

    public boolean isAlive(int x, int y) {
        return isAlive(x, y, matrix);
    }

    public boolean isAlive(int x, int y, boolean[][] m) {
        return m[x][y];
    }

    public void setAlive(int x, int y, boolean alive) {
        setAlive(x, y, alive, matrix);
    }

    private void setAlive(int x, int y, boolean alive, boolean[][] m) {
        m[x % m.length][y % m[0].length] = alive;
    }

    public int getLivingNeighbours(int x, int y) {
        int count = 0;

        // left up
        if (isAlive(getLeft(x), getUp(y), matrix)) {
            count++;
        }

        // up
        if (isAlive(x, getUp(y), matrix)) {
            count++;
        }

        // right up
        if (isAlive(getRight(x), getUp(y), matrix)) {
            count++;
        }

        // right
        if (isAlive(getRight(x), y, matrix)) {
            count++;
        }

        // right down
        if (isAlive(getRight(x), getDown(y), matrix)) {
            count++;
        }

        // down
        if (isAlive(x, getDown(y), matrix)) {
            count++;
        }

        // left down
        if (isAlive(getLeft(x), getDown(y), matrix)) {
            count++;
        }

        // left
        if (isAlive(getLeft(x), y, matrix)) {
            count++;
        }


        return count;
    }

    private int getLeft(int x) {
        return (cols + x - 1) % cols;
    }

    private int getUp(int y) {
        return (rows + y - 1) % rows;
    }

    private int getRight(int x) {
        return (x + 1) % cols;
    }

    private int getDown(int y) {
        return (y + 1) % rows;
    }

    public void onCellClicked(int x, int y) {
        setAlive(x, y, !isAlive(x, y, matrix), matrix);
    }

    public void step() {
        computeCellsChanges();
    }

    private void computeCellsChanges() {

        clearMatrix(auxMatrix);

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                computeCellChanges(i, j);
            }
        }

        copyMatrix(auxMatrix, matrix);
    }

    private void computeCellChanges(int x, int y) {

        final int livingNeighbours = getLivingNeighbours(x, y);

        if (isAlive(x, y, matrix)) {
            if (livingNeighbours < 2) {
                // Any live cell with fewer than two live neighbours dies, as if caused by under-population.
                setAlive(x, y, false, auxMatrix);
            } else if (livingNeighbours == 2 || livingNeighbours == 3) {
                // Any live cell with two or three live neighbours lives on to the next generation.
                setAlive(x, y, true, auxMatrix);
            } else if (livingNeighbours > 3) {
                // Any live cell with more than three live neighbours dies, as if by overcrowding.
                setAlive(x, y, false, auxMatrix);
            }
        } else {
            if (livingNeighbours == 3) {
                // Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
                setAlive(x, y, true, auxMatrix);
            }
        }
    }

    private void clearMatrix(boolean[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            Arrays.fill(matrix[i], false);
        }
    }

    private void copyMatrix(boolean[][] source, boolean[][] target) {
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, target[i], 0, source[i].length);
        }
    }

    private void toggleAll() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                setAlive(i, j, !isAlive(i, j, matrix), matrix);
            }
        }
    }


}
